package net.wardmatt1.peacfullcraft.event;

import com.mojang.serialization.Dynamic;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.wardmatt1.peacfullcraft.registry.ModPotions;

public class PotionImpactHandler {

    // ---------------------------------------------------------
    // Potion impact handler — triggers AoE cure
    // ---------------------------------------------------------
    @SubscribeEvent
    public void onPotionImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof ThrownPotion potion)) {
            return;
        }

        // Read potion contents using Data Components (1.21+)
        PotionContents contents = potion.getItem().get(DataComponents.POTION_CONTENTS);

        // Extract Potion from Optional<Holder<Potion>>
        Potion potionType = null;
        if (contents != null) {
            potionType = contents.potion()
                    .map(Holder::value)
                    .orElse(null);
        }

        // Not a potion or not our custom potion
        if (potionType == null) return;
        if (potionType != ModPotions.INSTANT_CURE.value()) return;

        // AoE cure logic
        var level = potion.level();
        var pos = potion.position();

        double radius = 4.0;
        AABB box = new AABB(
                pos.x - radius, pos.y - radius, pos.z - radius,
                pos.x + radius, pos.y + radius, pos.z + radius
        );

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (entity instanceof Villager villager) {

                // ---------------------------------------------------------
                // Only cure villagers with jobs (no unemployed, no nitwits)
                // ---------------------------------------------------------
                var profession = villager.getVillagerData().getProfession();
                if (profession == VillagerProfession.NONE ||
                        profession == VillagerProfession.NITWIT) {
                    continue;
                }

                // ---------------------------------------------------------
                // NEW SYSTEM: already cured?
                // ---------------------------------------------------------
                if (villager.getPersistentData().getBoolean("peacefullcraft_cured")) {
                    continue;
                }

                // ---------------------------------------------------------
                // OLD SYSTEM MIGRATION:
                // Detect villagers cured before the new system existed
                // (they were renamed to "Name (Cured)")
                // ---------------------------------------------------------
                if (villager.hasCustomName() &&
                        villager.getName().getString().endsWith("(Cured)")) {

                    villager.getPersistentData().putBoolean("peacefullcraft_cured", true);
                    continue;
                }

                // Apply cure normally
                applyCure(villager);
            }
        }
    }

    // ---------------------------------------------------------
    // Cure logic — one-time cure, discounts, healing, rename
    // ---------------------------------------------------------
    private void applyCure(Villager villager) {

        // Mark villager as cured (persistent)
        villager.getPersistentData().putBoolean("peacefullcraft_cured", true);

        // Wipe all gossip (correct 1.21.1 method)
        villager.getGossips().update(
                new Dynamic<>(NbtOps.INSTANCE, new CompoundTag())
        );

        // Permanent discount via special price diff
        villager.getOffers().forEach(offer -> {
            offer.addToSpecialPriceDiff(-20);
        });

        // Heal the villager
        villager.heal(10.0F);

        // ---------------------------------------------------------
        // Clean rename logic — ensure "(Cured)" appears exactly once
        // ---------------------------------------------------------
        String name = villager.getName().getString();

        // Remove any existing "(Cured)" suffix
        if (name.endsWith("(Cured)")) {
            name = name.substring(0, name.length() - "(Cured)".length()).trim();
        }

        // Apply suffix once
        villager.setCustomNameVisible(true);
        villager.setCustomName(Component.literal(name + " (Cured)"));
    }

    // ---------------------------------------------------------
    // Brewing chain — Weakness → Base Cure → Instant Cure → Splash
    // ---------------------------------------------------------
    @SubscribeEvent
    public void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        // Step 1: Weakness + Rotten Flesh → Base Cure
        builder.addMix(Potions.WEAKNESS, Items.ROTTEN_FLESH, ModPotions.BASE_CURE);

        // Step 2: Base Cure + Golden Apple → Instant Cure (drinkable)
        builder.addMix(ModPotions.BASE_CURE, Items.GOLDEN_APPLE, ModPotions.INSTANT_CURE);

        // Step 3: Instant Cure + Gunpowder → Instant Cure (splash)
        builder.addMix(ModPotions.INSTANT_CURE, Items.GUNPOWDER, ModPotions.INSTANT_CURE);
    }

}

package net.wardmatt1.peacfullcraft.event;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.wardmatt1.peacfullcraft.registry.ModPotions;

public class PotionImpactHandler {

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
                applyCure(villager);
            }
        }
    }

    // ---------------------------------------------------------
    // Cure logic — instant discounts, healing, cosmetic rename
    // ---------------------------------------------------------
    private void applyCure(Villager villager) {

        // Apply strong discounts to all trades
        villager.getOffers().forEach(offer -> offer.addToSpecialPriceDiff(-20));

        // Heal the villager
        villager.heal(10.0F);

        // Cosmetic rename
        villager.setCustomNameVisible(true);
        villager.setCustomName(
                villager.getName().copy().append(" (Cured)")
        );

        // Optional: add particles or sound here
    }
}

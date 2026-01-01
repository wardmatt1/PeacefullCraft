package net.wardmatt1.peacfullcraft.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.wardmatt1.peacfullcraft.PeacefullCraft;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(Registries.POTION, PeacefullCraft.MODID);

    public static final DeferredHolder<Potion, Potion> BASE_CURE =
            POTIONS.register("base_cure",
                    () -> new Potion(
                            new MobEffectInstance(ModEffects.CURE_EFFECT, 1)
                    ));

    public static final DeferredHolder<Potion, Potion> INSTANT_CURE =
            POTIONS.register("instant_cure",
                    () -> new Potion(
                            new MobEffectInstance(ModEffects.CURE_EFFECT, 1)
                    ));
}

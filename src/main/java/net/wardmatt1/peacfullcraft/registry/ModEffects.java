package net.wardmatt1.peacfullcraft.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.wardmatt1.peacfullcraft.PeacefullCraft;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, PeacefullCraft.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> CURE_EFFECT =
            EFFECTS.register("cure_effect", CureEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> CURE_INGREDIENT =
            EFFECTS.register("cure_ingredient", CureIngredient::new);

    // ---------------------------------------------------------
    // Custom effect class (required because MobEffect is protected)
    // ---------------------------------------------------------
    public static class CureEffect extends MobEffect {
        public CureEffect() {
            super(MobEffectCategory.BENEFICIAL, 0x00FF00); // green
        }

    }
    public static class CureIngredient extends MobEffect {
        public CureIngredient() {
            super(MobEffectCategory.NEUTRAL, 0x87A363 ); // green
        }
    }
}

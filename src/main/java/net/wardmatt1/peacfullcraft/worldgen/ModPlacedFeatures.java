package net.wardmatt1.peacfullcraft.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.wardmatt1.peacfullcraft.PeacefullCraft;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> NETHER_GEODE_KEY = registerKey("nether_geode");
    public static final ResourceKey<PlacedFeature> END_GEODE_KEY = registerKey("end_geode");




    public  static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?,?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);


        register(context, NETHER_GEODE_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_GEODE),
                List.of(RarityFilter.onAverageOnceEvery(75), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(64), VerticalAnchor.absolute(50)),
                        BiomeFilter.biome()));

        register(context, END_GEODE_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_GEODE),
                List.of(RarityFilter.onAverageOnceEvery(100), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(0)),
                        BiomeFilter.biome()));

    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PeacefullCraft.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}

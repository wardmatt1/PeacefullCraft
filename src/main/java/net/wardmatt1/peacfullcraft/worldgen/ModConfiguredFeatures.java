package net.wardmatt1.peacfullcraft.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.wardmatt1.peacfullcraft.PeacefullCraft;

import java.util.List;

public class ModConfiguredFeatures {
    //CF -> PF -> BM
    //Geodes go here.
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_GEODE = registerKey("nether_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_GEODE = registerKey("end_geode");





    public  static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {
        register(context, NETHER_GEODE, Feature.GEODE,
                new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),               //fillingProvider
                        BlockStateProvider.simple(Blocks.NETHERRACK),                                              //innerLayerProvider
                        BlockStateProvider.simple(Blocks.SOUL_SAND),                                               //alternateInnerLayerProvider
                        BlockStateProvider.simple(Blocks.BLACKSTONE),                                              //middleLayerProvider
                        BlockStateProvider.simple(Blocks.BASALT),                                                  //outerLayerProvider
                        List.of(Blocks.BONE_BLOCK.defaultBlockState(),Blocks.BONE_BLOCK.defaultBlockState(),Blocks.BONE_BLOCK.defaultBlockState(),Blocks.BONE_BLOCK.defaultBlockState(),Blocks.GLOWSTONE.defaultBlockState(),Blocks.GLOWSTONE.defaultBlockState(),Blocks.GLOWSTONE.defaultBlockState(),Blocks.GLOWSTONE.defaultBlockState(),Blocks.GLOWSTONE.defaultBlockState(),Blocks.ANCIENT_DEBRIS.defaultBlockState()),   //innerPlacements
                        BlockTags.FEATURES_CANNOT_REPLACE ,                                                        //cannotReplace
                        BlockTags.GEODE_INVALID_BLOCKS),                                                           //invalidBlocks

                        new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                        new GeodeCrackSettings(0.95D, 2.0D, 2),
                        0.35D,
                        0.083D,
                        true,
                        UniformInt.of(4, 6),
                        UniformInt.of(3, 4),
                        UniformInt.of(1, 2),
                        -16,
                        16,
                        0.05D,
                        1));
        register(context, END_GEODE, Feature.GEODE,
                new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),               //fillingProvider
                        BlockStateProvider.simple(Blocks.END_STONE),                                               //innerLayerProvider
                        BlockStateProvider.simple(Blocks.END_STONE),                                               //alternateInnerLayerProvider
                        BlockStateProvider.simple(Blocks.END_STONE),                                               //middleLayerProvider
                        BlockStateProvider.simple(Blocks.OBSIDIAN),                                                //outerLayerProvider
                        List.of(Blocks.END_STONE.defaultBlockState()),    //innerPlacements
                        BlockTags.FEATURES_CANNOT_REPLACE ,                                                        //cannotReplace
                        BlockTags.GEODE_INVALID_BLOCKS),                                                           //invalidBlocks

                        new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                        new GeodeCrackSettings(0.95D, 2.0D, 2),
                        0.35D,
                        0.083D,
                        true,
                        UniformInt.of(4, 6),
                        UniformInt.of(3, 4),
                        UniformInt.of(1, 2),
                        -16,
                        16,
                        0.05D,
                        1));

    }



    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PeacefullCraft.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}

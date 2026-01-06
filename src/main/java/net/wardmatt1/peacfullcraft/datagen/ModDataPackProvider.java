package net.wardmatt1.peacfullcraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.wardmatt1.peacfullcraft.PeacefullCraft;
import net.wardmatt1.peacfullcraft.worldgen.ModBiomeModifiers;
import net.wardmatt1.peacfullcraft.worldgen.ModConfiguredFeatures;
import net.wardmatt1.peacfullcraft.worldgen.ModPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataPackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);
    public ModDataPackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries )  {


        super(output, registries, BUILDER, Set.of(PeacefullCraft.MODID));
    }
}

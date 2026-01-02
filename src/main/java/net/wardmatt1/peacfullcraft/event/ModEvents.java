package net.wardmatt1.peacfullcraft.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.core.component.DataComponents;
import net.wardmatt1.peacfullcraft.PeacefullCraft;
import net.wardmatt1.peacfullcraft.registry.ModPotions;

@EventBusSubscriber(modid = PeacefullCraft.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void blockIngredientUse(PlayerInteractEvent.RightClickItem event) {

        var contents = event.getItemStack().get(DataComponents.POTION_CONTENTS);

        if (contents != null &&
                contents.potion().map(p -> p.is(ModPotions.BASE_CURE)).orElse(false)) {

            event.setCanceled(true);
        }
    }
}

package net.wardmatt1.peacfullcraft.peaceful_survival;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = "peacfullcraft")
public class SpawnBlocker {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {

        EntityType<?> type = event.getEntity().getType();

        // Allow only spiders and wolves
        if (type.getCategory() == MobCategory.MONSTER
                && type != EntityType.SPIDER
                && type != EntityType.WOLF) {

            event.setCanceled(true);
        }
    }
}

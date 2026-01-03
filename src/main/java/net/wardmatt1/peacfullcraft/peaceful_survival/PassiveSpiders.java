package net.wardmatt1.peacfullcraft.peaceful_survival;

import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = "peacfullcraft")
public class PassiveSpiders {

    @SubscribeEvent
    public static void onSpiderJoinWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Spider spider)) return;

        // Throttle spawn rate (~25%)
        if (spider.level().random.nextFloat() > 0.25F) {
            event.setCanceled(true);
            return;
        }

        // 1. Remove proactive aggression ONLY
        spider.targetSelector.removeAllGoals(goal ->
                goal instanceof NearestAttackableTargetGoal<?>);

        // 2. Remove proactive melee attack goals
        spider.goalSelector.removeAllGoals(goal ->
                goal instanceof MeleeAttackGoal);

        // 3. Re-add retaliation behavior
        spider.targetSelector.addGoal(1, new HurtByTargetGoal(spider));

        // 4. Re-add melee attack goal so retaliation can actually deal damage
        spider.goalSelector.addGoal(2, new MeleeAttackGoal(spider, 1.0D, false));
    }
}

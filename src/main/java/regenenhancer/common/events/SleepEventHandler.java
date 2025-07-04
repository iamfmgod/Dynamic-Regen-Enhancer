package regenenhancer.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import regenenhancer.RegenEnhancerConfig;
import regenenhancer.common.util.SleepTracker;

public class SleepEventHandler {
    @SubscribeEvent
    public void onPlayerWakeUp(PlayerWakeUpEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.world.isRemote) {
            if (RegenEnhancerConfig.enableFullHealOnSleep) {
                float maxHealth = player.getMaxHealth();
                float currentHealth = player.getHealth();
                float toHeal = maxHealth - currentHealth;
                if (toHeal > 0) {
                    player.setHealth(maxHealth);
                    int foodToRemove = (int)Math.ceil(toHeal * RegenEnhancerConfig.sleepFoodCostPerHeart);
                    int newFood = Math.max(0, player.getFoodStats().getFoodLevel() - foodToRemove);
                    player.getFoodStats().setFoodLevel(newFood);
                }
            }
            SleepTracker.recordSleep(player);
        }
    }
}
package regenenhancer.common.events;

import java.lang.reflect.Field;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import regenenhancer.common.items.ItemHerbalSoup;

public class PlayerRegenHandler {
    private static Field foodTimerField;
    static {
        try {
            foodTimerField = net.minecraft.util.FoodStats.class.getDeclaredField("foodTimer");
            foodTimerField.setAccessible(true);
        } catch (Exception e) {
            foodTimerField = null;
        }
    }
    private static void resetFoodTimerSafe(EntityPlayer player) {
        if (foodTimerField != null) {
            try {
                foodTimerField.setInt(player.getFoodStats(), 0);
            } catch (Exception ignored) {}
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerHeal(LivingHealEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        if (player.world.isRemote) return;
        if (!regenenhancer.RegenEnhancerConfig.disableVanillaRegen) return;

        // Allow healing from Herbal Soup
        if (ItemHerbalSoup.ALLOW_SOUP_HEAL) return;

        // Block ALL healing except from potion effects
        boolean hasPotionEffect = !player.getActivePotionEffects().isEmpty();
        if (!hasPotionEffect) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        if (player.world.isRemote) return;
        if (!regenenhancer.RegenEnhancerConfig.disableVanillaRegen) return;

        // Block vanilla regen
        if (player.getHealth() < player.getMaxHealth()) {
            player.setHealth(player.getHealth());
            // Prevent vanilla hunger drain for natural regen
            resetFoodTimerSafe(player);
        }
    }
}
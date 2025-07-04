package regenenhancer.common.util;

import net.minecraft.entity.player.EntityPlayer;

public class SleepTracker {
    private static final int REGEN_WINDOW_TICKS = 20 * 60 * 10; // 10 minutes

    public static void recordSleep(EntityPlayer player) {
        player.getEntityData()
                .setLong("RegenEnhancer:lastSleepTime",
                        player.world.getTotalWorldTime());
    }

    public static boolean hasRecentlySlept(EntityPlayer player) {
        long last = player.getEntityData()
                .getLong("RegenEnhancer:lastSleepTime");
        return (player.world.getTotalWorldTime() - last) < REGEN_WINDOW_TICKS;
    }
}
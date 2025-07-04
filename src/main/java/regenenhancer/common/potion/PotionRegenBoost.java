package regenenhancer.common.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import regenenhancer.RegenEnhancerConfig;

public class PotionRegenBoost extends Potion {
    public PotionRegenBoost() {
        super(false, 0x66FF66);
        setPotionName("effect.regen_boost");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amp) {
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(RegenEnhancerConfig.potionRegenBoostAmount * (amp + 1) / 2.0F);
        }
    }

    @Override
    public boolean isReady(int duration, int amp) {
        int interval = RegenEnhancerConfig.potionRegenBoostIntervalBase >> amp;
        return interval <= 0 || duration % interval == 0;
    }
}
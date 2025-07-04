package regenenhancer.common.potion;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import regenenhancer.RegenEnhancerMod;

public class ModPotions {
    public static Potion     REGEN_BOOST;
    public static PotionType REGEN_BOOST_TYPE;

    public static void init() {
        // define & register effect
        REGEN_BOOST = new PotionRegenBoost();
        REGEN_BOOST.setRegistryName(RegenEnhancerMod.MODID, "regen_boost");
        ForgeRegistries.POTIONS.register(REGEN_BOOST);

        // define & register potion type
        REGEN_BOOST_TYPE = new PotionType(
                "regen_boost",
                new PotionEffect(REGEN_BOOST, 100, 0)
        );
        REGEN_BOOST_TYPE.setRegistryName(RegenEnhancerMod.MODID, "regen_boost");
        ForgeRegistries.POTION_TYPES.register(REGEN_BOOST_TYPE);
    }
}
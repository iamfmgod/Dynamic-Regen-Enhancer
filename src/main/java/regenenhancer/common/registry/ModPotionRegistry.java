package regenenhancer.common.registry;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import regenenhancer.RegenEnhancerMod;
import regenenhancer.common.potion.PotionRegenBoost;

@Mod.EventBusSubscriber(modid = RegenEnhancerMod.MODID)
public class ModPotionRegistry {

    public static Potion     REGEN_BOOST;
    public static PotionType REGEN_BOOST_TYPE;

    @SubscribeEvent
    public static void onRegisterPotions(RegistryEvent.Register<Potion> event) {
        REGEN_BOOST = new PotionRegenBoost()
                .setRegistryName(RegenEnhancerMod.MODID, "regen_boost")
                .setPotionName("effect.regen_boost");
        event.getRegistry().register(REGEN_BOOST);
    }

    @SubscribeEvent
    public static void onRegisterPotionTypes(RegistryEvent.Register<PotionType> event) {
        REGEN_BOOST_TYPE = new PotionType(
                "regen_boost",
                new PotionEffect(REGEN_BOOST, 100, 0)
        ).setRegistryName(RegenEnhancerMod.MODID, "regen_boost");
        event.getRegistry().register(REGEN_BOOST_TYPE);
    }
}
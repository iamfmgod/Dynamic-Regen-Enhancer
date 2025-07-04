// src/main/java/regenenhancer/common/registry/ModItemRegistry.java
package regenenhancer.common.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import regenenhancer.RegenEnhancerMod;
import regenenhancer.common.items.ItemBandage;
import regenenhancer.common.items.ItemHerbalSoup;

@Mod.EventBusSubscriber(modid = RegenEnhancerMod.MODID)
public class ModItemRegistry {
    public static Item BANDAGE;
    public static Item HERBAL_SOUP;

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> ev) {
        BANDAGE = new ItemBandage()
            .setRegistryName(RegenEnhancerMod.MODID, ItemBandage.NAME)
            .setTranslationKey("regenenhancer." + ItemBandage.NAME);
        HERBAL_SOUP = new ItemHerbalSoup()
            .setRegistryName(RegenEnhancerMod.MODID, ItemHerbalSoup.NAME)
            .setTranslationKey("regenenhancer." + ItemHerbalSoup.NAME);

        ev.getRegistry().registerAll(BANDAGE, HERBAL_SOUP);
    }
}
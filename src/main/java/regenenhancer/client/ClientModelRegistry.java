package regenenhancer.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import regenenhancer.RegenEnhancerMod;
import regenenhancer.common.registry.ModItemRegistry;
import regenenhancer.common.items.ItemBandage;
import regenenhancer.common.items.ItemHerbalSoup;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = RegenEnhancerMod.MODID)
public class ClientModelRegistry {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(ItemBandage.NAME);
        registerModel(ItemHerbalSoup.NAME);
    }

    private static void registerModel(String name) {
        Item item = Item.getByNameOrId(RegenEnhancerMod.MODID + ":" + name);
        if (item != null) {
            ModelLoader.setCustomModelResourceLocation(
                item, 0,
                new ModelResourceLocation(RegenEnhancerMod.MODID + ":" + name, "inventory")
            );
        }
    }
}

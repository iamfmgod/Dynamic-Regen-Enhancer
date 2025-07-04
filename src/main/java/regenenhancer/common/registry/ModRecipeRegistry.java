package regenenhancer.common.registry;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import regenenhancer.RegenEnhancerMod;
import regenenhancer.common.recipe.RecipeCraftBandage;
import regenenhancer.common.recipe.RecipeHerbalSoup;
import regenenhancer.common.recipe.RecipeRefillBandage;

@Mod.EventBusSubscriber(modid = RegenEnhancerMod.MODID)
public class ModRecipeRegistry {
    @SubscribeEvent
    public static void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(new RecipeRefillBandage().setRegistryName(RegenEnhancerMod.MODID, "refill_bandage"));
        event.getRegistry().register(new RecipeCraftBandage().setRegistryName(RegenEnhancerMod.MODID, "craft_bandage"));
        event.getRegistry().register(new RecipeHerbalSoup().setRegistryName(RegenEnhancerMod.MODID, "herbal_soup"));
    }
}

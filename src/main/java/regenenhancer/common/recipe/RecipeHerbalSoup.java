package regenenhancer.common.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFood;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import regenenhancer.common.registry.ModItemRegistry;

public class RecipeHerbalSoup extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean foundBowl = false;
        boolean foundLeaf = false;
        boolean foundPlant = false;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                // Bowl
                if (item == Items.BOWL && !foundBowl) {
                    foundBowl = true;
                // Any leaf block
                } else if (item instanceof ItemBlock && !foundLeaf) {
                    Block block = ((ItemBlock) item).getBlock();
                    if (block instanceof BlockLeaves || block.getRegistryName().toString().contains("leaves")) {
                        foundLeaf = true;
                    } else {
                        return false;
                    }
                // Any plant material: seeds, wheat, or flower
                } else if (!foundPlant && (item instanceof ItemSeeds || item == Items.WHEAT || (item instanceof ItemBlock && ((ItemBlock)item).getBlock() instanceof BlockFlower))) {
                    foundPlant = true;
                } else {
                    return false;
                }
            }
        }
        return foundBowl && foundLeaf && foundPlant;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return new ItemStack(ModItemRegistry.HERBAL_SOUP);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItemRegistry.HERBAL_SOUP);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        return list;
    }
}


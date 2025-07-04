package regenenhancer.common.recipe;

import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.PotionType;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import regenenhancer.common.registry.ModItemRegistry;
import regenenhancer.common.registry.ModPotionRegistry;

public class RecipeCraftBandage extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        int woolCount = 0;
        boolean foundPotion = false;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Item.getItemFromBlock(Blocks.WOOL) && stack.getMetadata() == 0) {
                    woolCount++;
                } else if (stack.getItem() == Items.POTIONITEM) {
                    PotionType type = PotionUtils.getPotionFromItem(stack);
                    if (type == ModPotionRegistry.REGEN_BOOST_TYPE) {
                        if (foundPotion) return false;
                        foundPotion = true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return woolCount == 8 && foundPotion;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return new ItemStack(ModItemRegistry.BANDAGE);
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 9;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItemRegistry.BANDAGE);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.POTIONITEM) {
                list.set(i, new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return list;
    }
}

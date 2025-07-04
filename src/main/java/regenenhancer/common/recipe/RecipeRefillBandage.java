package regenenhancer.common.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.PotionType;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;
import regenenhancer.common.items.ItemBandage;
import regenenhancer.common.registry.ModItemRegistry;
import regenenhancer.common.registry.ModPotionRegistry;

public class RecipeRefillBandage extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean foundBandage = false;
        boolean foundPotion = false;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item instanceof ItemBandage && ItemBandage.isDamagedBandage(stack)) {
                    if (foundBandage) return false; // Only one allowed
                    foundBandage = true;
                } else if (item == Items.POTIONITEM) {
                    PotionType type = PotionUtils.getPotionFromItem(stack);
                    if (type == ModPotionRegistry.REGEN_BOOST_TYPE) {
                        if (foundPotion) return false; // Only one allowed
                        foundPotion = true;
                    } else {
                        return false; // Only regen potion allowed
                    }
                } else {
                    return false; // Only bandage and regen potion allowed
                }
            }
        }
        return foundBandage && foundPotion;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemBandage) {
                ItemStack result = stack.copy();
                result.setItemDamage(0); // Fully refilled
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
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


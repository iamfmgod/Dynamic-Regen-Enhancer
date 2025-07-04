package regenenhancer.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import regenenhancer.RegenEnhancerConfig;
import regenenhancer.RegenEnhancerMod;

public class ItemBandage extends Item {
    public static final String NAME = "bandage";

    public ItemBandage() {
        super();
        setMaxStackSize(RegenEnhancerConfig.bandageMaxStack);
        setMaxDamage(RegenEnhancerConfig.bandageUses);
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return RegenEnhancerConfig.bandageUseDuration;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && stack.getItemDamage() < stack.getMaxDamage()) {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving) {
        if (!worldIn.isRemote && entityLiving instanceof EntityPlayer && stack.getItemDamage() < stack.getMaxDamage()) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            player.addPotionEffect(new PotionEffect(
                    MobEffects.REGENERATION,
                    RegenEnhancerConfig.bandageRegenDuration,
                    RegenEnhancerConfig.bandageRegenAmplifier,
                    false,
                    true
            ));
            worldIn.playSound(null,
                    player.posX, player.posY, player.posZ,
                    SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
                    SoundCategory.PLAYERS, 1.0F, 1.0F
            );
            stack.damageItem(1, player);
        }
        return stack;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack,
                               @Nullable World world,
                               @Nonnull List<String> tooltip,
                               @Nonnull ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY +
                I18n.translateToLocal("item.regenenhancer.bandage.desc"));
        tooltip.add(TextFormatting.DARK_GREEN +
                String.format(I18n.translateToLocal("item.regenenhancer.bandage.uses"), (stack.getMaxDamage() - stack.getItemDamage())));
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (count % 10 == 0) {
            player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                    1.25f, (float) (1.1f + 0.05f * player.getRNG().nextDouble()));
        }
    }

    // Helper: returns true if the bandage is damaged (used)
    public static boolean isDamagedBandage(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemBandage && stack.getItemDamage() > 0;
    }
}
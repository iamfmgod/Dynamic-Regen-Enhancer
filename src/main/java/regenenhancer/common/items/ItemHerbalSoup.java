package regenenhancer.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import regenenhancer.RegenEnhancerConfig;
import regenenhancer.RegenEnhancerMod;

public class ItemHerbalSoup extends Item {
    public static final String NAME = "herbal_soup";
    public static boolean ALLOW_SOUP_HEAL = false;

    public ItemHerbalSoup() {
        super();
        setCreativeTab(CreativeTabs.FOOD);
        setMaxStackSize(RegenEnhancerConfig.herbalSoupMaxStack);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world,
                                                    EntityPlayer player,
                                                    @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {
            ALLOW_SOUP_HEAL = true;
            player.heal(RegenEnhancerConfig.herbalSoupHealAmount / 2.0F);
            ALLOW_SOUP_HEAL = false;
            world.playSound(null,
                    player.posX, player.posY, player.posZ,
                    SoundEvents.ENTITY_PLAYER_BURP,
                    SoundCategory.PLAYERS, 1.0F, 1.0F
            );
            stack.shrink(1);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack,
                               @Nullable World world,
                               @Nonnull List<String> tooltip,
                               @Nonnull ITooltipFlag flag) {
        tooltip.add(TextFormatting.GREEN +
                I18n.translateToLocal("item.regenenhancer.herbal_soup.desc"));
        tooltip.add(TextFormatting.GRAY +
                I18n.translateToLocal("item.regenenhancer.herbal_soup.heal"));
    }
}
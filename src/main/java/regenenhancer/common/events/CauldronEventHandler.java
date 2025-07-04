package regenenhancer.common.events;

import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.PotionType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import regenenhancer.RegenEnhancerConfig;
import regenenhancer.RegenEnhancerMod;
import regenenhancer.network.PacketBrewParticles;
import regenenhancer.common.registry.ModPotionRegistry;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Mod.EventBusSubscriber(modid = RegenEnhancerMod.MODID)
public class CauldronEventHandler {
    public static int BREW_TIME = RegenEnhancerConfig.cauldronBrewTime;
    private static final Map<BlockPos, Integer> brewTimers = new ConcurrentHashMap<>();
    private static final Set<BlockPos> signaled = new ConcurrentSkipListSet<>();

    public static Integer getBrewTime(BlockPos pos) {
        return brewTimers.get(pos);
    }

    public static boolean isBrewReady(BlockPos pos) {
        Integer t = brewTimers.get(pos);
        return t != null && t >= BREW_TIME;
    }

    @SubscribeEvent
    public static void onAddFood(PlayerInteractEvent.RightClickBlock ev) {
        World world = ev.getWorld();
        if (world.isRemote) return;

        BlockPos pos = ev.getPos();
        if (world.getBlockState(pos).getBlock() != Blocks.CAULDRON) return;
        if (world.getBlockState(pos.down()).getBlock() != Blocks.FIRE) return;

        ItemStack held = ev.getEntityPlayer().getHeldItem(ev.getHand());
        if (!(held.getItem() instanceof ItemFood)
                || ((ItemFood) held.getItem()).isWolfsFavoriteMeat()) return;

        int level = world.getBlockState(pos).getValue(BlockCauldron.LEVEL);
        if (level == 0) return;

        // Prevent eating the food item when used for cauldron brewing
        ev.setCanceled(true);

        held.shrink(1);
        brewTimers.put(pos.toImmutable(), 0);
        signaled.remove(pos);
        // Damage the player by half a heart (1.0F) if enabled in config
        if (RegenEnhancerConfig.cauldronBrewSelfDamage) {
            float dmg = RegenEnhancerConfig.cauldronBrewSelfDamageAmount * 0.5F;
            ev.getEntityPlayer().attackEntityFrom(net.minecraft.util.DamageSource.GENERIC, dmg);
        }
    }

    private static void spawnBrewCompleteParticles(World world, BlockPos pos) {
        for (int i = 0; i < RegenEnhancerConfig.cauldronBrewParticleCount; i++) {
            double angle = 2 * Math.PI * i / RegenEnhancerConfig.cauldronBrewParticleCount;
            double radius = 0.7 + world.rand.nextDouble() * 0.2;
            double px = pos.getX() + 0.5 + Math.cos(angle) * radius;
            double py = pos.getY() + 1.2 + world.rand.nextDouble() * 0.3;
            double pz = pos.getZ() + 0.5 + Math.sin(angle) * radius;
            world.spawnParticle(
                    EnumParticleTypes.SPELL_WITCH,
                    true,
                    px, py, pz,
                    0, 0.1 + world.rand.nextDouble() * 0.1, 0
            );
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent ev) {
        if (RegenEnhancerConfig.disableCauldronBrewing) return;
        if (ev.phase != TickEvent.Phase.END) return;

        World world = ev.world;
        Iterator<Map.Entry<BlockPos, Integer>> it = brewTimers.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<BlockPos, Integer> entry = it.next();
            BlockPos pos = entry.getKey();
            int t = entry.getValue();

            if (world.getBlockState(pos).getBlock() != Blocks.CAULDRON
                    || world.getBlockState(pos).getValue(BlockCauldron.LEVEL) == 0) {
                it.remove();
                signaled.remove(pos);
                continue;
            }

            int nt = t + 1;
            entry.setValue(nt);


            if (nt == BREW_TIME && signaled.add(pos)) {
                world.playSound(
                        null,
                        pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                        SoundEvents.BLOCK_BREWING_STAND_BREW,
                        SoundCategory.BLOCKS,
                        1.0F, 1.0F
                );
                if (!world.isRemote) {
                    RegenEnhancerMod.NETWORK.sendToAllAround(
                        new PacketBrewParticles(pos),
                        new net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint(
                            world.provider.getDimension(),
                            pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                            32D
                        )
                    );
                }
            }
            // Client: always spawn for local player if brew just completed
            if (world.isRemote && nt == BREW_TIME && signaled.contains(pos)) {
                spawnBrewCompleteParticles(world, pos);
            }
        }
    }

    @SubscribeEvent
    public static void onCollectPotion(PlayerInteractEvent.RightClickBlock ev) {
        if (RegenEnhancerConfig.disableCauldronBrewing) return;
        World world = ev.getWorld();
        BlockPos pos = ev.getPos();
        EntityPlayer player = ev.getEntityPlayer();
        ItemStack held = player.getHeldItem(ev.getHand());

        if (world.getBlockState(pos).getBlock() != Blocks.CAULDRON) return;
        if (held.getItem() != Items.GLASS_BOTTLE) return;

        Integer t = brewTimers.get(pos);
        boolean isReady = t != null && t >= BREW_TIME;

        // CLIENT: swirl effect only
        if (world.isRemote) {
            if (isReady) {
                double sx = player.posX;
                double sy = player.posY + player.getEyeHeight();
                double sz = player.posZ;
                double ex = pos.getX() + 0.5;
                double ey = pos.getY() + 1.0;
                double ez = pos.getZ() + 0.5;

                for (int i = 0; i < 20; i++) {
                    double f = i / 20.0;
                    double px = sx + (ex - sx) * f + (world.rand.nextDouble() - 0.5) * 0.2;
                    double py = sy + (ey - sy) * f + (world.rand.nextDouble() - 0.5) * 0.2;
                    double pz = sz + (ez - sz) * f + (world.rand.nextDouble() - 0.5) * 0.2;
                    world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, px, py, pz, 0, 0, 0);
                }
            }
            return;
        }

        // SERVER: only handle if brew is ready
        if (isReady) {
            ev.setCanceled(true);

            int newLevel = world.getBlockState(pos).getValue(BlockCauldron.LEVEL) - 1;
            world.setBlockState(
                    pos,
                    Blocks.CAULDRON.getDefaultState().withProperty(BlockCauldron.LEVEL, newLevel)
            );

            held.shrink(1);
            PotionType type = ModPotionRegistry.REGEN_BOOST_TYPE;
            ItemStack potion = PotionUtils.addPotionToItemStack(
                    new ItemStack(Items.POTIONITEM), type
            );

            if (held.isEmpty()) {
                player.setHeldItem(ev.getHand(), potion);
            } else if (!player.inventory.addItemStackToInventory(potion)) {
                player.dropItem(potion, false);
            }

            brewTimers.remove(pos);
            signaled.remove(pos);
            return;
        }

        // Fallback: vanilla water bottle collection
        int level = world.getBlockState(pos).getValue(BlockCauldron.LEVEL);
        if (level > 0 && held.getItem() == Items.GLASS_BOTTLE) {
            ev.setCanceled(true);
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState().withProperty(BlockCauldron.LEVEL, level - 1));
            held.shrink(1);
            ItemStack waterBottle = new ItemStack(Items.POTIONITEM);
            PotionUtils.addPotionToItemStack(waterBottle, PotionType.getPotionTypeForName("water"));
            if (held.isEmpty()) {
                player.setHeldItem(ev.getHand(), waterBottle);
            } else if (!player.inventory.addItemStackToInventory(waterBottle)) {
                player.dropItem(waterBottle, false);
            }
        }
    }
}

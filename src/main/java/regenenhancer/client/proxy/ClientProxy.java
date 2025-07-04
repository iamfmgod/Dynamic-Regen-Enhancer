package regenenhancer.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import regenenhancer.common.events.CauldronEventHandler;
import regenenhancer.server.proxy.CommonProxy;
import regenenhancer.client.proxy.ClientProxy;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void init() {
        super.init();

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
                (state, world, pos, tintIndex) -> {
                    if (tintIndex != 0 || world == null || pos == null) return 0xFFFFFF;

                    if (CauldronEventHandler.isBrewReady(pos)) {
                        return 0x00FFAA; // teal-green when ready
                    }

                    return 0x3F76E4; // default water blue
                },
                net.minecraft.init.Blocks.CAULDRON
        );
    }
}
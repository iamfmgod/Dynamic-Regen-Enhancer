package regenenhancer.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import regenenhancer.client.gui.GuiRegenEnhancer;

public class GuiHandler implements IGuiHandler {
    public static final int GUI_ID_REGEN = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null; // no container in this example
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUI_ID_REGEN) {
            return new GuiRegenEnhancer(player);
        }
        return null;
    }
}
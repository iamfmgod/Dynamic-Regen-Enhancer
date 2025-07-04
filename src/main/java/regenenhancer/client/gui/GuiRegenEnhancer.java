package regenenhancer.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import regenenhancer.RegenEnhancerMod;

public class GuiRegenEnhancer extends GuiScreen {
    private static final ResourceLocation BG =
            new ResourceLocation(RegenEnhancerMod.MODID,
                    "textures/gui/regen_enhancer.png");
    private final EntityPlayer player;
    private final int xSize = 176, ySize = 166;
    private int guiLeft, guiTop;

    public GuiRegenEnhancer(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void initGui() {
        guiLeft = (width - xSize) / 2;
        guiTop  = (height - ySize) / 2;
    }

    @Override
    public void drawScreen(int mx, int my, float pt) {
        drawDefaultBackground();
        GlStateManager.color(1F,1F,1F,1F);
        mc.getTextureManager().bindTexture(BG);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        fontRenderer.drawString("Regen Enhancer", guiLeft + 8,  guiTop + 6, 0xFFFFFF);
        boolean awake = player.getEntityData()
                .getLong("RegenEnhancer:lastSleepTime")
                + (20*60*10)
                > player.world.getTotalWorldTime();
        String status = awake ? "Natural Regen: ON" : "Natural Regen: OFF";
        fontRenderer.drawString(status, guiLeft + 10, guiTop + 25, 0xFF5555);

        super.drawScreen(mx, my, pt);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
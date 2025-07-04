package regenenhancer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import regenenhancer.common.events.CauldronEventHandler;
import regenenhancer.common.events.PlayerRegenHandler;
import regenenhancer.common.events.SleepEventHandler;
import regenenhancer.common.gui.GuiHandler;
import regenenhancer.network.PacketBrewParticles;
import regenenhancer.server.proxy.CommonProxy;

@Mod(
        modid = RegenEnhancerMod.MODID,
        name = RegenEnhancerMod.NAME,
        version = RegenEnhancerMod.VERSION
)
public class RegenEnhancerMod {
    public static final String MODID = "regenenhancer";
    public static final String NAME = "Regen Enhancer";
    public static final String VERSION = "1.0";

    @SidedProxy(
            clientSide = "regenenhancer.client.proxy.ClientProxy",
            serverSide = "regenenhancer.server.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(MODID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerRegenHandler());
        MinecraftForge.EVENT_BUS.register(new SleepEventHandler());
        MinecraftForge.EVENT_BUS.register(new CauldronEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        NETWORK.registerMessage(PacketBrewParticles.Handler.class, PacketBrewParticles.class, 0, Side.CLIENT);
        MinecraftForge.EVENT_BUS.register(this); // Register for config change events
    }

    @net.minecraftforge.fml.common.eventhandler.SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            net.minecraftforge.common.config.ConfigManager.sync(MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(); // Register client-side color handlers, etc.
    }
}
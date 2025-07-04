package regenenhancer.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketBrewParticles implements IMessage {
    private BlockPos pos;

    public PacketBrewParticles() {}
    public PacketBrewParticles(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<PacketBrewParticles, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketBrewParticles msg, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                // Brew complete burst
                for (int i = 0; i < 60; i++) {
                    double angle = 2 * Math.PI * i / 60.0;
                    double radius = 0.7 + Minecraft.getMinecraft().world.rand.nextDouble() * 0.2;
                    double px = msg.pos.getX() + 0.5 + Math.cos(angle) * radius;
                    double py = msg.pos.getY() + 1.2 + Minecraft.getMinecraft().world.rand.nextDouble() * 0.3;
                    double pz = msg.pos.getZ() + 0.5 + Math.sin(angle) * radius;
                    Minecraft.getMinecraft().world.spawnParticle(
                        net.minecraft.util.EnumParticleTypes.SPELL_WITCH,
                        px, py, pz,
                        0, 0.1 + Minecraft.getMinecraft().world.rand.nextDouble() * 0.1, 0
                    );
                }
                // Minor brewing effect: gentle green bubbles
                for (int i = 0; i < 4; i++) {
                    double px = msg.pos.getX() + 0.3 + Minecraft.getMinecraft().world.rand.nextDouble() * 0.4;
                    double py = msg.pos.getY() + 1.0 + Minecraft.getMinecraft().world.rand.nextDouble() * 0.2;
                    double pz = msg.pos.getZ() + 0.3 + Minecraft.getMinecraft().world.rand.nextDouble() * 0.4;
                    Minecraft.getMinecraft().world.spawnParticle(
                        net.minecraft.util.EnumParticleTypes.SPELL_MOB,
                        px, py, pz,
                        0, 0.05, 0
                    );
                }
            });
            return null;
        }
    }
}

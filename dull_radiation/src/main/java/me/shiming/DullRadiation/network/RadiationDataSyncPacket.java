package me.shiming.DullRadiation.network;

import me.shiming.DullRadiation.DullRadiation;
import me.shiming.DullRadiation.capability.RadiationCapability;
import me.shiming.DullRadiation.config.RadiationConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;

/**
 * 辐射数据同步包
 *
 * 用于在服务端和客户端之间同步玩家的辐射数据
 * 注意：网络包注册需要在 Forge 1.20.1 中使用正确的 API 实现
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadiationDataSyncPacket {

    private final double radiation;
    private final double flux;
    private final double delta;

    /**
     * 构造函数
     */
    public RadiationDataSyncPacket(double radiation, double flux, double delta) {
        this.radiation = radiation;
        this.flux = flux;
        this.delta = delta;
    }

    /**
     * 从缓冲区解码数据包
     */
    public RadiationDataSyncPacket(FriendlyByteBuf buffer) {
        this.radiation = buffer.readDouble();
        this.flux = buffer.readDouble();
        this.delta = buffer.readDouble();
    }

    /**
     * 将数据包编码到缓冲区
     */
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.radiation);
        buffer.writeDouble(this.flux);
        buffer.writeDouble(this.delta);
    }

    /**
     * 客户端处理
     */
    public static void handleClient(Player player, double radiation, double flux, double delta) {
        // TODO: 实现客户端数据处理
        // 这部分需要在正确的网络系统中实现
    }

    /**
     * 注册网络包
     */
    public static void register() {
        // TODO: 实现网络包注册（需要 Forge 1.20.1 正确的网络 API）
        DullRadiation.LOGGER.info("RadiationDataSyncPacket - network system pending implementation");
    }
}

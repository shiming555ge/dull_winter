package me.shiming.DullRadiation.capability;

import me.shiming.DullRadiation.DullRadiation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

/**
 * 辐射 Capability 常量和工具类
 *
 * 负责：
 * - 定义 Capability 常量
 * - 提供便捷的访问方法
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadiationCapability {

    /**
     * Radiation Capability 类型
     */
    public static final Capability<IRadiationData> RADIATION_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    /**
     * Capability ID（用于资源位置）
     */
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(DullRadiation.MODID, "radiation_data");

    /**
     * 便捷方法：获取玩家的辐射数据
     */
    public static LazyOptional<IRadiationData> getRadiationData(Player player) {
        return player.getCapability(RADIATION_CAPABILITY);
    }

    /**
     * 静态注册方法（用于主类调用）
     */
    public static void register() {
        // Capability 通过 RadiationCapabilityRegistrar 注册
        // 这个方法用于保持代码一致性
    }
}

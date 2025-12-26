package me.shiming.DullRadiation.api;

import me.shiming.DullRadiation.capability.IRadiationData;
import me.shiming.DullRadiation.capability.RadiationCapability;
import me.shiming.DullRadiation.DullRadiation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

/**
 * 辐射系统 API
 *
 * 提供给其他模组使用的公共接口
 *
 * 使用示例：
 * <pre>
 * // 获取玩家辐射值
 * double radiation = RadiationAPI.getPlayerRadiation(player);
 *
 * // 增加玩家辐射
 * RadiationAPI.addPlayerRadiation(player, 10.0);
 *
 * // 获取辐射等级
 * int level = RadiationAPI.getRadiationLevel(player);
 * </pre>
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadiationAPI {

    private static boolean initialized = false;

    /**
     * 初始化 API（由主类调用）
     */
    public static void init() {
        if (!initialized) {
            DullRadiation.LOGGER.info("Radiation API initialized");
            initialized = true;
        }
    }

    // ========== 基础访问方法 ==========

    /**
     * 获取玩家的辐射数据
     * @param player 玩家
     * @return 辐射数据的 LazyOptional，如果不存在则返回 empty
     */
    public static LazyOptional<IRadiationData> getRadiationData(Player player) {
        return RadiationCapability.getRadiationData(player);
    }

    // ========== 辐射值操作 ==========

    /**
     * 获取玩家当前的辐射值
     * @param player 玩家
     * @return 辐射值，如果无法获取则返回 0.0
     */
    public static double getPlayerRadiation(Player player) {
        return getRadiationData(player)
                .map(IRadiationData::getRadiation)
                .orElse(0.0);
    }

    /**
     * 设置玩家辐射值
     * @param player 玩家
     * @param radiation 辐射值
     */
    public static void setPlayerRadiation(Player player, double radiation) {
        getRadiationData(player).ifPresent(data -> data.setRadiation(radiation));
    }

    /**
     * 增加玩家辐射值
     * @param player 玩家
     * @param amount 增加量
     */
    public static void addPlayerRadiation(Player player, double amount) {
        getRadiationData(player).ifPresent(data -> data.addRadiation(amount));
    }

    // ========== 辐射通量操作 ==========

    /**
     * 获取玩家当前的辐射通量
     * @param player 玩家
     * @return 通量值，如果无法获取则返回 0.0
     */
    public static double getPlayerFlux(Player player) {
        return getRadiationData(player)
                .map(IRadiationData::getFlux)
                .orElse(0.0);
    }

    /**
     * 增加玩家辐射通量
     * @param player 玩家
     * @param amount 增加量
     */
    public static void addPlayerFlux(Player player, double amount) {
        getRadiationData(player).ifPresent(data -> data.addFlux(amount));
    }

    // ========== 辐射增量操作 ==========

    /**
     * 获取玩家上一次的辐射增量
     * @param player 玩家
     * @return 增量值，如果无法获取则返回 0.0
     */
    public static double getPlayerDelta(Player player) {
        return getRadiationData(player)
                .map(IRadiationData::getDelta)
                .orElse(0.0);
    }

    // ========== 辐射等级 ==========

    /**
     * 获取玩家辐射等级 (0-10)
     * @param player 玩家
     * @return 辐射等级，如果无法获取则返回 0
     */
    public static int getRadiationLevel(Player player) {
        return getRadiationData(player)
                .map(IRadiationData::getRadiationLevel)
                .orElse(0);
    }

    /**
     * 检查玩家是否受到严重辐射（等级 >= 7）
     * @param player 玩家
     * @return 是否严重
     */
    public static boolean isSeverelyRadiated(Player player) {
        return getRadiationData(player)
                .map(IRadiationData::isSeverelyRadiated)
                .orElse(false);
    }

    // ========== 数据管理 ==========

    /**
     * 更新玩家辐射数据（将 Flux 转化为 Radiation）
     * @param player 玩家
     */
    public static void updateRadiationData(Player player) {
        getRadiationData(player).ifPresent(IRadiationData::update);
    }

    /**
     * 重置玩家辐射数据
     * @param player 玩家
     */
    public static void resetRadiationData(Player player) {
        getRadiationData(player).ifPresent(IRadiationData::reset);
    }

    /**
     * 检查玩家是否有辐射数据
     * @param player 玩家
     * @return 是否有数据
     */
    public static boolean hasRadiationData(Player player) {
        return getRadiationData(player).isPresent();
    }
}

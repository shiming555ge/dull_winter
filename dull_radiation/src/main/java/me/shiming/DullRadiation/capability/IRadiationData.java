package me.shiming.DullRadiation.capability;

/**
 * 玩家辐射数据接口
 *
 * 辐射系统核心概念：
 * - Radiation（辐射值）：玩家当前累积的辐射总量，影响玩家状态
 * - Flux（通量）：短时间内接受的辐射量，会定期转化为 Radiation
 * - Delta（增量）：上一次转化的辐射量
 *
 * @author Shiming
 * @version 2.0.0
 */
public interface IRadiationData {

    /**
     * 获取当前辐射值
     * @return 辐射值 (0.0 - Double.MAX_VALUE)
     */
    double getRadiation();

    /**
     * 设置辐射值
     * @param radiation 辐射值
     */
    void setRadiation(double radiation);

    /**
     * 增加辐射值
     * @param amount 增加量
     */
    void addRadiation(double amount);

    /**
     * 获取当前辐射通量（短时间内接收的辐射）
     * @return 通量值
     */
    double getFlux();

    /**
     * 设置辐射通量
     * @param flux 通量值
     */
    void setFlux(double flux);

    /**
     * 增加辐射通量
     * @param amount 增加量
     */
    void addFlux(double amount);

    /**
     * 获取上一次转化的辐射增量
     * @return 增量值
     */
    double getDelta();

    /**
     * 设置辐射增量
     * @param delta 增量值
     */
    void setDelta(double delta);

    /**
     * 更新辐射数据
     * 将 Flux 转化为 Radiation，并更新 Delta
     */
    void update();

    /**
     * 更新辐射数据（带防护因子）
     * 将 Flux 转化为 Radiation，考虑防护装备的减伤效果
     * @param protectionFactor 防护因子 (0.0 = 无防护, 1.0 = 完全防护)
     */
    void update(double protectionFactor);

    /**
     * 获取辐射等级 (0-10)
     * @return 辐射等级
     */
    int getRadiationLevel();

    /**
     * 检查玩家是否受到严重辐射影响
     * @return 是否严重
     */
    boolean isSeverelyRadiated();

    /**
     * 重置辐射数据
     */
    void reset();
}

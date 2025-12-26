package me.shiming.DullRadiation.capability;

import me.shiming.DullRadiation.config.RadiationConfig;
import net.minecraft.nbt.CompoundTag;

/**
 * 辐射数据实现类
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadiationData implements IRadiationData {

    private double radiation = 0.0;  // 累积辐射值
    private double flux = 0.0;       // 短期辐射通量
    private double delta = 0.0;      // 上次转化增量

    // 辐射等级阈值
    private static final double[] LEVEL_THRESHOLDS = {
        0.0,     // 等级 0: 安全
        50.0,    // 等级 1: 轻微
        100.0,   // 等级 2: 轻度
        200.0,   // 等级 3: 中度
        350.0,   // 等级 4: 中度偏高
        500.0,   // 等级 5: 重度
        700.0,   // 等级 6: 重度偏高
        1000.0,  // 等级 7: 严重
        1500.0,  // 等级 8: 严重偏高
        2500.0,  // 等级 9: 危险
        4000.0   // 等级 10: 致命
    };

    @Override
    public double getRadiation() {
        return radiation;
    }

    @Override
    public void setRadiation(double radiation) {
        this.radiation = Math.max(0.0, radiation);
    }

    @Override
    public void addRadiation(double amount) {
        this.radiation += amount;
        if (this.radiation < 0.0) {
            this.radiation = 0.0;
        }
    }

    @Override
    public double getFlux() {
        return flux;
    }

    @Override
    public void setFlux(double flux) {
        this.flux = Math.max(0.0, flux);
    }

    @Override
    public void addFlux(double amount) {
        this.flux += amount;
        if (this.flux < 0.0) {
            this.flux = 0.0;
        }
    }

    @Override
    public double getDelta() {
        return delta;
    }

    @Override
    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public void update() {
        // 默认更新：使用配置的基础转化率（无防护）
        double baseConversionRate = RadiationConfig.COMMON.fluxToRadiationRate.get();
        update(0.0); // 无防护因子
    }

    /**
     * 更新辐射数据（带防护因子）
     * @param protectionFactor 防护因子 (0.0 = 无防护, 1.0 = 完全防护)
     *                         例如：0.5 表示减少50%的转化
     */
    public void update(double protectionFactor) {
        // 从配置文件读取基础转化率
        double baseConversionRate = RadiationConfig.COMMON.fluxToRadiationRate.get();

        // 将 Flux 转化为 Radiation
        // 转化率 = 基础转化率 × (1 - 防护因子)
        double actualConversionRate = baseConversionRate * (1.0 - Math.max(0.0, Math.min(1.0, protectionFactor)));

        this.delta = this.flux * actualConversionRate;
        this.radiation += this.delta;
        this.flux = 0.0;
    }

    @Override
    public int getRadiationLevel() {
        int level = 0;
        for (int i = 0; i < LEVEL_THRESHOLDS.length; i++) {
            if (this.radiation >= LEVEL_THRESHOLDS[i]) {
                level = i;
            } else {
                break;
            }
        }
        return level;
    }

    @Override
    public boolean isSeverelyRadiated() {
        return getRadiationLevel() >= 7;
    }

    @Override
    public void reset() {
        this.radiation = 0.0;
        this.flux = 0.0;
        this.delta = 0.0;
    }

    /**
     * 从 NBT 读取数据
     */
    public void readNBT(CompoundTag nbt) {
        this.radiation = nbt.getDouble("radiation");
        this.flux = nbt.getDouble("flux");
        this.delta = nbt.getDouble("delta");
    }

    /**
     * 写入 NBT 数据
     */
    public void writeNBT(CompoundTag nbt) {
        nbt.putDouble("radiation", this.radiation);
        nbt.putDouble("flux", this.flux);
        nbt.putDouble("delta", this.delta);
    }
}

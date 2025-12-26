package me.shiming.DullRadiation.Stuff.items;

import me.shiming.DullRadiation.api.RadiationAPI;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 抗辐射药品基类
 *
 * 功能：
 * - 使用时减少辐射值
 * - 可以配置减少的辐射量
 * - 可以添加副作用（饥饿效果等）
 *
 * @author Shiming
 * @version 2.0.0
 */
public class AntiRadiationItem extends Item {

    private final double radiationReduction;
    private final boolean causeHunger;
    private final int hungerDuration;

    /**
     * 创建抗辐射药品
     *
     * @param properties 物品属性
     * @param radiationReduction 减少的辐射量
     * @param causeHunger 是否导致饥饿
     * @param hungerDuration 饥饿效果持续时间（秒），如果 causeHunger 为 true
     */
    public AntiRadiationItem(Properties properties, double radiationReduction, boolean causeHunger, int hungerDuration) {
        super(properties);
        this.radiationReduction = radiationReduction;
        this.causeHunger = causeHunger;
        this.hungerDuration = hungerDuration;
    }

    /**
     * 简化构造函数 - 不导致饥饿
     */
    public AntiRadiationItem(Properties properties, double radiationReduction) {
        this(properties, radiationReduction, false, 0);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        // 只对玩家生效
        if (!level.isClientSide && entity instanceof Player player) {
            double currentLevel = RadiationAPI.getPlayerRadiation(player);
            double newLevel = Math.max(0, currentLevel - radiationReduction);
            RadiationAPI.setPlayerRadiation(player, newLevel);

            // 如果有饥饿副作用
            if (causeHunger && hungerDuration > 0) {
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.HUNGER,
                    hungerDuration * 20, // 转换为tick (20 tick = 1秒)
                    0,
                    false,
                    false
                ));
            }
        }

        return result;
    }

    /**
     * 获取辐射减少量
     */
    public double getRadiationReduction() {
        return radiationReduction;
    }
}

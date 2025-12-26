package me.shiming.DullRadiation.Stuff.items;

import me.shiming.DullRadiation.api.RadiationAPI;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 被污染的物品
 *
 * 功能：
 * - 在物品栏中时持续给玩家增加辐射
 * - 携带越多，辐射增加越快
 *
 * @author Shiming
 * @version 2.0.0
 */
public class PollutedItem extends Item {

    private final double radiationIncreaseRate; // 每tick增加的辐射量
    private final boolean isFood; // 是否为食物（食物在食用后额外增加辐射）

    public PollutedItem(Properties properties, double radiationIncreaseRate, boolean isFood) {
        super(properties);
        this.radiationIncreaseRate = radiationIncreaseRate;
        this.isFood = isFood;
    }

    /**
     * 简化构造函数 - 用于非食物的被污染物品
     */
    public PollutedItem(Properties properties, double radiationIncreaseRate) {
        this(properties, radiationIncreaseRate, false);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        // 只在服务器端且对玩家生效
        if (!level.isClientSide && entity instanceof Player player && !player.isCreative()) {
            // 每tick增加辐射
            RadiationAPI.addPlayerRadiation(player, radiationIncreaseRate);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        // 如果是被污染的食物，食用后额外增加辐射
        if (isFood && !level.isClientSide && entity instanceof Player player) {
            double foodRadiationBonus = 50.0; // 食用后额外增加50点辐射
            RadiationAPI.addPlayerRadiation(player, foodRadiationBonus);
        }

        return result;
    }

    /**
     * 获取辐射增加速率
     */
    public double getRadiationIncreaseRate() {
        return radiationIncreaseRate;
    }
}

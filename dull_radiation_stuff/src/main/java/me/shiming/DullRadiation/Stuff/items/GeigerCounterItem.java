package me.shiming.DullRadiation.Stuff.items;

import me.shiming.DullRadiation.api.RadiationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 盖革计数器物品
 *
 * 功能：
 * - 检测当前环境辐射强度（通量）
 * - 在HUD上显示辐射通量和增量
 * - 实时反映辐射暴露速度
 *
 * @author Shiming
 * @version 2.0.0
 */
public class GeigerCounterItem extends Item {

    private final int detectionRange;
    private final boolean isAdvanced;

    public GeigerCounterItem(Properties properties, int detectionRange, boolean isAdvanced) {
        super(properties);
        this.detectionRange = detectionRange;
        this.isAdvanced = isAdvanced;
    }

    public GeigerCounterItem(Properties properties) {
        this(properties, 10, false); // 默认检测范围10格
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        // 只在客户端且实体是玩家时处理
        if (level.isClientSide && entity instanceof Player player) {
            // 当物品在主手或副手时显示辐射水平
            if (isSelected || slotId == -106) { // -106 是副手槽位
                displayRadiationLevel(player);
            }
        }
    }

    /**
     * 显示当前辐射强度（Delta - 上次转化的辐射量）
     */
    @OnlyIn(Dist.CLIENT)
    private void displayRadiationLevel(Player player) {
        double delta = RadiationAPI.getPlayerDelta(player);

        // 在HUD上显示辐射强度
        Minecraft mc = Minecraft.getInstance();
        if (mc.gui != null) {
            String message;
            if (isAdvanced) {
                // 高级版显示更精确的数值
                message = String.format("辐射强度: %.3f µSv/h", delta);
            } else {
                // 普通版显示简化的数值
                message = String.format("辐射强度: %.1f µSv/h", delta);
            }

            mc.gui.setOverlayMessage(Component.literal(message), false);
        }
    }

    /**
     * 获取检测范围
     */
    public int getDetectionRange() {
        return detectionRange;
    }

    /**
     * 是否为高级版本
     */
    public boolean isAdvanced() {
        return isAdvanced;
    }
}

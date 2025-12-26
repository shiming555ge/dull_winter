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
 * - 检测玩家当前辐射水平
 * - 在HUD上显示辐射值
 * - 高辐射时发出警告音
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
     * 显示当前辐射水平
     */
    @OnlyIn(Dist.CLIENT)
    private void displayRadiationLevel(Player player) {
        double radiation = RadiationAPI.getPlayerRadiation(player);
        int level = RadiationAPI.getRadiationLevel(player);

        // 在HUD上显示辐射水平
        Minecraft mc = Minecraft.getInstance();
        if (mc.gui != null) {
            String message;
            if (isAdvanced) {
                message = String.format("辐射: %.2f (等级 %d)", radiation, level);
            } else {
                message = String.format("辐射: %.0f", radiation);
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

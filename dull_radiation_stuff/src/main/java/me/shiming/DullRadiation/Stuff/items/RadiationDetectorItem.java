package me.shiming.DullRadiation.Stuff.items;

import me.shiming.DullRadiation.api.RadiationAPI;
import net.minecraft.ChatFormatting;
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
 * 辐射检测仪物品
 *
 * 功能：
 * - 显示玩家当前辐射等级
 * - 显示玩家累计辐射总量
 * - 提供辐射状态警告
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadiationDetectorItem extends Item {

    private final boolean isAdvanced;

    public RadiationDetectorItem(Properties properties, boolean isAdvanced) {
        super(properties);
        this.isAdvanced = isAdvanced;
    }

    public RadiationDetectorItem(Properties properties) {
        this(properties, false); // 默认为普通版
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        // 只在客户端且实体是玩家时处理
        if (level.isClientSide && entity instanceof Player player) {
            // 当物品在主手或副手时显示辐射信息
            if (isSelected || slotId == -106) { // -106 是副手槽位
                displayRadiationInfo(player);
            }
        }
    }

    /**
     * 显示辐射信息
     */
    @OnlyIn(Dist.CLIENT)
    private void displayRadiationInfo(Player player) {
        double radiation = RadiationAPI.getPlayerRadiation(player);
        int level = RadiationAPI.getRadiationLevel(player);
        double delta = RadiationAPI.getPlayerDelta(player);

        // 在HUD上显示辐射信息
        Minecraft mc = Minecraft.getInstance();
        if (mc.gui != null) {
            String message;

            if (isAdvanced) {
                // 高级版显示详细信息
                ChatFormatting color = getRadiationColor(level);
                String levelName = getRadiationLevelName(level);

                message = String.format("辐射: %.1f (%s) [%d级] 强度: %.2f",
                    radiation, levelName, level, delta);
            } else {
                // 普通版只显示等级
                String levelName = getRadiationLevelName(level);
                ChatFormatting color = getRadiationColor(level);

                message = String.format("辐射等级: %d - %s", level, levelName);
            }

            mc.gui.setOverlayMessage(Component.literal(message), false);
        }
    }

    /**
     * 获取辐射等级名称
     */
    private String getRadiationLevelName(int level) {
        return switch (level) {
            case 0 -> "安全";
            case 1 -> "轻微";
            case 2 -> "轻度";
            case 3 -> "中度";
            case 4 -> "中度偏高";
            case 5 -> "重度";
            case 6 -> "重度偏高";
            case 7 -> "严重";
            case 8 -> "严重偏高";
            case 9 -> "危险";
            case 10 -> "致命";
            default -> "未知";
        };
    }

    /**
     * 根据辐射等级获取颜色
     */
    private ChatFormatting getRadiationColor(int level) {
        if (level <= 1) return ChatFormatting.GREEN;
        if (level <= 3) return ChatFormatting.YELLOW;
        if (level <= 5) return ChatFormatting.GOLD;
        if (level <= 7) return ChatFormatting.RED;
        return ChatFormatting.DARK_RED;
    }

    /**
     * 是否为高级版本
     */
    public boolean isAdvanced() {
        return isAdvanced;
    }
}

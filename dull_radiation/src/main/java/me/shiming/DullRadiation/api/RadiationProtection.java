package me.shiming.DullRadiation.api;

import me.shiming.DullRadiation.config.RadiationConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 辐射防护系统 API
 *
 * 提供接口让其他模组或原版装备注册辐射防护能力
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadiationProtection {

    /**
     * 防护提供者接口
     */
    public interface ProtectionProvider {
        /**
         * 计算装备的防护因子
         * @param stack 物品堆
         * @param slot 装备槽
         * @param player 玩家
         * @return 防护因子 (0.0 = 无防护, 1.0 = 完全防护)
         */
        double getProtectionFactor(ItemStack stack, EquipmentSlot slot, Player player);
    }

    // 物品到防护提供者的映射
    private static final Map<Item, ProtectionProvider> PROTECTION_PROVIDERS = new HashMap<>();

    /**
     * 注册物品的防护能力
     *
     * @param item 物品
     * @param provider 防护提供者
     *
     * 使用示例：
     * <pre>
     * // 注册固定防护值
     * RadiationProtection.registerProtection(myArmorItem, (stack, slot, player) -> 0.3);
     *
     * // 根据耐久度动态计算
     * RadiationProtection.registerProtection(myHelmet, (stack, slot, player) -> {
     *     double durabilityRatio = (double)stack.getDamageValue() / stack.getMaxDamage();
     *     return 0.5 * (1.0 - durabilityRatio); // 耐久度越低防护越弱
     * });
     *
     * // 完整套装加成
     * RadiationProtection.registerProtection(radiationSuitChestplate, (stack, slot, player) -> {
     *     if (hasFullSuit(player)) {
     *         return 0.8; // 完整套装提供 80% 防护
     *     }
     *     return 0.2; // 单件只提供 20% 防护
     * });
     * </pre>
     */
    public static void registerProtection(Item item, ProtectionProvider provider) {
        PROTECTION_PROVIDERS.put(item, provider);
    }

    /**
     * 注册物品的固定防护值（便捷方法）
     *
     * @param item 物品
     * @param protectionFactor 防护因子 (0.0 - 1.0)
     *
     * 使用示例：
     * <pre>
     * // 简单注册：铁头盔提供 10% 辐射防护
     * RadiationProtection.registerProtection(Items.IRON_HELMET, 0.1);
     *
     * // 钻石胸甲提供 20% 辐射防护
     * RadiationProtection.registerProtection(Items.DIAMOND_CHESTPLATE, 0.2);
     * </pre>
     */
    public static void registerProtection(Item item, double protectionFactor) {
        registerProtection(item, (stack, slot, player) -> protectionFactor);
    }

    /**
     * 获取物品的防护因子
     *
     * @param stack 物品堆
     * @param slot 装备槽
     * @param player 玩家
     * @return 防护因子，如果物品没有注册防护则返回 0.0
     */
    public static double getProtectionFactor(ItemStack stack, EquipmentSlot slot, Player player) {
        Item item = stack.getItem();
        ProtectionProvider provider = PROTECTION_PROVIDERS.get(item);

        if (provider != null) {
            return provider.getProtectionFactor(stack, slot, player);
        }

        return 0.0; // 默认无防护
    }

    /**
     * 计算玩家总防护因子
     *
     * 防护计算方式：
     * 1. 遍历所有装备槽
     * 2. 获取每件装备的防护因子
     * 3. 使用"剩余伤害"公式叠加：totalProtection = 1 - (1-p1) * (1-p2) * (1-p3) * ...
     *
     * 例如：
     * - 头盔 0.1 (10%)
     * - 胸甲 0.2 (20%)
     * - 腿甲 0.1 (10%)
     * - 靴子 0.1 (10%)
     * 总防护 = 1 - 0.9 * 0.8 * 0.9 * 0.9 = 1 - 0.5832 = 0.4168 (约41.7%)
     *
     * @param player 玩家
     * @return 总防护因子 (0.0 - 1.0)
     */
    public static double getTotalProtection(Player player) {
        if (!RadiationConfig.COMMON.protectionEnabled.get()) {
            return RadiationConfig.COMMON.baseProtectionFactor.get();
        }

        double remainingDamage = 1.0; // 剩余伤害比例

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR || slot == EquipmentSlot.OFFHAND) {
                ItemStack stack = player.getItemBySlot(slot);
                if (!stack.isEmpty()) {
                    double protection = getProtectionFactor(stack, slot, player);
                    remainingDamage *= (1.0 - protection);
                }
            }
        }

        double totalProtection = 1.0 - remainingDamage;

        // 加上基础防护因子
        double baseProtection = RadiationConfig.COMMON.baseProtectionFactor.get();
        totalProtection = Math.min(1.0, totalProtection + baseProtection);

        return Math.max(0.0, Math.min(1.0, totalProtection));
    }

    /**
     * 检查玩家是否有完整的防辐射套装
     *
     * @param player 玩家
     * @param baseItem 套装的基础物品（任意一件）
     * @return 是否完整
     */
    public static boolean hasFullSuit(Player player, Item baseItem) {
        // 检查是否所有装备槽都注册了防护
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                ItemStack stack = player.getItemBySlot(slot);
                if (stack.isEmpty() || !PROTECTION_PROVIDERS.containsKey(stack.getItem())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 移除物品的防护注册
     *
     * @param item 物品
     */
    public static void unregisterProtection(Item item) {
        PROTECTION_PROVIDERS.remove(item);
    }

    /**
     * 清空所有防护注册
     */
    public static void clearAll() {
        PROTECTION_PROVIDERS.clear();
    }

    /**
     * 获取已注册的防护物品数量
     */
    public static int getRegisteredCount() {
        return PROTECTION_PROVIDERS.size();
    }
}

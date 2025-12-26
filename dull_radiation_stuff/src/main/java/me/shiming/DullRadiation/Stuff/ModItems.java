package me.shiming.DullRadiation.Stuff;

import me.shiming.DullRadiation.DullRadiation;
import me.shiming.DullRadiation.Stuff.items.AntiRadiationItem;
import me.shiming.DullRadiation.Stuff.items.GeigerCounterItem;
import me.shiming.DullRadiation.Stuff.items.PollutedItem;
import me.shiming.DullRadiation.Stuff.items.RadiationArmorItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 物品注册类
 *
 * @author Shiming
 * @version 2.0.0
 */
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DullRadiationStuff.MODID);

    // ========== 装备材质 ==========

    /**
     * 防毒面具材质
     */
    public static final RadiationArmorItem.RadiationArmorMaterial GAS_MASK_MATERIAL =
        new RadiationArmorItem.RadiationArmorMaterial(
            200, // 耐久度
            new int[]{1, 0, 0, 0}, // 防护值（只有头部有防护）
            15, // 附魔值
            SoundEvents.ARMOR_EQUIP_LEATHER, // 装备声音
            Ingredient.of(ModItems.RADIATION_FABRIC.get()) // 修复材料
        );

    /**
     * 辐射面罩材质
     */
    public static final RadiationArmorItem.RadiationArmorMaterial RADIATION_MASK_MATERIAL =
        new RadiationArmorItem.RadiationArmorMaterial(
            400, // 耐久度
            new int[]{2, 0, 0, 0}, // 防护值（只有头部有防护）
            25, // 附魔值
            SoundEvents.ARMOR_EQUIP_IRON, // 装备声音
            Ingredient.of(ModItems.LEAD_PLATE.get()) // 修复材料
        );

    // ========== 资源物品 ==========

    /**
     * 铀锭
     * 用途：合成防辐射装备、核燃料等
     */
    public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot",
        () -> new Item(new Item.Properties()));

    /**
     * 铅锭
     * 用途：合成防辐射装备、铅容器等
     */
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot",
        () -> new Item(new Item.Properties()));

    /**
     * 被污染的碎片
     * 危险物品，会辐射玩家
     * 每tick增加 0.05 点辐射
     */
    public static final RegistryObject<Item> POLLUTION_SCRAP = ITEMS.register("pollution_scrap",
        () -> new PollutedItem(new Item.Properties(), 0.05));

    // ========== 防护装备 ==========

    /**
     * 防毒面具
     * 提供基础辐射防护（20%）
     */
    public static final RegistryObject<Item> GAS_MASK = ITEMS.register("gas_mask",
        () -> new RadiationArmorItem(GAS_MASK_MATERIAL, net.minecraft.world.item.ArmorItem.Type.HELMET,
            new Item.Properties()));

    /**
     * 防辐射面罩
     * 更高级的面罩（35%）
     */
    public static final RegistryObject<Item> RADIATION_MASK = ITEMS.register("radiation_mask",
        () -> new RadiationArmorItem(RADIATION_MASK_MATERIAL, net.minecraft.world.item.ArmorItem.Type.HELMET,
            new Item.Properties()));

    // ========== 药品 ==========

    /**
     * 抗辐射药片
     * 减少 20 点辐射
     */
    public static final RegistryObject<Item> ANTI_RAD_PILL = ITEMS.register("anti_rad_pill",
        () -> new AntiRadiationItem(new Item.Properties()
            .stacksTo(16)
            .food(net.minecraft.world.food.Foods.APPLE), 20.0));

    /**
     * 抗辐射注射器
     * 减少 50 点辐射，立即生效
     */
    public static final RegistryObject<Item> ANTI_RAD_INJECTION = ITEMS.register("anti_rad_injection",
        () -> new AntiRadiationItem(new Item.Properties()
            .stacksTo(4)
            .food(net.minecraft.world.food.Foods.APPLE), 50.0));

    /**
     * 碘片
     * 减少 100 点辐射，但会给予饥饿效果
     */
    public static final RegistryObject<Item> IODINE_TABLET = ITEMS.register("iodine_tablet",
        () -> new AntiRadiationItem(new Item.Properties()
            .stacksTo(8)
            .food(net.minecraft.world.food.Foods.APPLE), 100.0, true, 3));

    /**
     * 抗辐射饮料
     * 减少 30 点辐射，同时补充饥饿值
     */
    public static final RegistryObject<Item> ANTI_RAD_DRINK = ITEMS.register("anti_rad_drink",
        () -> new AntiRadiationItem(new Item.Properties()
            .stacksTo(8)
            .food(net.minecraft.world.food.Foods.MUSHROOM_STEW), 30.0));

    /**
     * 罐头食品
     * 安全的食物来源
     */
    public static final RegistryObject<Item> CANNED_FOOD = ITEMS.register("canned_food",
        () -> new Item(new Item.Properties()
            .stacksTo(16)
            .food(net.minecraft.world.food.Foods.COOKED_BEEF)));

    /**
     * 被污染的罐头
     * 危险食物，食用后会增加辐射
     * 物品栏中每tick增加 0.1 点辐射，食用后额外增加 50 点
     */
    public static final RegistryObject<Item> CONTAMINATED_CAN = ITEMS.register("contaminated_can",
        () -> new PollutedItem(new Item.Properties()
            .stacksTo(16)
            .food(net.minecraft.world.food.Foods.ROTTEN_FLESH), 0.1, true));

    /**
     * 盖革计数器
     * 用于检测辐射水平的手持设备
     * 检测范围: 10 格
     */
    public static final RegistryObject<Item> GEIGER_COUNTER = ITEMS.register("geiger_counter",
        () -> new GeigerCounterItem(new Item.Properties()
            .stacksTo(1), 10, false));

    /**
     * 盖革计数器高级版
     * 更精确的检测，更大的范围
     * 检测范围: 25 格，显示详细信息
     */
    public static final RegistryObject<Item> ADVANCED_GEIGER_COUNTER = ITEMS.register("advanced_geiger_counter",
        () -> new GeigerCounterItem(new Item.Properties()
            .stacksTo(1), 25, true));

    /**
     * 辐射检测仪
     * 可以放置的检测设备
     */
    public static final RegistryObject<Item> RADIATION_DETECTOR = ITEMS.register("radiation_detector",
        () -> new Item(new Item.Properties()
            .stacksTo(64)));

    // ========== 工具材料 ==========

    /**
     * 辐射防护布料
     * 用于合成防护装备
     */
    public static final RegistryObject<Item> RADIATION_FABRIC = ITEMS.register("radiation_fabric",
        () -> new Item(new Item.Properties()));

    /**
     * 铅板
     * 用于合成防护装备和铅容器
     */
    public static final RegistryObject<Item> LEAD_PLATE = ITEMS.register("lead_plate",
        () -> new Item(new Item.Properties()));

    /**
     * 过滤器
     * 用于制作防毒面具和空气过滤器
     */
    public static final RegistryObject<Item> FILTER = ITEMS.register("filter",
        () -> new Item(new Item.Properties()
            .stacksTo(16)));

    /**
     * 活性炭过滤器
     * 更高级的过滤器
     */
    public static final RegistryObject<Item> ACTIVATED_CHARCOAL_FILTER = ITEMS.register("activated_charcoal_filter",
        () -> new Item(new Item.Properties()
            .stacksTo(16)));

    /**
     * 注册所有物品到事件总线
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        DullRadiation.LOGGER.info("Registered all items for Dull Radiation: Stuff Addition");
    }
}

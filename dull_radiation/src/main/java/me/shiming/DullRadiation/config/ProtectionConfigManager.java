package me.shiming.DullRadiation.Stuff.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.shiming.DullRadiation.DullRadiation;
import me.shiming.DullRadiation.api.RadiationProtection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 装备防护配置管理器
 *
 * 从配置文件读取装备的辐射防护能力配置
 * 配置文件位置: config/dull_radiation/protection_config.json
 *
 * @author Shiming
 * @version 2.0.0
 */
public class ProtectionConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/dull_radiation/protection_config.json");

    /**
     * 加载装备防护配置
     */
    public static void loadProtectionConfig() {
        if (!CONFIG_FILE.exists()) {
            createDefaultConfig();
            return;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject config = GSON.fromJson(reader, JsonObject.class);
            parseConfig(config);
            DullRadiation.LOGGER.info("Loaded radiation protection config from {}", CONFIG_FILE.getPath());
        } catch (Exception e) {
            DullRadiation.LOGGER.error("Failed to load protection config, using defaults", e);
            createDefaultConfig();
        }
    }

    /**
     * 解析配置文件
     */
    private static void parseConfig(JsonObject config) {
        int registeredCount = 0;

        // 遍历所有模组
        for (String modId : config.keySet()) {
            if (modId.equals("_comment")) continue; // 跳过注释

            // 检查模组是否已加载
            if (!modId.equals("minecraft") && !ModList.get().isLoaded(modId)) {
                DullRadiation.LOGGER.debug("Skipping items from unloaded mod: {}", modId);
                continue;
            }

            JsonObject modConfig = config.getAsJsonObject(modId);
            registeredCount += parseModItems(modId, modConfig);
        }

        DullRadiation.LOGGER.info("Registered {} items with radiation protection", registeredCount);
    }

    /**
     * 解析单个模组的物品配置
     */
    private static int parseModItems(String modId, JsonObject modConfig) {
        int count = 0;

        for (String itemId : modConfig.keySet()) {
            if (itemId.equals("_comment")) continue;

            JsonObject itemConfig = modConfig.getAsJsonObject(itemId);
            double protection = itemConfig.get("protection").getAsDouble();

            // 注册物品防护
            registerItemProtection(modId, itemId, protection);
            count++;
        }

        return count;
    }

    /**
     * 注册单个物品的防护
     */
    private static void registerItemProtection(String modId, String itemId, double protection) {
        ResourceLocation itemLocation = ResourceLocation.fromNamespaceAndPath(modId, itemId);
        Item item = ForgeRegistries.ITEMS.getValue(itemLocation);

        if (item != null) {
            RadiationProtection.registerProtection(item, protection);
            DullRadiation.LOGGER.debug("Registered protection: {} = {}", itemLocation, protection);
        } else {
            DullRadiation.LOGGER.warn("Item not found: {}", itemLocation);
        }
    }

    /**
     * 创建默认配置文件
     */
    private static void createDefaultConfig() {
        JsonObject config = new JsonObject();

        // 添加说明注释
        config.addProperty("_comment", createComment());

        // Minecraft 原版物品示例
        JsonObject minecraft = new JsonObject();
        minecraft.addProperty("_comment", "Minecraft 原版装备防护配置");
        minecraft.addProperty("iron_helmet", 0.05);
        minecraft.addProperty("iron_chestplate", 0.10);
        minecraft.addProperty("iron_leggings", 0.08);
        minecraft.addProperty("iron_boots", 0.05);
        minecraft.addProperty("diamond_helmet", 0.10);
        minecraft.addProperty("diamond_chestplate", 0.20);
        minecraft.addProperty("diamond_leggings", 0.15);
        minecraft.addProperty("diamond_boots", 0.10);
        minecraft.addProperty("netherite_helmet", 0.15);
        minecraft.addProperty("netherite_chestplate", 0.25);
        minecraft.addProperty("netherite_leggings", 0.20);
        minecraft.addProperty("netherite_boots", 0.15);
        minecraft.addProperty("golden_helmet", 0.03);
        minecraft.addProperty("golden_chestplate", 0.06);
        minecraft.addProperty("golden_leggings", 0.05);
        minecraft.addProperty("golden_boots", 0.03);
        minecraft.addProperty("turtle_helmet", 0.15);
        config.add("minecraft", minecraft);

        // 示例：其他模组（玩家可以参考添加）
        JsonObject exampleMod = new JsonObject();
        exampleMod.addProperty("_comment", "示例：其他模组的物品防护配置");
        exampleMod.addProperty("example_item", 0.5);
        config.add("example_modid", exampleMod);

        // 保存配置文件
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(config, writer);
            }
            DullRadiation.LOGGER.info("Created default protection config at {}", CONFIG_FILE.getPath());
        } catch (IOException e) {
            DullRadiation.LOGGER.error("Failed to create default config", e);
        }
    }

    /**
     * 创建配置说明
     */
    private static String createComment() {
        return """
        ============================================
        辐射防护配置文件
        Dull Radiation - Protection Config
        ============================================

        说明：
        - 在此配置文件中，你可以为任何模组（包括原版）的装备设置辐射防护能力
        - protection 值范围：0.0（无防护）到 1.0（完全防护）
        - 例如：0.5 表示减少 50% 的辐射转化
        - 防护计算使用"剩余伤害"公式叠加，所以多件装备的防护会叠加

        格式：
        {
          "modid": {
            "item_id": {
              "protection": 防护值
            }
          }
        }

        示例：
        {
          "minecraft": {
            "diamond_helmet": 0.1
          }
        }

        注意：
        1. 修改配置后需要重启游戏生效
        2. 物品 ID 必须是注册表名称（格式：modid:itemid）
        3. 如果物品不存在，会在日志中显示警告
        4. 可以通过检查游戏日志（logs/latest.log）查看加载情况

        获取物品 ID 的方法：
        1. 在游戏中拿着物品，使用 /minecraft:iteminfo 命令（如果有）
        2. 查看 JEI/REI 物品显示
        3. 查看模组的 Wiki 或文档

        配置更新：
        - 每次启动时会自动生成默认配置
        - 你可以修改此文件来定制防护值
        - 删除此文件会重新生成默认配置
        """;
    }
}

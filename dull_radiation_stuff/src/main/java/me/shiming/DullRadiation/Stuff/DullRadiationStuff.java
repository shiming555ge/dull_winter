package me.shiming.DullRadiation.Stuff;

import me.shiming.DullRadiation.DullRadiation;
import me.shiming.DullRadiation.Stuff.config.ProtectionConfigManager;
import me.shiming.DullRadiation.api.RadiationProtection;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dull Radiation: Stuff Addition - 物品附加模块
 *
 * 功能：
 * - 基础物品和方块（铀、铅、辐射源等）
 * - 防护装备（防辐射服、防毒面具等）
 * - 抗辐射物品（药品、食品等）
 * - 装备防护配置系统
 *
 * @author Shiming
 * @version 2.0.0
 */
@SuppressWarnings("deprecation")
@Mod(DullRadiationStuff.MODID)
public class DullRadiationStuff {

    public static final String MODID = "dull_radiation_stuff";
    public static final String VERSION = "2.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @SuppressWarnings({"removal", "deprecation"})
    public DullRadiationStuff() {
        // 注册 Forge 事件总线
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // 注册物品、方块和创造标签页
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        CreativeTabs.register(modEventBus);

        // 注册模组事件
        modEventBus.addListener(this::commonSetup);

        LOGGER.info("Dull Radiation: Stuff Addition {} is loading!", VERSION);
    }

    /**
     * 公共设置阶段
     */
    private void commonSetup(net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {
        // 加载装备防护配置
        ProtectionConfigManager.loadProtectionConfig();

        // 注册模组内物品的防护能力
        registerProtectionItems();

        LOGGER.info("Dull Radiation: Stuff Addition common setup completed!");
    }

    /**
     * 注册防护装备的辐射防护能力
     */
    private void registerProtectionItems() {
        // 防毒面具：20% 防护
        RadiationProtection.registerProtection(ModItems.GAS_MASK.get(), 0.20);

        // 辐射面罩：35% 防护
        RadiationProtection.registerProtection(ModItems.RADIATION_MASK.get(), 0.35);

        DullRadiation.LOGGER.info("Registered radiation protection for {} items", 2);
    }
}

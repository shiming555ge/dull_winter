package me.shiming.DullRadiation;

import me.shiming.DullRadiation.api.RadiationAPI;
import me.shiming.DullRadiation.capability.RadiationCapability;
import me.shiming.DullRadiation.config.RadiationConfig;
import me.shiming.DullRadiation.event.RadiationEventHandler;
import me.shiming.DullRadiation.network.RadiationDataSyncPacket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dull Radiation - 核战后末日辐射系统
 *
 * 核心功能：
 * - 玩家辐射数据管理（Capability）
 * - 辐射计算和效果应用
 * - 区块辐射系统
 * - 防护系统
 *
 * @author Shiming
 * @version 2.0.0
 */
@Mod(DullRadiation.MODID)
public class DullRadiation {

    public static final String MODID = "dull_radiation";
    public static final String VERSION = "2.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @SuppressWarnings({"removal", "deprecation"})
    public DullRadiation() {
        // 注册配置
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RadiationConfig.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RadiationConfig.CLIENT_SPEC);

        // 注册 Forge 事件总线
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        // 注册模组事件
        modEventBus.addListener(this::commonSetup);

        // 注册游戏事件
        forgeEventBus.register(new RadiationEventHandler());

        LOGGER.info("Dull Radiation {} is loading!", VERSION);
    }

    /**
     * 公共设置阶段
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        // 注册网络包
        RadiationDataSyncPacket.register();

        // 初始化 API
        RadiationAPI.init();

        LOGGER.info("Dull Radiation common setup completed!");
    }
}

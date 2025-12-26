package me.shiming.DullRadiation.capability;

import me.shiming.DullRadiation.DullRadiation;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * 辐射 Capability 注册类
 *
 * 负责：注册 Capability 到 Forge 系统
 *
 * @author Shiming
 * @version 2.0.0
 */
@Mod.EventBusSubscriber(modid = DullRadiation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RadiationCapabilityRegistrar {

    /**
     * 注册 Capability
     */
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IRadiationData.class);
        DullRadiation.LOGGER.info("Registered Radiation Capability");
    }
}

package me.shiming.DullRadiation.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * 辐射系统配置
 *
 * 配置文件位置：
 * - 服务端/客户端通用：config/dull_radiation-common.toml
 * - 客户端专用：config/dull_radiation-client.toml
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadiationConfig {

    // ========== 通用配置 ==========
    public static final ForgeConfigSpec COMMON_SPEC;

    public static final CommonConfig COMMON;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        COMMON = new CommonConfig(builder);
        COMMON_SPEC = builder.build();
    }

    public static class CommonConfig {
        // 辐射系统开关
        public final ForgeConfigSpec.BooleanValue radiationEnabled;
        public final ForgeConfigSpec.BooleanValue naturalDecayEnabled;

        // 辐射计算参数
        public final ForgeConfigSpec.ConfigValue<Double> fluxToRadiationRate;
        public final ForgeConfigSpec.ConfigValue<Double> radiationDecayRate;
        public final ForgeConfigSpec.ConfigValue<Integer> updateInterval;

        // 辐射效果
        public final ForgeConfigSpec.BooleanValue effectsEnabled;
        public final ForgeConfigSpec.ConfigValue<Double> effectMultiplier;
        public final ForgeConfigSpec.ConfigValue<Integer> severeRadiationThreshold;

        // 防护系统
        public final ForgeConfigSpec.BooleanValue protectionEnabled;
        public final ForgeConfigSpec.ConfigValue<Double> baseProtectionFactor;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("General");

            radiationEnabled = builder
                    .comment("Enable/disable the radiation system")
                    .define("radiationEnabled", true);

            naturalDecayEnabled = builder
                    .comment("Enable natural radiation decay over time")
                    .define("naturalDecayEnabled", true);

            builder.pop();

            builder.push("Radiation Calculation");

            fluxToRadiationRate = builder
                    .comment("Rate at which flux converts to radiation (0.0 - 1.0)")
                    .defineInRange("fluxToRadiationRate", 0.2, 0.0, 1.0);

            radiationDecayRate = builder
                    .comment("Amount of radiation decayed per tick (when natural decay is enabled)")
                    .defineInRange("radiationDecayRate", 0.01, 0.0, 1.0);

            updateInterval = builder
                    .comment("Radiation update interval in ticks (20 ticks = 1 second)")
                    .defineInRange("updateInterval", 20, 1, 1200);

            builder.pop();

            builder.push("Radiation Effects");

            effectsEnabled = builder
                    .comment("Enable/disable radiation effects on players")
                    .define("effectsEnabled", true);

            effectMultiplier = builder
                    .comment("Multiplier for radiation effect intensity")
                    .defineInRange("effectMultiplier", 1.0, 0.0, 10.0);

            severeRadiationThreshold = builder
                    .comment("Radiation level threshold for severe effects (0-10)")
                    .defineInRange("severeRadiationThreshold", 7, 0, 10);

            builder.pop();

            builder.push("Protection System");

            protectionEnabled = builder
                    .comment("Enable/disable radiation protection system")
                    .define("protectionEnabled", true);

            baseProtectionFactor = builder
                    .comment("Base protection factor (0.0 - 1.0, where 1.0 = 100% protection)")
                    .defineInRange("baseProtectionFactor", 0.0, 0.0, 1.0);

            builder.pop();
        }
    }

    // ========== 客户端配置 ==========
    public static final ForgeConfigSpec CLIENT_SPEC;

    public static final ClientConfig CLIENT;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        CLIENT = new ClientConfig(builder);
        CLIENT_SPEC = builder.build();
    }

    public static class ClientConfig {
        // UI 设置
        public final ForgeConfigSpec.BooleanValue showRadiationHUD;
        public final ForgeConfigSpec.ConfigValue<Double> hudPosX;
        public final ForgeConfigSpec.ConfigValue<Double> hudPosY;
        public final ForgeConfigSpec.ConfigValue<Double> hudScale;

        // 音效设置
        public final ForgeConfigSpec.BooleanValue geigerCounterEnabled;
        public final ForgeConfigSpec.ConfigValue<Double> geigerVolume;

        // 视觉效果
        public final ForgeConfigSpec.BooleanValue radiationOverlayEnabled;
        public final ForgeConfigSpec.ConfigValue<Double> overlayOpacity;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("HUD");

            showRadiationHUD = builder
                    .comment("Show radiation level HUD")
                    .define("showRadiationHUD", true);

            hudPosX = builder
                    .comment("HUD position X (0.0 - 1.0, relative to screen width)")
                    .defineInRange("hudPosX", 0.02, 0.0, 1.0);

            hudPosY = builder
                    .comment("HUD position Y (0.0 - 1.0, relative to screen height)")
                    .defineInRange("hudPosY", 0.02, 0.0, 1.0);

            hudScale = builder
                    .comment("HUD scale")
                    .defineInRange("hudScale", 1.0, 0.5, 3.0);

            builder.pop();

            builder.push("Sound");

            geigerCounterEnabled = builder
                    .comment("Enable Geiger counter sounds")
                    .define("geigerCounterEnabled", true);

            geigerVolume = builder
                    .comment("Geiger counter volume (0.0 - 1.0)")
                    .defineInRange("geigerVolume", 0.7, 0.0, 1.0);

            builder.pop();

            builder.push("Visual Effects");

            radiationOverlayEnabled = builder
                    .comment("Enable radiation visual overlay")
                    .define("radiationOverlayEnabled", true);

            overlayOpacity = builder
                    .comment("Radiation overlay opacity (0.0 - 1.0)")
                    .defineInRange("overlayOpacity", 0.3, 0.0, 1.0);

            builder.pop();
        }
    }
}

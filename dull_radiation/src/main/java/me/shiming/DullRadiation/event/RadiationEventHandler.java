package me.shiming.DullRadiation.event;

import me.shiming.DullRadiation.DullRadiation;
import me.shiming.DullRadiation.api.RadiationAPI;
import me.shiming.DullRadiation.api.RadiationProtection;
import me.shiming.DullRadiation.capability.IRadiationData;
import me.shiming.DullRadiation.capability.RadiationCapability;
import me.shiming.DullRadiation.config.RadiationConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 辐射事件处理器
 *
 * 处理以下事件：
 * - 玩家 tick 更新辐射数据
 * - 应用辐射效果
 * - 玩家克隆/重生时保留辐射数据
 *
 * @author Shiming
 * @version 2.0.0
 */
@Mod.EventBusSubscriber(modid = DullRadiation.MODID)
public class RadiationEventHandler {

    // 辐射效果配置
    private static final int POISON_THRESHOLD = 4;    // 辐射等级 >= 4 时中毒
    private static final int NAUSEA_THRESHOLD = 5;    // 辐射等级 >= 5 时恶心
    private static final int WEAKNESS_THRESHOLD = 6;  // 辐射等级 >= 6 时虚弱
    private static final int SLOWNESS_THRESHOLD = 7;  // 辐射等级 >= 7 时缓慢
    private static final int HUNGER_THRESHOLD = 3;    // 辐射等级 >= 3 时饥饿

    /**
     * 玩家 tick 事件 - 更新辐射数据和效果
     */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 只在服务端处理，且每秒更新一次（20 ticks）
        if (event.side.isServer() && event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            long gameTime = player.level().getGameTime();

            // 每 20 ticks（1秒）更新一次
            if (gameTime % RadiationConfig.COMMON.updateInterval.get() == 0) {
                updateRadiation(player);
            }
        }
    }

    /**
     * 更新玩家辐射数据和效果
     */
    private static void updateRadiation(Player player) {
        // 检查辐射系统是否启用
        if (!RadiationConfig.COMMON.radiationEnabled.get()) {
            return;
        }

        // 获取玩家辐射数据
        RadiationAPI.getRadiationData(player).ifPresent(data -> {
            // 计算玩家的总防护因子（基于装备）
            double protectionFactor = RadiationProtection.getTotalProtection(player);

            // 更新辐射数据（Flux -> Radiation），考虑防护
            data.update(protectionFactor);

            // 自然衰减
            if (RadiationConfig.COMMON.naturalDecayEnabled.get()) {
                double decay = RadiationConfig.COMMON.radiationDecayRate.get();
                data.addRadiation(-decay);
            }

            // 应用辐射效果
            if (RadiationConfig.COMMON.effectsEnabled.get()) {
                applyRadiationEffects(player, data);
            }

            // 严重辐射伤害
            if (data.isSeverelyRadiated()) {
                applyRadiationDamage(player, data);
            }
        });
    }

    /**
     * 应用辐射效果
     */
    private static void applyRadiationEffects(Player player, IRadiationData data) {
        int level = data.getRadiationLevel();
        double multiplier = RadiationConfig.COMMON.effectMultiplier.get();

        // 根据辐射等级应用不同的效果
        if (level >= POISON_THRESHOLD) {
            applyEffect(player, MobEffects.POISON, calculateDuration(level, 100), calculateLevel(level, 0), multiplier);
        }

        if (level >= NAUSEA_THRESHOLD) {
            applyEffect(player, MobEffects.CONFUSION, calculateDuration(level, 200), 0, multiplier);
        }

        if (level >= WEAKNESS_THRESHOLD) {
            applyEffect(player, MobEffects.WEAKNESS, calculateDuration(level, 200), calculateLevel(level, 0), multiplier);
        }

        if (level >= SLOWNESS_THRESHOLD) {
            applyEffect(player, MobEffects.MOVEMENT_SLOWDOWN, calculateDuration(level, 150), calculateLevel(level, 1), multiplier);
        }

        if (level >= HUNGER_THRESHOLD) {
            applyEffect(player, MobEffects.HUNGER, calculateDuration(level, 100), calculateLevel(level, 0), multiplier);
        }
    }

    /**
     * 应用药水效果
     */
    private static void applyEffect(Player player, net.minecraft.world.effect.MobEffect effect, int duration, int amplifier, double multiplier) {
        // 如果玩家已经有这个效果且时间更长，则不覆盖
        MobEffectInstance existing = player.getEffect(effect);
        if (existing != null && existing.getDuration() > duration) {
            return;
        }

        // 应用效果
        int finalDuration = (int) (duration * multiplier);
        int finalAmplifier = (int) (amplifier * multiplier);

        player.addEffect(new MobEffectInstance(effect, finalDuration, finalAmplifier, false, false));
    }

    /**
     * 计算效果持续时间
     */
    private static int calculateDuration(int radiationLevel, int baseDuration) {
        // 辐射等级越高，持续时间越长
        return baseDuration + (radiationLevel * 20);
    }

    /**
     * 计算效果等级
     */
    private static int calculateLevel(int radiationLevel, int baseLevel) {
        // 每 3 个辐射等级增加 1 级效果
        return baseLevel + (radiationLevel / 3);
    }

    /**
     * 应用严重辐射伤害
     */
    private static void applyRadiationDamage(Player player, IRadiationData data) {
        int level = data.getRadiationLevel();

        // 计算伤害：辐射等级越高，伤害越大
        // 等级 7-8: 轻微伤害
        // 等级 9-10: 严重伤害
        float damage = 0.0f;

        if (level >= 9) {
            damage = 2.0f; // 1 心
        } else if (level == 8) {
            damage = 1.0f; // 半心
        } else if (level == 7) {
            damage = 0.5f; // 四分之一心
        }

        if (damage > 0.0f) {
            // 应用伤害（忽略护甲，因为辐射是穿透性伤害）
            player.hurt(player.level().damageSources().magic(), damage);
        }
    }

    /**
     * 玩家克隆事件（如末地传送、返回主世界）
     * 保留辐射数据
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            // 如果不是死亡，复制辐射数据
            event.getOriginal().getCapability(RadiationCapability.RADIATION_CAPABILITY).ifPresent(originalData -> {
                event.getEntity().getCapability(RadiationCapability.RADIATION_CAPABILITY).ifPresent(newData -> {
                    // 复制数据
                    newData.setRadiation(originalData.getRadiation());
                    newData.setFlux(originalData.getFlux());
                    newData.setDelta(originalData.getDelta());
                });
            });
        } else {
            // 如果是死亡，可以选择保留部分辐射数据
            event.getOriginal().getCapability(RadiationCapability.RADIATION_CAPABILITY).ifPresent(originalData -> {
                event.getEntity().getCapability(RadiationCapability.RADIATION_CAPABILITY).ifPresent(newData -> {
                    // 保留 50% 的辐射值
                    newData.setRadiation(originalData.getRadiation() * 0.5);
                    // Flux 和 Delta 重置
                    newData.setFlux(0.0);
                    newData.setDelta(0.0);
                });
            });
        }
    }

    /**
     * 玩家登录事件
     * 同步辐射数据到客户端
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // TODO: 实现网络包同步
        DullRadiation.LOGGER.debug("Player {} logged in, syncing radiation data", event.getEntity().getName());
    }

    /**
     * 玩家登出事件
     */
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        DullRadiation.LOGGER.debug("Player {} logged out", event.getEntity().getName());
    }
}

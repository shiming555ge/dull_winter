# Dull Radiation API 使用文档

## 概述

Dull Radiation 是一个核战后末日辐射系统的核心模组，提供完整的玩家辐射数据管理、计算和效果应用。

## 核心功能

### 1. 辐射数据系统

每个玩家都有三个核心辐射数据：

- **Radiation（辐射值）**: 玩家累积的辐射总量
- **Flux（通量）**: 短时间内接收的辐射量
- **Delta（增量）**: 上一次从 Flux 转化为 Radiation 的量

### 2. 辐射等级系统

辐射等级从 0 到 10，每个等级对应不同的效果：

| 等级 | 辐射值范围 | 效果 |
|------|-----------|------|
| 0 | 0-49 | 安全 |
| 1 | 50-99 | 轻微 |
| 2 | 100-199 | 轻度 |
| 3 | 200-349 | 饥饿效果 |
| 4 | 350-499 | 中毒 |
| 5 | 500-699 | 恶心 |
| 6 | 700-999 | 虚弱 |
| 7 | 1000-1499 | 缓慢 + 轻微伤害 |
| 8 | 1500-2499 | 中等伤害 |
| 9 | 2500-3999 | 严重伤害 |
| 10 | 4000+ | 致命伤害 |

## API 使用方法

### 基础操作

```java
import me.shiming.DullRadiation.api.RadiationAPI;
import net.minecraft.world.entity.player.Player;

// 获取玩家当前辐射值
double radiation = RadiationAPI.getPlayerRadiation(player);

// 增加玩家辐射
RadiationAPI.addPlayerRadiation(player, 10.0);

// 设置玩家辐射值
RadiationAPI.setPlayerRadiation(player, 100.0);

// 增加辐射通量（短时间内接收的辐射）
RadiationAPI.addPlayerFlux(player, 5.0);
```

### 获取辐射等级

```java
// 获取辐射等级 (0-10)
int level = RadiationAPI.getRadiationLevel(player);

// 检查是否严重辐射（等级 >= 7）
boolean severe = RadiationAPI.isSeverelyRadiated(player);
```

### 辐射数据管理

```java
// 更新辐射数据（将 Flux 转化为 Radiation）
RadiationAPI.updateRadiationData(player);

// 重置辐射数据
RadiationAPI.resetRadiationData(player);

// 检查玩家是否有辐射数据
boolean hasData = RadiationAPI.hasRadiationData(player);
```

## 配置说明

配置文件位于 `config/dull_radiation-common.toml` 和 `config/dull_radiation-client.toml`

### 通用配置 (common)

```toml
# 辐射系统开关
radiationEnabled = true

# 自然辐射衰减
naturalDecayEnabled = true
radiationDecayRate = 0.01

# Flux 转化为 Radiation 的速率
fluxToRadiationRate = 0.2

# 更新间隔（tick）
updateInterval = 20

# 辐射效果
effectsEnabled = true
effectMultiplier = 1.0
severeRadiationThreshold = 7
```

### 客户端配置 (client)

```toml
# HUD 显示
showRadiationHUD = true
hudPosX = 0.02
hudPosY = 0.02
hudScale = 1.0

# 盖革计数器音效
geigerCounterEnabled = true
geigerVolume = 0.7

# 辐射视觉效果
radiationOverlayEnabled = true
overlayOpacity = 0.3
```

## 给其他模组开发者的集成示例

### 创建辐射方块

```java
public class RadioactiveBlock extends Block {

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            // 对站在方块上的玩家增加辐射
            RadiationAPI.addPlayerFlux(player, 0.5); // 每tick增加0.5通量
        }
        super.stepOn(level, pos, state, entity);
    }
}
```

### 创建防辐射装备

```java
public class RadiationArmorItem extends ArmorItem {

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            // 装备时每tick减少辐射通量
            double currentFlux = RadiationAPI.getPlayerFlux(player);
            if (currentFlux > 0) {
                // 减少50%的通量
                RadiationAPI.addPlayerFlux(player, -currentFlux * 0.5);
            }
        }
    }
}
```

### 创建抗辐射物品

```java
public class AntiRadPillItem extends Item {

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            // 减少50点辐射
            double current = RadiationAPI.getPlayerRadiation(player);
            RadiationAPI.setPlayerRadiation(player, Math.max(0, current - 50));
        }
        return super.finishUsingItem(stack, level, entity);
    }
}
```

## 依赖说明

- **Forge**: 47.4.13
- **Minecraft**: 1.20.1
- **Java**: 17

## 技术架构

```
me.shiming.DullRadiation/
├── DullRadiation.java          # 主类
├── api/
│   └── RadiationAPI.java        # 公共API接口
├── capability/
│   ├── IRadiationData.java      # 辐射数据接口
│   ├── RadiationData.java       # 数据实现类
│   └── RadiationCapability.java # Capability管理
├── config/
│   └── RadiationConfig.java     # 配置类
├── event/
│   └── RadiationEventHandler.java # 事件处理
└── network/
    └── RadiationDataSyncPacket.java # 网络包
```

## 版本历史

- **2.0.0**: 完全重写，模块化架构，清晰的API接口

## 许可证

MIT License

## 作者

Shiming

## 支持

如有问题或建议，请提交 Issue 或 Pull Request。

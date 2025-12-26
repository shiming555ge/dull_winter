# 钝色之冬（Dull Winter）

> **一个完整的核战争后末日 Minecraft 模组项目**

**版本**: 1.20.1 | **Forge**: 47.4.13

---

## 项目愿景

构建一个沉浸式的核战后末日生存体验，玩家将在充满辐射、废墟和变异生物的世界中求生。模组采用模块化设计，核心辐射系统与各功能模块分离，便于维护和扩展。

---

## 模块架构设计

### 1. 钝色辐射（DullRadiation）- 核心模块

**依赖关系**: 所有其他模块的基础

#### 功能模块

```
me.shiming.DullRadiation/
├── Capabilities/
│   ├── PlayerRadiationData.java          # 玩家辐射数据（已有）
│   ├── ChunkRadiationData.java          # 区块辐射数据
│   └── WorldRadiationManager.java       # 世界辐射管理器
├── Api/
│   ├── IRadioactive.java                # 辐射接口
│   ├── IProtection.java                 # 防护接口
│   └── RadiationAPI.java                # 辐射API
├── Event/
│   ├── RadiationEventHandler.java       # 辐射事件处理
│   └── CapabilityEventHandler.java      # Capability注册
├── Util/
│   ├── Pollution.java                   # 污染计算（已有）
│   ├── MathUtil.java                    # 数学工具（已有）
│   └── RadiationCalculator.java         # 辐射计算器
├── Config/
│   ├── RadiationConfig.java             # 辐射配置
│   └── ClientConfig.java                # 客户端配置
└── Network/
    ├── RadiationDataPacket.java         # 辐射数据包
    └── RadiationSyncPacket.java         # 辐射同步包
```

#### 核心功能

**1. 辐射系统**
- 玩家辐射累积（Radiation）- 长期辐射值
- 辐射通量（Flux）- 短期辐射暴露
- 辐射增量（Delta）- 每tick变化量
- 辐射等级系统（0-10级）
- 辐射效果：恶心、虚弱、中毒、死亡

**2. 区块辐射**
- 每个区块独立辐射值
- 辐射扩散机制
- 辐射衰减系统
- 辐射热点区域

**3. 防护系统**
- 防护等级计算
- 盖革计数器UI
- 辐射警告系统

#### 需要的资源文件

**代码实现需要**:
- 网络包处理类
- 区块辐射存储系统
- 辐射扩散算法

---

### 2. 钝色辐射：物品附加（DullRadiationStuffAddition）

**依赖**: DullRadiation

#### 功能模块

```
me.shiming.DullRadiation.Stuff/
├── Items/
│   ├── Resource/
│   │   ├── PollutionScrapItem.java      # 被污染的碎片（已有）
│   │   ├── UraniumIngot.java            # 铀锭
│   │   ├── PlutoniumIngot.java          # 钚锭
│   │   └── LeadIngot.java               # 铅锭
│   ├── Tool/
│   │   ├── GeigerCounterItem.java       # 盖革计数器
│   │   ├── RadiationSuitItem.java       # 辐射防护服
│   │   └── GasMaskItem.java             # 防毒面具
│   ├── Medicine/
│   │   ├── AntiRadInjection.java        # 抗辐射药剂
│   │   ├── AntiRadPill.java             # 抗辐射药片
│   │   └── AntiRadDrink.java            # 抗辐射饮料
│   └── Food/
│       ├── ContaminatedFood.java        # 被污染的食物
│       └── CannedFood.java              # 罐头食品
├── Blocks/
│   ├── Ore/
│   │   ├── UraniumOreBlock.java         # 铀矿
│   │   └── LeadOreBlock.java            # 铅矿
│   ├── Radioactive/
│   │   ├── RadioactiveSourceBlock.java  # 放射源（已有actinogen）
│   │   ├── NuclearWasteBlock.java       # 核废料块
│   │   └── ContaminatedSoilBlock.java   # 被污染的土壤
│   └── Crafting/
│       ├── LeadChestBlock.java          # 铅制箱子（防辐射）
│       └── DecontaminationTable.java    # 去污台
└── Recipe/
    ├── CraftingTableRecipes.java        # 工作台配方
    └── SmeltingRecipes.java             # 熔炉配方
```

#### 需要的资源文件

**纹理资源** (`assets/dull_radiation/textures/`):
```
item/
├── uranium_ingot.png          # 铀锭
├── plutonium_ingot.png        # 钚锭
├── lead_ingot.png             # 铅锭
├── geiger_counter.png         # 盖革计数器
├── radiation_suit.png         # 防护服
├── gas_mask.png               # 防毒面具
├── anti_rad_injection.png     # 抗辐射注射
├── anti_rad_pill.png          # 抗辐射药片
├── canned_food.png            # 罐头食品
└── contaminated_food.png      # 被污染的食物

block/
├── uranium_ore.png            # 铀矿
├── lead_ore.png               # 铅矿
├── nuclear_waste.png          # 核废料
├── contaminated_soil.png      # 被污染的土壤
└── lead_chest.png             # 铅制箱子
```

**模型文件** (`assets/dull_radiation/models/`):
- 所有物品的 JSON 模型
- 所有方块的 JSON 模型

**语言文件** (`assets/dull_radiation/lang/`):
- 所有物品的中英文翻译

---

### 3. 钝色辐射：结构（DullRadiationStructures）

**依赖**: DullRadiation, DullRadiationStuffAddition, Create (可选但推荐)

> **说明**: 本模组不包含废墟城市结构，整合包采用 [The Lost Cities](https://www.curseforge.com/minecraft/mc-mods/the-lost-cities) 模组提供城市生成。
> 重点实现连接各城市的铁路系统，替换 The Lost Cities 的地下地铁结构，与 [Create](https://modrinth.com/mod/create) 机械动力模组深度联动。

#### 功能模块

```
me.shiming.DullRadiation.Structures/
├── Structure/
│   ├── TransportConvoy.java           # 运输车队结构
│   ├── RailwaySystem.java             # 铁路系统核心
│   ├── NuclearPowerPlant.java         # 核电站废墟
│   ├── MilitaryBunker.java            # 军事掩体
│   ├── GasStation.java                # 加油站废墟
│   └── SmallStructures.java           # 小型散落结构
├── Railway/
│   ├── RailwayNetwork.java            # 铁路网络管理器
│   ├── RailwayStation.java            # 铁路站点
│   ├── RailwayTunnel.java             # 铁路隧道
│   ├── RailwayEntrance.java           # 铁路入口
│   └── CreateIntegration.java         # Create联动接口
├── Processor/
│   ├── StructureProcessor.java        # 结构处理器
│   ├── RadiationProcessor.java        # 辐射处理器
│   └── LootProcessor.java             # 战利品处理器
└── WorldGen/
    ├── StructurePlacement.java        # 结构放置逻辑
    ├── RailwayGeneration.java         # 铁路生成逻辑
    └── StructureDensityController.java # 结构密度控制
```

#### 核心设计：城市铁路系统

**系统概述**
铁路系统将替换 The Lost Cities 模组的地下地铁结构生成，在地下 Y=30~Y=50 层生成连接各个失落城市的铁路网络。完全基于 Create 模组的轨道、列车和车站系统，玩家可以修复并驾驶列车在城市间快速移动。

**The Lost Cities 集成**
- The Lost Cities 默认生成地下地铁结构、地牢和战利品
- 本模组禁用或替换其地下结构生成
- 在 The Lost Cities 的建筑下方生成铁路站点入口
- 保持与城市地面的连接性（楼梯、电梯井等）

**Create 联动特性**
- 使用 Create 的标准轨道 (`create:track`)
- 使用 Create 的车站方块 (`create:train_station`)
- 使用 Create 的信号系统 (`create:track_signal`)
- 预生成可行驶的列车实体（可修复使用）
- 完全兼容 Create 的列车调度和控制系统

**铁路系统组成**

**1. 铁路站点（Train Station）**
- **中央车站**：位于大型城市地下，多站台，多条线路交汇
- **区域车站**：位于中等城市，1-2 个站台
- **小型车站**：位于小镇或郊区，单站台
- **结构内容**：
  - Create 车站方块和轨道
  - 候车区（座椅、自动售货机、战利品箱）
  - 售票亭（稀有战利品、地图）
  - 连接到地表的楼梯/电梯井
  - 辐射值：较低（相对安全的避难所）

**2. 铁路隧道（Rail Tunnel）**
- **主隧道**：双向轨道，使用 Create 标准轨道
- **维护通道**：隧道侧面步行通道，含有战利品箱
- **变轨区域**：使用 Create 的道岔 (`create:track_switch`)
- **信号系统**：每隔一定距离放置 Create 信号方块
- **隧道类型**：
  - 直线隧道（标准）
  - 弯道隧道
  - 交汇隧道（线路分支）
  - 塌方隧道（需要玩家修复轨道）
  - 积水隧道（需要排水或绕行）

**3. 列车配置**
预生成的列车实体，玩家可修复并使用：
- **地铁列车**：城市短途列车，2-4 节车厢
- **城际列车**：连接城市的长途列车，4-8 节车厢
- **货运列车**：运输物资的列车（稀有）
- 列车初始状态：部分损坏，需要玩家修复轨道和提供燃料
- 燃料：Create 的标准燃料（煤炭、生物质等）

**4. 地表入口**
- 与 The Lost Cities 建筑集成的入口点
- 地铁站入口（与城市建筑对接）
- 独立的车站建筑（小型站点）
- 入口特征：标识清晰、辐射较低、相对安全

#### 已有结构

**运输车系列**（可复用）:
- 运输车.nbt
- 损坏的运输车.nbt
- 侧翻的运输车.nbt
- 遇袭的运输车.nbt

**其他结构**（可复用）:
- 被污染物抓钩.nbt
- 破损堆芯.nbt

#### 需要新增的结构文件

**铁路站点系列** (`data/dull_radiation/structures/railway/`):
```
railway_station_central.nbt     # 中央车站（大型，多站台）
railway_station_regional.nbt     # 区域车站（中型，1-2站台）
railway_station_small.nbt        # 小型车站（单站台）
railway_station_abandoned.nbt    # 废弃车站（损坏严重，高辐射）
```

**铁路隧道系列**:
```
railway_tunnel_straight.nbt      # 直线隧道（标准双轨）
railway_tunnel_curve.nbt         # 弯道隧道
railway_tunnel_junction.nbt      # 铁路岔口
railway_tunnel_collapsed.nbt     # 塌方隧道（需要修复）
railway_tunnel_flooded.nbt       # 积水隧道
railway_tunnel_maintenance.nbt   # 维护通道（单侧步行道）
```

**车站内部设施**:
```
railway_waiting_area.nbt         # 候车区（座椅、售货机、战利品箱）
railway_ticket_office.nbt        # 售票亭（稀有战利品）
railway_storage_room.nbt         # 设备间（工具、材料）
railway_control_room.nbt         # 控制室（列车调度相关）
railway_hidden_room.nbt          # 隐藏房间（稀有高价值战利品）
```

**地表入口系列**:
```
railway_entrance_building.nbt    # 建筑内入口（对接城市建筑）
railway_entrance_street.nbt      # 街道入口（独立建筑）
railway_entrance_park.nbt        # 公园入口（地下通道）
```

**军事设施系列**:
```
military_bunker_entrance.nbt     # 掩体入口
military_bunker_inside.nbt       # 掩体内部
roadblock.nbt                    # 路障
checkpost.nbt                    # 检查站
helicopter_crash.nbt             # 坠毁的直升机
```

**特殊地点系列**:
```
nuclear_power_plant.nbt          # 核电站
gas_station_ruin.nbt             # 加油站废墟
hospital_ruin.nbt                # 医院废墟
supermarket_ruin.nbt             # 超市废墟
school_ruin.nbt                  # 学校废墟
farm_house.nbt                   # 农场废墟
```

#### 铁路系统生成配置

**生成规则**:
1. **替换 The Lost Cities 地下结构**: 禁用 The Lost Cities 的地铁/地牢生成，使用本模组铁路系统
2. **入口对接**: 与 The Lost Cities 建筑的地下室、楼梯对接
3. **网络结构**: 生成网状铁路网络，保证各城市互通
4. **深度范围**: Y=30~Y=50，避开常见矿层
5. **辐射分布**: 站点辐射较低，隧道辐射中等，废弃区域辐射高
6. **Create 依赖**: 如果检测到 Create 模组，生成 Create 兼容的轨道和车站

**Create 集成配置示例**:
```json
{
  "create_integration": {
    "enabled": true,
    "track_block": "create:track",
    "station_block": "create:train_station",
    "signal_block": "create:track_signal",
    "operator_block": "create:track_observer",
    "train_schedule_interval": 1200,
    "fuel_types": ["coal", "charcoal", "biomass"]
  }
}
```

#### 需要的资源文件

**结构模板文件** (.nbt格式) - 在游戏中建造后使用结构方块导出
- 所有结构需包含 Create 轨道和车站方块的正确放置
- 确保轨道连续性和列车可通行性

**结构配置文件** (`data/dull_radiation/worldgen/config/`):
```
railway_network.json             # 铁路网络生成配置
railway_station_spawn.json       # 站点生成规则
railway_tunnel_layout.json       # 隧道布局配置
transport_convoy.json            # 运输车队生成配置
military_structure.json          # 军事设施生成配置
```

**Create 列车配置** (`data/dull_radiation/create/trains/`):
```
train_passenger.json             # 客运列车配置
train_freight.json               # 货运列车配置
train_maintenance.json           # 维修车辆配置
```

#### 战利品表设计

**车站战利品**:
- 食物：罐头食品、瓶装水（常见）
- 药品：绷带、抗辐射药片（较少）
- 装备：防毒面具、简单防护服（稀有）
- 材料：铁锭、铜锭、 Create 机械零件（常见）
- 特殊：铁路地图、Create 轨道、列车组件（稀有）

**隧道战利品**:
- 维护通道：工具、电池、材料
- 塌方区域：高价值材料、稀有装备、高辐射风险
- 隐藏房间：独特物品、日记、地图

**铁路系统特性**:
1. **相对安全**: 站点区域辐射较低，适合新手探索
2. **快速交通**: 修复 Create 列车后可快速在城市间移动
3. **资源丰富**: 大量战利品箱，适合搜刮
4. **可扩展**: 玩家可以修复并扩建铁路系统
5. **挑战性**: 深处有变异生物、塌方、积水、高辐射区域

---

### 4. 钝色辐射：变异（DullRadiationVariation）

**依赖**: DullRadiation

#### 功能模块

```
me.shiming.DullRadiation.Variation/
├── Entity/
│   ├── MutatedAnimal.java           # 变异动物基类
│   ├── MutatedZombie.java           # 变异僵尸
│   ├── MutatedSkeleton.java         # 变异骷髅
│   ├── Ghoul.java                   # 食尸鬼
│   ├── RadScorpion.java             # 辐射蝎
│   └── Deathclaw.java               # 死亡爪
├── AI/
│   ├── MutatedAIBehavior.java       # 变异生物AI
│   └── RadiationSeekAI.java         # 辐射寻源AI
├── Genetics/
│   ├── MutationSystem.java          # 变异系统
│   └── RadiationMutation.java       # 辐射变异逻辑
└── Event/
    └── MutationEventHandler.java    # 变异事件处理
```

#### 变异生物设计

**1. 变异僵尸（Mutated Zombie）**
- 外观：发光、皮肤溃烂、体型增大
- 特性：攻击带辐射、夜间视野增强
- 生成：高辐射区域

**2. 食尸鬼（Ghoul）**
- 外观：人类形态、极度消瘦、速度快
- 特性：群体行动、高攻击力
- 生成：废墟城市、夜晚

**3. 辐射蝎（Rad Scorpion）**
- 外观：巨型蝎子、发紫色光
- 特性：毒液攻击、钻地
- 生成：沙漠、废墟

**4. 死亡爪（Deathclaw）**
- 外观：巨型猛兽、强壮肌肉
- 特性：极高攻击力、高血量
- 生成：特殊区域、BOSS级

#### 需要的资源文件

**模型和纹理** (`assets/dull_radiation/textures/entity/`):
```
mutated_zombie.png             # 变异僵尸
ghoul.png                      # 食尸鬼
rad_scorpion.png               # 辐射蝎
deathclaw.png                  # 死亡爪
mutated_skeleton.png           # 变异骷髅
mutated_spider.png             # 变异蜘蛛
```

**音效** (`assets/dull_radiation/sounds/`):
```
ghoul_growl.ogg                # 食尸鬼咆哮
ghoul_attack.ogg               # 食尸鬼攻击
rad_scorpion_idle.ogg          # 辐射蝎待机
rad_scorpion_attack.ogg        # 辐射蝎攻击
deathclaw_roar.ogg             # 死亡爪咆哮
deathclaw_attack.ogg           # 死亡爪攻击
mutated_ambient.ogg            # 变异生物环境音
```

---

### 5. 钝色之冬（DullWinter）- 整合模块

**依赖**: DullRadiation, DullRadiationStuffAddition, DullRadiationStructures

#### 功能模块

```
me.shiming.DullWinter/
├── World/
│   ├── WorldType/
│   │   ├── PostApocalypticWorldType.java  # 末世世界类型
│   │   └── WastelandWorldType.java        # 荒原世界类型
│   └── Biome/
│       ├── WastelandBiome.java            # 荒原生物群系
│       ├── RuinedCityBiome.java           # 废墟城市生物群系
│       └── ContaminatedForest.java        # 污染森林
├── Player/
│   ├── SpawnHandler.java                  # 出生处理
│   ├── StarterBook.java                   # 初始书
│   ├── StarterKit.java                    # 初始装备
│   └── Profession.java                    # 职业系统
├── Quest/
│   ├── QuestSystem.java                   # 任务系统
│   ├── QuestManager.java                  # 任务管理器
│   └── QuestData.java                     # 任务数据
└── Event/
    ├── SeasonEventHandler.java            # 季节事件
    └── DisasterEventHandler.java          # 灾难事件
```

#### 核心功能

**1. 世界生成**
- 末世世界类型（破坏后的地形）
- 荒原生物群系（干燥、荒凉）
- 污染森林（变异植物）
- 废墟城市生物群系

**2. 出生系统**
- 出生点选择
- 初始物品配置
- 初始书籍（末世生存指南）
- 职业选择（士兵、医生、工程师、拾荒者）

**3. 任务系统**
- 主线任务：探索、生存、重建
- 支线任务：寻找物资、救助NPC
- 成就系统

#### 需要的资源文件

**书籍内容** (`assets/dull_winter/textures/gui/book/`):
- 末世生存指南纹理

**世界生成纹理**:
- 荒原方块（干裂的土地、枯萎的草）
- 污染水方块
- 辐射尘埃粒子纹理

**生物群系纹理**:
- 荒原地面、树叶变色
- 污染树木纹理

**GUI纹理** (`assets/dull_winter/textures/gui/`):
```
spawn_selection.png           # 出生选择界面
profession_selection.png      # 职业选择界面
quest_book.png                # 任务书界面
radiation_meter.png           # 辐射计量表
```

---

## 资源文件清单汇总

### 纹理资源（需要制作）

#### 方块纹理 (约30+张)
- 铀矿、铅矿
- 核废料、污染土壤
- 铅制箱子、去污台
- 荒原系列方块（干裂土地、枯草等）
- 污染水、污染树叶

#### 物品纹理 (约40+张)
- 金属锭（铀、钚、铅）
- 工具（盖革计数器、防护服、防毒面具）
- 药品（抗辐射药剂、药片）
- 食物（罐头、污染食品）

#### 实体纹理 (约10+张)
- 变异僵尸、食尸鬼
- 辐射蝎、死亡爪
- 其他变异生物

#### GUI纹理 (约10+张)
- 出生选择、职业选择
- 任务界面、辐射计量表
- 盖革计数器界面

### 结构文件（需要制作）

**高优先级（核心体验 - 铁路系统）**:
- 铁路站点系列 (4个) - 中央、区域、小型、废弃车站
- 铁路隧道系列 (6个) - 直线、弯道、岔口、塌方、积水、维护通道
- 车站内部设施 (5个) - 候车区、售票亭、设备间、控制室、隐藏房间
- 地表入口系列 (3个) - 建筑内、街道、公园入口

**中优先级（其他结构）**:
- 军事掩体系列 (3-5个)
- 核电站 (1-2个)

**低优先级（增强体验）**:
- 加油站、超市、医院等 (5-10个)

**说明**:
- 废墟城市由 [The Lost Cities](https://www.curseforge.com/minecraft/mc-mods/the-lost-cities) 模组提供
- 铁路系统需要约 18+ 个结构文件（.nbt）
- 结构可以在游戏中建造后使用结构方块导出
- 所有结构需包含 Create 轨道和车站的正确放置

### 音效文件（需要录制）

**环境音效**:
- 盖革计数器（已有基础，需要扩展）
- 风声（荒原）
- 辐射嗡鸣声

**生物音效**:
- 变异生物叫声、攻击声
- 死亡音效

**UI音效**:
- 辐射警告
- 任务完成
- 获得物品

**说明**:
- 铁路列车音效由 [Create](https://modrinth.com/mod/create) 模组提供，无需额外录制

---

## 开发路线图

### 阶段 1：核心辐射系统（DullRadiation）
- [x] 基础Capability系统
- [ ] 完善辐射计算逻辑
- [ ] 区块辐射系统
- [ ] 网络同步
- [ ] 盖革计数器UI

### 阶段 2：物品与方块（DullRadiationStuffAddition）
- [ ] 基础资源（铀、铅）
- [ ] 防护装备
- [ ] 抗辐射物品
- [ ] 核心方块
- [ ] 配方系统

### 阶段 3：结构生成（DullRadiationStructures）
- [ ] 铁路系统架构设计
- [ ] 铁路站点/隧道结构模板制作
- [ ] 铁路网络生成逻辑（替换 The Lost Cities 地下结构）
- [ ] Create 模组集成（轨道、车站、列车）
- [ ] 战利品表配置
- [ ] 军事设施和其他小型结构

### 阶段 4：变异生物（DullRadiationVariation）
- [ ] 变异生物实体
- [ ] AI行为
- [ ] 模型和动画
- [ ] 生成规则

### 阶段 5：整合功能（DullWinter）
- [ ] 世界类型
- [ ] 生物群系
- [ ] 出生系统
- [ ] 任务系统

---

## 技术栈

- **Minecraft**: 1.20.1
- **Forge**: 47.4.13
- **Java**: 17
- **Build Tool**: Gradle 8.1.1
- **Mappings**: Official 1.20.1

---

## 贡献指南

1. Fork 本项目
2. 创建功能分支
3. 提交更改
4. 发起 Pull Request

---

## 许可证

MIT License

---

**当前状态**: 开发中

**最后更新**: 2025-12-26

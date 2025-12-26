package me.shiming.DullRadiation.Stuff;

import me.shiming.DullRadiation.DullRadiation;
import me.shiming.DullRadiation.Stuff.blocks.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 方块注册类
 *
 * @author Shiming
 * @version 2.0.0
 */
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DullRadiationStuff.MODID);

    // ========== 矿石方块 ==========

    /**
     * 铀矿
     * 生成于地下的稀有矿石
     */
    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore",
        () -> new RadioactiveOreBlock(BlockBehaviour.Properties.of()
            .strength(3.0F, 3.0F)
            .requiresCorrectToolForDrops()));

    /**
     * 铅矿
     * 生成于地下的常见矿石
     */
    public static final RegistryObject<Block> LEAD_ORE = BLOCKS.register("lead_ore",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(3.0F, 3.0F)
            .requiresCorrectToolForDrops()));

    // ========== 辐射方块 ==========

    /**
     * 放射源
     * 高辐射区域，靠近会持续增加辐射
     */
    public static final RegistryObject<Block> RADIOACTIVE_SOURCE = BLOCKS.register("radioactive_source",
        () -> new RadioactiveSourceBlock(BlockBehaviour.Properties.of()
            .strength(5.0F, 6.0F)
            .lightLevel(state -> 15)));

    /**
     * 核废料块
     * 辐射性废料，可以储存但会持续辐射周围
     */
    public static final RegistryObject<Block> NUCLEAR_WASTE = BLOCKS.register("nuclear_waste",
        () -> new NuclearWasteBlock(BlockBehaviour.Properties.of()
            .strength(2.0F, 3.0F)
            .lightLevel(state -> 8)));

    /**
     * 被污染的土壤
     * 被辐射污染的土地，会缓慢扩散辐射
     */
    public static final RegistryObject<Block> CONTAMINATED_SOIL = BLOCKS.register("contaminated_soil",
        () -> new ContaminatedSoilBlock(BlockBehaviour.Properties.of()
            .strength(0.5F)
            .speedFactor(0.8F)));

    // ========== 功能方块 ==========

    /**
     * 铅制箱子
     * 可以阻止辐射穿透的储物容器
     */
    public static final RegistryObject<Block> LEAD_CHEST = BLOCKS.register("lead_chest",
        () -> new LeadChestBlock(BlockBehaviour.Properties.of()
            .strength(2.5F, 3.0F)));

    /**
     * 去污台
     * 用于清理被污染的物品和装备
     */
    public static final RegistryObject<Block> DECONTAMINATION_TABLE = BLOCKS.register("decontamination_table",
        () -> new DecontaminationTableBlock(BlockBehaviour.Properties.of()
            .strength(2.5F, 3.0F)));

    /**
     * 铅玻璃
     * 防止辐射穿透的透明方块
     */
    public static final RegistryObject<Block> LEAD_GLASS = BLOCKS.register("lead_glass",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(0.3F)
            .lightLevel(state -> 0)
            .noOcclusion()));

    // ========== 建筑方块 ==========

    /**
     * 铅块
     * 建筑方块，用于防辐射建筑
     */
    public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(3.0F, 6.0F)
            .requiresCorrectToolForDrops()));

    // ========== 装饰方块 ==========

    /**
     * 辐射警告牌
     * 装饰性方块，用于标记危险区域
     */
    public static final RegistryObject<Block> RADIATION_SIGN = BLOCKS.register("radiation_sign",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(1.0F)
            .lightLevel(state -> 0)));

    /**
     * 废弃金属
     * 装饰性方块，用于建造废墟场景
     */
    public static final RegistryObject<Block> SCRAP_METAL = BLOCKS.register("scrap_metal",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(2.0F, 3.0F)));

    /**
     * 注册所有方块到事件总线
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        DullRadiation.LOGGER.info("Registered all blocks for Dull Radiation: Stuff Addition");
    }
}

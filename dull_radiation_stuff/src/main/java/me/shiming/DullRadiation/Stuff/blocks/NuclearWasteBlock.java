package me.shiming.DullRadiation.Stuff.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * 核废料方块
 * 中等强度辐射，用于储存核废料
 *
 * @author Shiming
 * @version 2.0.0
 */
public class NuclearWasteBlock extends RadioactiveBlock {

    public NuclearWasteBlock(BlockBehaviour.Properties properties) {
        super(properties, 1.0); // 每tick增加1点辐射通量
    }
}

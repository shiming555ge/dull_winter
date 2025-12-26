package me.shiming.DullRadiation.Stuff.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * 放射源方块
 * 高强度辐射，用于标记危险区域
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadioactiveSourceBlock extends RadioactiveBlock {

    public RadioactiveSourceBlock(BlockBehaviour.Properties properties) {
        super(properties, 2.0); // 每tick增加2点辐射通量
    }
}

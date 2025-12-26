package me.shiming.DullRadiation.Stuff.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * 铀矿方块
 * 低强度辐射，可以采集获得铀
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadioactiveOreBlock extends RadioactiveBlock {

    public RadioactiveOreBlock(BlockBehaviour.Properties properties) {
        super(properties, 0.5); // 每tick增加0.5点辐射通量
    }
}

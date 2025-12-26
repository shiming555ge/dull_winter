package me.shiming.DullRadiation.Stuff.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * 被污染的土壤
 * 轻度辐射，会缓慢影响周围的生物
 *
 * @author Shiming
 * @version 2.0.0
 */
public class ContaminatedSoilBlock extends RadioactiveBlock {

    public ContaminatedSoilBlock(BlockBehaviour.Properties properties) {
        super(properties, 0.1); // 每tick增加0.1点辐射通量
    }
}

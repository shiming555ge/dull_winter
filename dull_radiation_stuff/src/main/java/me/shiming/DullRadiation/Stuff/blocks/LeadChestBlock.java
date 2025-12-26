package me.shiming.DullRadiation.Stuff.blocks;

import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * 铅制箱子
 * 防止辐射穿透的储物容器
 * TODO: 实现特殊的容器逻辑，阻止内部物品辐射外部
 *
 * @author Shiming
 * @version 2.0.0
 */
public class LeadChestBlock extends ChestBlock {

    public LeadChestBlock(BlockBehaviour.Properties properties) {
        super(properties, null);
        // TODO: 实现辐射屏蔽逻辑
    }
}

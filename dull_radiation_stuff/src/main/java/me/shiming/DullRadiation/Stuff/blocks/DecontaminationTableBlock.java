package me.shiming.DullRadiation.Stuff.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 去污台
 * 用于清理被污染的物品和装备
 * TODO: 实现交互逻辑和GUI
 *
 * @author Shiming
 * @version 2.0.0
 */
public class DecontaminationTableBlock extends Block {

    public DecontaminationTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if (!level.isClientSide) {
            // TODO: 打开去污台 GUI
            // player.openMenu(new DecontaminationMenu(), pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}

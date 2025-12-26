package me.shiming.DullRadiation.Stuff.blocks;

import me.shiming.DullRadiation.api.RadiationAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 放射性方块基类
 *
 * 任何靠近的实体都会受到辐射影响
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadioactiveBlock extends net.minecraft.world.level.block.Block {

    private final double radiationStrength; // 辐射强度

    /**
     * 创建放射性方块
     * @param properties 方块属性
     * @param radiationStrength 辐射强度（每 tick 增加的辐射通量）
     */
    public RadioactiveBlock(Properties properties, double radiationStrength) {
        super(properties);
        this.radiationStrength = radiationStrength;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        // 只在服务端处理玩家
        if (!level.isClientSide && entity instanceof Player player) {
            // 增加辐射通量
            RadiationAPI.addPlayerFlux(player, radiationStrength);
        }
        super.stepOn(level, pos, state, entity);
    }

    /**
     * 获取辐射强度
     */
    public double getRadiationStrength() {
        return radiationStrength;
    }
}

package me.shiming.DullRadiation.Variation;

import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dull Radiation: Variation - 变异生物模块
 *
 * TODO: 实现变异生物系统
 * - 辐射导致的生物变异
 * - 新的敌对生物
 * - 变异生物的行为和AI
 *
 * @author Shiming
 * @version 2.0.0
 */
@Mod(DullRadiationVariation.MODID)
public class DullRadiationVariation {

    public static final String MODID = "dull_radiation_variation";
    public static final String VERSION = "2.0.0";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public DullRadiationVariation() {
        LOGGER.info("Dull Radiation: Variation {} is loading!", VERSION);
        LOGGER.info("This module is currently a placeholder. Variant creatures will be implemented in future updates.");
    }
}

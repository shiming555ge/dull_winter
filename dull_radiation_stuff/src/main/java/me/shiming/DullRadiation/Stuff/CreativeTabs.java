package me.shiming.DullRadiation.Stuff;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * 创造模式标签页
 *
 * @author Shiming
 * @version 2.0.0
 */
public class CreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DullRadiationStuff.MODID);

    /**
     * 钝色辐射：物品附加标签页
     */
    public static final RegistryObject<CreativeModeTab> DULL_RADIATION_STUFF = TABS.register("dull_radiation_stuff",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.dull_radiation_stuff"))
                    .icon(() -> new ItemStack(ModItems.URANIUM_INGOT.get()))
                    .displayItems((parameters, output) -> {
                        // ========== 资源物品 ==========
                        output.accept(ModItems.URANIUM_INGOT.get());
                        output.accept(ModItems.LEAD_INGOT.get());
                        output.accept(ModItems.POLLUTION_SCRAP.get());

                        // ========== 工具材料 ==========
                        output.accept(ModItems.RADIATION_FABRIC.get());
                        output.accept(ModItems.LEAD_PLATE.get());
                        output.accept(ModItems.FILTER.get());
                        output.accept(ModItems.ACTIVATED_CHARCOAL_FILTER.get());

                        // ========== 防护装备 ==========
                        output.accept(ModItems.GAS_MASK.get());
                        output.accept(ModItems.RADIATION_MASK.get());

                        // ========== 药品 ==========
                        output.accept(ModItems.ANTI_RAD_PILL.get());
                        output.accept(ModItems.ANTI_RAD_INJECTION.get());
                        output.accept(ModItems.IODINE_TABLET.get());
                        output.accept(ModItems.ANTI_RAD_DRINK.get());

                        // ========== 食品 ==========
                        output.accept(ModItems.CANNED_FOOD.get());
                        output.accept(ModItems.CONTAMINATED_CAN.get());

                        // ========== 工具 ==========
                        output.accept(ModItems.GEIGER_COUNTER.get());
                        output.accept(ModItems.ADVANCED_GEIGER_COUNTER.get());
                        output.accept(ModItems.RADIATION_DETECTOR.get());

                        // ========== 矿石方块 ==========
                        output.accept(ModBlocks.URANIUM_ORE.get());
                        output.accept(ModBlocks.LEAD_ORE.get());

                        // ========== 辐射方块 ==========
                        output.accept(ModBlocks.RADIOACTIVE_SOURCE.get());
                        output.accept(ModBlocks.NUCLEAR_WASTE.get());
                        output.accept(ModBlocks.CONTAMINATED_SOIL.get());

                        // ========== 功能方块 ==========
                        output.accept(ModBlocks.LEAD_CHEST.get());
                        output.accept(ModBlocks.DECONTAMINATION_TABLE.get());
                        output.accept(ModBlocks.LEAD_GLASS.get());

                        // ========== 建筑方块 ==========
                        output.accept(ModBlocks.LEAD_BLOCK.get());

                        // ========== 装饰方块 ==========
                        output.accept(ModBlocks.RADIATION_SIGN.get());
                        output.accept(ModBlocks.SCRAP_METAL.get());
                    })
                    .build());

    /**
     * 注册所有标签页
     */
    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        TABS.register(eventBus);
    }
}

package me.shiming.DullRadiation.capability;

import me.shiming.DullRadiation.DullRadiation;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 辐射 Capability 事件处理器
 *
 * 负责：附加 Capability 到玩家实体
 *
 * @author Shiming
 * @version 2.0.0
 */
@Mod.EventBusSubscriber(modid = DullRadiation.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RadiationCapabilityHandler {

    /**
     * 附加 Capability 到玩家实体
     */
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            RadiationProvider provider = new RadiationProvider();
            event.addCapability(RadiationCapability.ID, provider);
            event.addListener(provider::invalidate);
        }
    }

    /**
     * Radiation Capability 提供者
     */
    public static class RadiationProvider implements ICapabilityProvider, net.minecraftforge.common.util.INBTSerializable<CompoundTag> {

        private final RadiationData data = new RadiationData();
        private final LazyOptional<IRadiationData> optional = LazyOptional.of(() -> data);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return RadiationCapability.RADIATION_CAPABILITY.orEmpty(cap, optional);
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag nbt = new CompoundTag();
            data.writeNBT(nbt);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            data.readNBT(nbt);
        }

        /**
         * 使缓存失效
         */
        public void invalidate() {
            optional.invalidate();
        }
    }
}

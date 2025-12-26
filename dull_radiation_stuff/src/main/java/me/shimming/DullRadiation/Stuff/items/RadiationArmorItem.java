package me.shiming.DullRadiation.Stuff.items;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * 辐射防护装备
 *
 * 提供辐射防护的头盔装备
 *
 * @author Shiming
 * @version 2.0.0
 */
public class RadiationArmorItem extends ArmorItem {

    /**
     * 创建辐射防护装备
     *
     * @param material 装备材质
     * @param type 装备类型（必须是 HEAD）
     * @param properties 物品属性
     */
    public RadiationArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    /**
     * 自定义防护材质
     */
    public static class RadiationArmorMaterial implements ArmorMaterial {
        private final int durability;
        private final int[] protectionAmounts;
        private final int enchantmentValue;
        private final net.minecraft.sounds.SoundEvent equipSound;
        private final Ingredient repairIngredient;

        public RadiationArmorMaterial(int durability, int[] protectionAmounts,
                                     int enchantmentValue, net.minecraft.sounds.SoundEvent equipSound,
                                     Ingredient repairIngredient) {
            this.durability = durability;
            this.protectionAmounts = protectionAmounts;
            this.enchantmentValue = enchantmentValue;
            this.equipSound = equipSound;
            this.repairIngredient = repairIngredient;
        }

        @Override
        public int getDurabilityForType(Type type) {
            return durability;
        }

        @Override
        public int getDefenseForType(Type type) {
            return protectionAmounts[type.getSlot().getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public net.minecraft.sounds.SoundEvent getEquipSound() {
            return equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairIngredient;
        }

        @Override
        public String getName() {
            return "radiation_armor";
        }

        @Override
        public float getToughness() {
            return 0.0F;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.0F;
        }
    }
}

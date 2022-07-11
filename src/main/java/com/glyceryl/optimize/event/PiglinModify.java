package com.glyceryl.optimize.event;

import com.glyceryl.optimize.config.ClientConfig;
import com.glyceryl.optimize.goal.FindNearestBlockGoal;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Set;

public class PiglinModify {

    private final Set<Item> piglinLoved = ImmutableSet.of(
            Items.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.GLISTERING_MELON_SLICE,
            Items.GILDED_BLACKSTONE,
            Items.GOLDEN_HORSE_ARMOR,
            Items.GOLDEN_CARROT,
            Items.GOLDEN_APPLE,
            Items.GOLDEN_HELMET,
            Items.GOLDEN_CHESTPLATE,
            Items.GOLDEN_LEGGINGS,
            Items.GOLDEN_BOOTS,
            Items.GOLDEN_SWORD,
            Items.GOLDEN_PICKAXE,
            Items.GOLDEN_SHOVEL,
            Items.GOLDEN_AXE,
            Items.GOLDEN_HOE,
            Items.GOLD_BLOCK,
            Items.GOLD_INGOT,
            Items.CLOCK,
            Items.BELL,
            Items.RAW_GOLD,
            Items.RAW_GOLD_BLOCK,
            PiglinAi.BARTERING_ITEM);

    @SubscribeEvent
    public void lootGoldFromChest(LivingEvent event) {
        LivingEntity entity = event.getEntityLiving();
        int lootingInterval = ClientConfig.LOOTING_INTERVAL.get();
        if (entity instanceof Piglin piglin && piglin.tickCount % lootingInterval == 0) {
            BlockPos pos1 = piglin.blockPosition();
            for (BlockPos pos : BlockPos.withinManhattan(pos1, 1, 1, 1)) {
                for (Direction direction : Direction.values()) {
                    if (piglin.level.getBlockState(pos.relative(direction)).is(Blocks.GOLD_BLOCK)) {
                        if (piglin.level.getBlockState(pos).hasBlockEntity()) {
                            if (piglin.level.getBlockEntity(pos) instanceof Container container) {
                                if (container.hasAnyOf(piglinLoved) && piglin.getOffhandItem().isEmpty()) {
                                    outCycle:
                                    for (Item piglinLovedItem : this.piglinLoved) {
                                        if (container.countItem(piglinLovedItem) > 0) {
                                            ItemStack itemStack = piglinLovedItem.getDefaultInstance().split(1);
                                            ItemEntity itemEntity = new ItemEntity(piglin.level, pos1.getX(), pos1.above().getY(), pos1.getZ(), itemStack);
                                            if (!(piglin.level.getBlockState(pos1.below()).getBlock() instanceof Hopper)) {
                                                itemEntity.setNeverPickUp();
                                                itemEntity.lifespan = 15;
                                                piglin.level.addFreshEntity(itemEntity);
                                            }
                                            PiglinAi.holdInOffhand(piglin, itemStack);
                                            piglin.inventory.removeAllItems();
                                            for (int i = 0; i < container.getContainerSize(); i++) {
                                                if (container.getItem(i).is(piglinLovedItem)) {
                                                    container.removeItem(i, 1); break outCycle;
                                                }
                                            }
                                        }
                                    }
                                    piglin.playSound(SoundEvents.PIGLIN_ADMIRING_ITEM, 1.0F, piglin.getVoicePitch());
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void addGoalAndNbt(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Piglin piglin) {
            piglin.goalSelector.addGoal(1, new FindNearestBlockGoal(piglin));
        }
    }

}
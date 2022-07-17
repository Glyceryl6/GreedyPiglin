package com.glyceryl.optimize.event;

import com.glyceryl.optimize.config.CommonConfig;
import com.glyceryl.optimize.goal.FindNearestBlockGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class PiglinModify {

    @SubscribeEvent
    public void lootGoldFromChest(LivingEvent event) {
        LivingEntity entity = event.getEntityLiving();
        int lootingInterval = CommonConfig.LOOTING_INTERVAL.get();
        if (entity instanceof Piglin piglin && piglin.tickCount % lootingInterval == 0) {
            BlockPos pos1 = piglin.blockPosition();
            for (BlockPos pos : BlockPos.withinManhattan(pos1, 1, 1, 1)) {
                for (Direction direction : Direction.values()) {
                    if (piglin.level.getBlockState(pos.relative(direction)).is(Blocks.GOLD_BLOCK)) {
                        if (piglin.level.getBlockState(pos).hasBlockEntity()) {
                            if (piglin.level.getBlockEntity(pos) instanceof Container container) {
                                if (piglin.getOffhandItem().isEmpty()) {
                                    outCycle:
                                    for (String item : CommonConfig.CAN_ATTRACT_PIGLIN.get()) {
                                        Item piglinLoved = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item));
                                        if (piglinLoved != null && !piglinLoved.getDefaultInstance().isEmpty() && container.countItem(piglinLoved) > 0) {
                                            ItemStack itemStack = piglinLoved.getDefaultInstance().split(1);
                                            boolean noStuckInHopper = !(piglin.level.getBlockEntity(pos1) instanceof Hopper);
                                            boolean noHopperBelow = !(piglin.level.getBlockEntity(pos1.below()) instanceof Hopper);
                                            if (noStuckInHopper && noHopperBelow) {
                                                ItemEntity itemEntity = new ItemEntity(piglin.level, pos1.getX(), pos1.above().getY(), pos1.getZ(), itemStack);
                                                itemEntity.setNeverPickUp();
                                                itemEntity.lifespan = 15;
                                                piglin.level.addFreshEntity(itemEntity);
                                            }
                                            PiglinAi.holdInOffhand(piglin, itemStack);
                                            PiglinAi.putInInventory(piglin, itemStack);
                                            piglin.inventory.removeAllItems();
                                            for (int i = 0; i < container.getContainerSize(); i++) {
                                                if (container.getItem(i).is(piglinLoved)) {
                                                    piglin.playSound(SoundEvents.PIGLIN_ADMIRING_ITEM, 1.0F, piglin.getVoicePitch());
                                                    container.removeItem(i, 1); break outCycle;
                                                }
                                            }
                                        }
                                    }
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
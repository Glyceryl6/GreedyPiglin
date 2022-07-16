package com.glyceryl.optimize.goal;

import com.glyceryl.optimize.config.CommonConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FindNearestBlockGoal extends MoveToBlockGoal {

    public FindNearestBlockGoal(PathfinderMob mob) {
        super(mob, mob.isBaby() ? 0.8F : 0.5F,
                CommonConfig.HORIZONTAL_SEARCH_RANGE.get(),
                CommonConfig.VERTICAL_SEARCH_RANGE.get());
    }

    public boolean canContinueToUse() {
        return this.isValidTarget(this.mob.level, this.blockPos);
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        boolean flag0 = level.getBlockState(pos.east()).is(Blocks.GOLD_BLOCK);
        boolean flag1 = level.getBlockState(pos.west()).is(Blocks.GOLD_BLOCK);
        boolean flag2 = level.getBlockState(pos.south()).is(Blocks.GOLD_BLOCK);
        boolean flag3 = level.getBlockState(pos.north()).is(Blocks.GOLD_BLOCK);
        boolean flag4 = level.getBlockState(pos.above()).is(Blocks.GOLD_BLOCK);
        boolean flag5 = level.getBlockState(pos.below()).is(Blocks.GOLD_BLOCK);
        boolean hasGoldBlockNeighbor = flag0 || flag1 || flag2 || flag3 || flag4 || flag5;
        if (blockState.hasBlockEntity() && hasGoldBlockNeighbor) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof Container container) {
                int itemCount = 0;
                for (String item : CommonConfig.CAN_ATTRACT_PIGLIN.get()) {
                    Item piglinLoved = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item));
                    if (piglinLoved != null) {
                        if (container.countItem(piglinLoved) > 0) {
                            itemCount = container.countItem(piglinLoved);
                            break;
                        }
                    }
                }
                return itemCount > 0;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean hasPiglinLoved(Container container) {
        Item item = PiglinAi.BARTERING_ITEM;
        return container.countItem(item) > 0 ||
                container.countItem(Items.BELL) > 0 ||
                container.countItem(Items.CLOCK) > 0 ||
                container.countItem(Items.GOLD_BLOCK) > 0 ||
                container.countItem(Items.GOLD_INGOT) > 0 ||
                container.countItem(Items.GOLDEN_APPLE) > 0 ||
                container.countItem(Items.GOLDEN_CARROT) > 0 ||
                container.countItem(Items.GILDED_BLACKSTONE) > 0 ||
                container.countItem(Items.GOLDEN_HORSE_ARMOR) > 0 ||
                container.countItem(Items.GLISTERING_MELON_SLICE) > 0 ||
                container.countItem(Items.GOLDEN_HELMET) > 0 ||
                container.countItem(Items.GOLDEN_CHESTPLATE) > 0 ||
                container.countItem(Items.GOLDEN_LEGGINGS) > 0 ||
                container.countItem(Items.GOLDEN_BOOTS) > 0 ||
                container.countItem(Items.GOLDEN_SWORD) > 0 ||
                container.countItem(Items.GOLDEN_PICKAXE) > 0 ||
                container.countItem(Items.GOLDEN_SHOVEL) > 0 ||
                container.countItem(Items.GOLDEN_AXE) > 0 ||
                container.countItem(Items.GOLDEN_HOE) > 0 ||
                container.countItem(Items.RAW_GOLD) > 0 ||
                container.countItem(Items.RAW_GOLD_BLOCK) > 0 ||
                container.countItem(Items.ENCHANTED_GOLDEN_APPLE) > 0 ||
                container.countItem(Items.LIGHT_WEIGHTED_PRESSURE_PLATE) > 0;
    }

}
package com.glyceryl.optimize.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Piglin.class)
public abstract class PiglinMixin extends AbstractPiglin {

    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(PiglinMixin.class, EntityDataSerializers.ITEM_STACK);

    public PiglinMixin(EntityType<? extends AbstractPiglin> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedData(CallbackInfo ci) {
        this.getEntityData().define(DATA_ITEM, PiglinAi.BARTERING_ITEM.getDefaultInstance());
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        if (!this.getItem().isEmpty()) {
            compoundTag.put("BarteringItem", this.getItem().save(new CompoundTag()));
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        CompoundTag compound = compoundTag.getCompound("BarteringItem");
        this.setItem(ItemStack.of(compound));
        if (this.getItem().isEmpty()) {
            this.setItem(Items.GOLD_INGOT.getDefaultInstance());
        }
    }

    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM);
    }

    public void setItem(ItemStack pStack) {
        this.getEntityData().set(DATA_ITEM, pStack);
    }

}
package com.glyceryl.optimize.mixin;

import com.glyceryl.optimize.config.CommonConfig;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PiglinAi.class)
public class MixinPiglinAi {

    @ModifyConstant(method = "admireGoldItem", constant = @Constant(longValue = 120L))
    private static long admireGoldItem(long constant) {
        return CommonConfig.ADMIRE_DURATION.get();
    }

}
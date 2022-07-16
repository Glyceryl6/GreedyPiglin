package com.glyceryl.optimize;

import com.glyceryl.optimize.config.CommonConfig;
import com.glyceryl.optimize.event.PiglinModify;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(GreedyPiglin.MOD_ID)
public class GreedyPiglin {

    public static final String MOD_ID = "greedy_piglin";

    public GreedyPiglin() {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(CommonConfig.BARTERING_ITEM.get()));
        if (item != null) {
            PiglinAi.BARTERING_ITEM = !item.getDefaultInstance().isEmpty() ? item : Items.GOLD_INGOT;
        }
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CommonConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(new PiglinModify());
        MinecraftForge.EVENT_BUS.register(this);
    }

}
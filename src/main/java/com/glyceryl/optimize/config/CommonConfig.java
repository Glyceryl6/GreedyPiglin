package com.glyceryl.optimize.config;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class CommonConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> ADMIRE_DURATION;
    public static final ForgeConfigSpec.ConfigValue<Integer> LOOTING_INTERVAL;
    public static final ForgeConfigSpec.ConfigValue<Integer> HORIZONTAL_SEARCH_RANGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> VERTICAL_SEARCH_RANGE;
    public static final ForgeConfigSpec.ConfigValue<String> BARTERING_ITEM;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> CAN_ATTRACT_PIGLIN;

    static {
        BUILDER.push("Configs for modify the data of piglin");
        ADMIRE_DURATION = BUILDER.comment("Use for control the admire duration ticks when piglin hold a gold ingot.")
                .defineInRange("admireDuration", 5, 1, Integer.MAX_VALUE);
        LOOTING_INTERVAL = BUILDER.comment("Use for control the interval ticks when piglin looting golden item from container.")
                .defineInRange("lootingInterval", 10, 1, Integer.MAX_VALUE);
        HORIZONTAL_SEARCH_RANGE = BUILDER.comment("Use for control the horizontal search range that piglin find chest.")
                .defineInRange("horizontalSearchRange", 24, 1, Integer.MAX_VALUE);
        VERTICAL_SEARCH_RANGE = BUILDER.comment("Use for control the vertical search range that piglin find chest.")
                .defineInRange("verticalSearchRange", 6, 1, Integer.MAX_VALUE);
        BARTERING_ITEM = BUILDER.comment("Use for change the default bartering item of piglin.")
                .define("barteringItem", "minecraft:gold_ingot");
        CAN_ATTRACT_PIGLIN = BUILDER.comment("It used to determine what item can attract the piglin.")
                        .defineList("canAttractPiglin", ImmutableList.of("minecraft:gold_ore",
                                "minecraft:nether_gold_ore", "minecraft:deepslate_gold_ore",
                                "minecraft:gold_block", "minecraft:gilded_blackstone",
                                "minecraft:light_weighted_pressure_plate", "minecraft:gold_ingot",
                                "minecraft:bell", "minecraft:clock", "minecraft:golden_carrot",
                                "minecraft:glistering_melon_slice", "minecraft:golden_apple",
                                "minecraft:enchanted_golden_apple", "minecraft:golden_helmet",
                                "minecraft:golden_chestplate", "minecraft:golden_leggings",
                                "minecraft:golden_boots", "minecraft:golden_horse_armor",
                                "minecraft:golden_sword", "minecraft:golden_pickaxe",
                                "minecraft:golden_shovel", "minecraft:golden_axe",
                                "minecraft:golden_hoe", "minecraft:raw_gold",
                                "minecraft:raw_gold_block", "minecraft:porkchop",
                                "minecraft:cooked_porkchop"), (obj) -> true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
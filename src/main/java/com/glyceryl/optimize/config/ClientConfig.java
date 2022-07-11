package com.glyceryl.optimize.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> ADMIRE_DURATION;
    public static final ForgeConfigSpec.ConfigValue<Integer> LOOTING_INTERVAL;
    public static final ForgeConfigSpec.ConfigValue<Integer> HORIZONTAL_SEARCH_RANGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> VERTICAL_SEARCH_RANGE;
    public static final ForgeConfigSpec.ConfigValue<String> BARTERING_ITEM;

    static {
        BUILDER.push("Configs for modify the data of piglin");
        ADMIRE_DURATION = BUILDER.comment("Use for control the admire duration ticks when piglin hold a gold ingot")
                .defineInRange("ADMIRE_DURATION", 5, 1, Short.MAX_VALUE);
        LOOTING_INTERVAL = BUILDER.comment("Use for control the interval ticks when piglin looting golden item from container")
                .defineInRange("LOOTING_INTERVAL", 10, 1, Short.MAX_VALUE);
        HORIZONTAL_SEARCH_RANGE = BUILDER.comment("Use for control the horizontal search range that piglin find chest")
                .defineInRange("HORIZONTAL_SEARCH_RANGE", 24, 1, Short.MAX_VALUE);
        VERTICAL_SEARCH_RANGE = BUILDER.comment("Use for control the vertical search range that piglin find chest")
                .defineInRange("VERTICAL_SEARCH_RANGE", 6, 1, Short.MAX_VALUE);
        BARTERING_ITEM = BUILDER.comment("Use for change the default bartering item of piglin")
                .define("BARTERING_ITEM", "minecraft:gold_ingot");
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
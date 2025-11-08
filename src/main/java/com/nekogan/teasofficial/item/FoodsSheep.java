package com.nekogan.teasofficial.item;

import net.minecraft.world.food.FoodProperties;

public class FoodsSheep {
    /*
    * 这里的 nutrition 指的是恢复的 饥饿值（AppleSkin中的实心鸡腿）
    * 而 saturationModifier 是恢复的 饱食度（AppleSkin中的金边鸡腿），这里与 nutrition 乘算处理（有基准值，未测量）
    * alwaysEdible -> 可以让该物品一直吃不消耗
    */
    public static final FoodProperties HORN_O_PLENTY = (new FoodProperties.Builder()).alwaysEdible().nutrition(8).saturationModifier(0.8F).build();

}

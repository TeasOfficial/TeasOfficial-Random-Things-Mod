package com.nekogan.teasofficial;

import com.nekogan.teasofficial.registry.ModRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.spongepowered.asm.launch.MixinBootstrap;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "teasofficial";
    public static String tab() {
        return String.format("itemGroup.%s", MODID);
    }

    public Main(IEventBus modEventBus) {
        MixinBootstrap.init();
        modEventBus.register(ModRegistry.class);
        ModRegistry.ITEMS.register(modEventBus);
        ModRegistry.CREATIVE_TABS.register(modEventBus);
    }
}

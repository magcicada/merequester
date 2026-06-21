package com.almostreliable.merequester;

import com.almostreliable.merequester.network.PacketHandler;
import com.almostreliable.merequester.platform.Platform;
import com.mojang.logging.LogUtils;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

@SuppressWarnings("WeakerAccess")
@Mod(BuildConfig.MOD_ID)
public final class MERequester {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String TERMINAL_ID = "requester_terminal";
    public static final String REQUESTER_ID = "requester";

    public MERequester() {
        com.almostreliable.merequester.Registration.init();
        onInitialize();
    }

    public void onInitialize() {
        Platform.initConfig();
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(MERequester::onCommonSetup);
        modEventBus.addListener(MERequester::onRegistryEvent);
        modEventBus.addListener(MERequester::onCreativeTabContents);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(this::clientSetup);
        }
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private static void onRegistryEvent(RegisterEvent event) {
        ModTab.registerTab(event);
    }

    private static void onCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        ModTab.initContents(event);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(Registration::registerClientModels);
    }
}

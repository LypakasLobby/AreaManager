package com.lypaka.areamanager.Listeners;

import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.Regions.RegionHandler;
import com.lypaka.lypakautils.ConfigGetters;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = AreaManager.MOD_ID)
public class ServerLoadListener {

    @SubscribeEvent
    public static void onPostInit (FMLServerAboutToStartEvent event) throws IOException, ObjectMappingException {

        if (!ConfigGetters.tickListenerEnabled) {

            AreaManager.logger.warn("============ WARNING ============");
            AreaManager.logger.warn("Detected the tick listener in LypakaUtils is not enabled!");
            AreaManager.logger.warn("This configuration node is required to be enabled in order for AreaManager to work!");
            AreaManager.logger.warn("Please go into your lypakautils.conf file and set the tick listener to true to enable it, and then restart the server.");

        }
        MinecraftForge.EVENT_BUS.register(new MovementListener());
        MinecraftForge.EVENT_BUS.register(new SwimListener());
        MinecraftForge.EVENT_BUS.register(new WandListener());

        RegionHandler.loadRegions();

    }

}

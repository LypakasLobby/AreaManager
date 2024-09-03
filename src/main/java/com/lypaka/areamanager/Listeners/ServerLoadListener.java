package com.lypaka.areamanager.Listeners;

import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.Regions.RegionHandler;
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

        MinecraftForge.EVENT_BUS.register(new MovementListener());
        MinecraftForge.EVENT_BUS.register(new SwimListener());
        MinecraftForge.EVENT_BUS.register(new WandListener());

        RegionHandler.loadRegions();

    }

}

package com.lypaka.areamanager.Listeners;

import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.ConfigGetters;
import com.lypaka.areamanager.Spawners.FishSpawner;
import com.lypaka.areamanager.Spawners.HeadbuttSpawner;
import com.lypaka.areamanager.Spawners.NaturalSpawner;
import com.lypaka.areamanager.Spawners.RockSmashSpawner;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import java.util.Timer;
import java.util.TimerTask;

@Mod.EventBusSubscriber(modid = AreaManager.MOD_ID)
public class ServerStartedListener {

    public static boolean modActive = false;

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) {

        if (com.lypaka.lypakautils.ConfigGetters.tickListenerEnabled) {

            modActive = true;
            MinecraftForge.EVENT_BUS.register(new MovementListener());
            MinecraftForge.EVENT_BUS.register(new RespawnListener());
            Pixelmon.EVENT_BUS.register(new BattleEndListener());
            Pixelmon.EVENT_BUS.register(new FishSpawner());
            Pixelmon.EVENT_BUS.register(new HeadbuttSpawner());
            Pixelmon.EVENT_BUS.register(new NaturalPixelmonSpawnListener());
            Pixelmon.EVENT_BUS.register(new RockSmashSpawner());

            NaturalSpawner.startTimer();

            if (ConfigGetters.disablePixelmonsSpawner) {

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        PixelmonSpawning.coordinator.deactivate();

                    }

                }, 3000);

            }

        } else {

            AreaManager.logger.error("WARNING: AreaManager has detected the tick listener in LypakaUtils is disabled!");
            AreaManager.logger.error("This is not allowed! AreaManager REQUIRES that tick listener be enabled!");
            AreaManager.logger.error("Please enable that in the LypakaUtils config and then run the reload command for LypakaUtils and then the reload command for AreaManager!");

        }

    }

}

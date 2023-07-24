package com.lypaka.betterareas.Listeners;

import com.lypaka.betterareas.BetterAreas;
import com.lypaka.betterareas.ConfigGetters;
import com.lypaka.betterareas.Spawners.FishSpawner;
import com.lypaka.betterareas.Spawners.HeadbuttSpawner;
import com.lypaka.betterareas.Spawners.NaturalSpawner;
import com.lypaka.betterareas.Spawners.RockSmashSpawner;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import java.util.Timer;
import java.util.TimerTask;

@Mod.EventBusSubscriber(modid = BetterAreas.MOD_ID)
public class ServerStartedListener {

    @SubscribeEvent
    public static void onServerStarted (FMLServerStartedEvent event) {

        MinecraftForge.EVENT_BUS.register(new RespawnListener());
        MinecraftForge.EVENT_BUS.register(new TickListener());

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
                    PixelmonSpawning.coordinator = null;

                }

            }, 3000);

        }

    }

}

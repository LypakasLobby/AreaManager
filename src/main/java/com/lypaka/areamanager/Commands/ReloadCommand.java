package com.lypaka.areamanager.Commands;

import com.lypaka.areamanager.Areas.AreaHandler;
import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.ConfigGetters;
import com.lypaka.areamanager.Listeners.*;
import com.lypaka.areamanager.Spawners.FishSpawner;
import com.lypaka.areamanager.Spawners.HeadbuttSpawner;
import com.lypaka.areamanager.Spawners.NaturalSpawner;
import com.lypaka.areamanager.Spawners.RockSmashSpawner;
import com.lypaka.areamanager.Utils.HeldItemUtils;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : AreaManagerCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!c.getSource().getServer().isSinglePlayer()) {

                                                        if (!PermissionHandler.hasPermission(player, "areamanager.command.admin")) {

                                                            player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                            return 0;

                                                        }

                                                    } else {

                                                        if (!player.getName().getString().equalsIgnoreCase("Lypaka")) {

                                                            player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                            return 0;

                                                        }

                                                    }

                                                }

                                                try {

                                                    AreaManager.configManager.load();
                                                    ConfigGetters.load();
                                                    AreaHandler.loadAreas();
                                                    HeldItemUtils.load();
                                                    if (!ServerStartedListener.modActive) {

                                                        AreaManager.logger.info("Detected the reload of AreaManager with the event listeners disabled, attempting to enable...");
                                                        if (com.lypaka.lypakautils.ConfigGetters.tickListenerEnabled) {

                                                            ServerStartedListener.modActive = true;
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
                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded AreaManager!"), true);

                                                } catch (ObjectMappingException | IOException e) {

                                                    e.printStackTrace();

                                                }

                                                return 1;

                                            })
                            )
            );

        }

    }

}

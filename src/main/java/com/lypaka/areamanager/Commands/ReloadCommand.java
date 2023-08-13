package com.lypaka.areamanager.Commands;

import com.lypaka.areamanager.Areas.AreaHandler;
import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.ConfigGetters;
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

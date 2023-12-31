package com.lypaka.areamanager.Commands;

import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.Areas.AreaHandler;
import com.lypaka.areamanager.ConfigGetters;
import com.lypaka.areamanager.Regions.RegionHandler;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

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
                                                    RegionHandler.loadRegions();
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

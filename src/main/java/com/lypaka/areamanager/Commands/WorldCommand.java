package com.lypaka.areamanager.Commands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.storage.ServerWorldInfo;

public class WorldCommand {

    public WorldCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : AreaManagerCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("where")
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

                                                    String worldName = ((ServerWorldInfo) player.getServerWorld().getWorldInfo()).getWorldName();
                                                    player.sendMessage(FancyText.getFormattedText("&aWorld name: &e" + worldName), player.getUniqueID());

                                                }

                                                return 1;

                                            })
                            )
            );

        }

    }

}

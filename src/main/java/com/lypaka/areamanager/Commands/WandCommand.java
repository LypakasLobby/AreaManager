package com.lypaka.areamanager.Commands;

import com.lypaka.areamanager.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class WandCommand {

    public WandCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : AreaManagerCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("wand")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "areamanager.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 0;

                                                    }

                                                    player.addItemStackToInventory(ConfigGetters.getWand());
                                                    player.sendMessage(FancyText.getFormattedText("&eLeft click to set POS 1"), player.getUniqueID());
                                                    player.sendMessage(FancyText.getFormattedText("&eRight click to set POS 2"), player.getUniqueID());
                                                    player.sendMessage(FancyText.getFormattedText("&eShift left click to clear"), player.getUniqueID());

                                                }

                                                return 1;

                                            })
                            )
            );

        }

    }

}

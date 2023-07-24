package com.lypaka.betterareas.Commands;

import com.lypaka.betterareas.Areas.AreaHandler;
import com.lypaka.betterareas.BetterAreas;
import com.lypaka.betterareas.ConfigGetters;
import com.lypaka.betterareas.Utils.HeldItemUtils;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterAreasCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "betterareas.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 0;

                                                    }

                                                }

                                                try {

                                                    BetterAreas.configManager.load();
                                                    ConfigGetters.load();
                                                    AreaHandler.loadAreas();
                                                    HeldItemUtils.load();
                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded BetterAreas!"), true);

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

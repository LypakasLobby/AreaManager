package com.lypaka.areamanager.Commands;

import com.lypaka.areamanager.Wand.WandHandler;
import com.lypaka.areamanager.Wand.WandPOS;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

public class CreateRegionCommand {

    public CreateRegionCommand(CommandDispatcher<CommandSource> dispatcher) {

        for (String a : AreaManagerCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("createregion")
                                            .then(
                                                    Commands.argument("name", StringArgumentType.word())
                                                            .executes(c -> {

                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                    if (!PermissionHandler.hasPermission(player, "areamanager.command.admin")) {

                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                        return 0;

                                                                    }
                                                                    if (WandHandler.wandMap.containsKey(player.getUniqueID())) {

                                                                        List<WandPOS> posList = WandHandler.wandMap.get(player.getUniqueID());
                                                                        if (posList.size() > 1) {

                                                                            String name = StringArgumentType.getString(c, "name");
                                                                            try {

                                                                                WandHandler.createRegion(player, name);

                                                                            } catch (ObjectMappingException e) {

                                                                                throw new RuntimeException(e);

                                                                            }

                                                                        } else {

                                                                            player.sendMessage(FancyText.getFormattedText("&cNot enough positions set!"), player.getUniqueID());

                                                                        }

                                                                    } else {

                                                                        player.sendMessage(FancyText.getFormattedText("&cNo positions set! Use &e\"/areas wand\" to set some!"), player.getUniqueID());

                                                                    }

                                                                }

                                                                return 0;

                                                            })
                                            )
                            )
            );

        }

    }

}

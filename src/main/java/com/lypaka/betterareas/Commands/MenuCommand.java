package com.lypaka.betterareas.Commands;

import com.lypaka.betterareas.GUIs.MainMenu;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class MenuCommand {

    public MenuCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : BetterAreasCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("menu")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    try {

                                                        MainMenu.open(player);

                                                    } catch (ObjectMappingException e) {

                                                        e.printStackTrace();

                                                    }

                                                }

                                                return 0;

                                            })
                            )
            );

        }

    }

}

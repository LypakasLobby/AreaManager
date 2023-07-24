package com.lypaka.betterareas.Commands;

import com.lypaka.betterareas.BetterAreas;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = BetterAreas.MOD_ID)
public class BetterAreasCommand {

    public static List<String> ALIASES = Arrays.asList("betterareas", "areas", "bareas");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new MenuCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}

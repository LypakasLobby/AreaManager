package com.lypaka.areamanager.Commands;

import com.lypaka.areamanager.AreaManager;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = AreaManager.MOD_ID)
public class AreaManagerCommand {

    public static List<String> ALIASES = Arrays.asList("areamanager", "areas", "aman");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new ReloadCommand(event.getDispatcher());
        new WorldCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}

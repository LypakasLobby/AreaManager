package com.lypaka.areamanager.Listeners;

import com.lypaka.lypakautils.Listeners.JoinListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

/**
 * Fixes a desync issue with player location upon being killed by swimming
 */
public class RespawnListener {

    @SubscribeEvent
    public void onRespawn (PlayerEvent.PlayerRespawnEvent event) {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        putTheBitchBackIfMissing(player);

    }

    private static void putTheBitchBackIfMissing (ServerPlayerEntity player) {

        UUID uuid = player.getUniqueID();
        JoinListener.playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(uuid.toString()));
        JoinListener.playerMap.put(uuid, player);

    }

}

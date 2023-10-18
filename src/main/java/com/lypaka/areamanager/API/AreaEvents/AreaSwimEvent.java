package com.lypaka.areamanager.API.AreaEvents;

import com.lypaka.areamanager.Areas.Area;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when a player is detected to be swimming in an area
 */
public abstract class AreaSwimEvent extends Event {

    private final ServerPlayerEntity player;
    private final Area area;

    public AreaSwimEvent (ServerPlayerEntity player, Area area) {

        this.player = player;
        this.area = area;

    }

    /**
     * Called when a player is detected to be swimming in an area set to teleport the player
     * Cancelling the event will cancel the teleportation
     */
    @Cancelable
    public static class Teleport extends AreaSwimEvent {

        public Teleport (ServerPlayerEntity player, Area area) {

            super(player, area);

        }

    }

    /**
     * Called when a player is detected to be swimming in an area set to kill the player
     * Cancelling the event will cancel the killing
     */
    @Cancelable
    public static class Kill extends AreaSwimEvent {

        public Kill (ServerPlayerEntity player, Area area) {

            super(player, area);

        }

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Area getArea() {

        return this.area;

    }

}

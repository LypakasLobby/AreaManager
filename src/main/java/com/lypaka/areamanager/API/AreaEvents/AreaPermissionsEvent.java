package com.lypaka.areamanager.API.AreaEvents;

import com.lypaka.areamanager.Areas.Area;
import com.lypaka.areamanager.Areas.AreaPermissions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when the server runs a permission check on a player entering or leaving an area that has permissions to check
 * Cancelling the event will allow the player to bypass the permission check
 */
@Cancelable
public class AreaPermissionsEvent extends Event {

    private final ServerPlayerEntity player;
    private final Area area;
    private final AreaPermissions permissions;

    public AreaPermissionsEvent (ServerPlayerEntity player, Area area, AreaPermissions permissions) {

        this.player = player;
        this.area = area;
        this.permissions = permissions;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Area getArea() {

        return this.area;

    }

    public AreaPermissions getPermissions() {

        return this.permissions;

    }

}

package com.lypaka.areamanager.API.RegionEvents;

import com.lypaka.areamanager.Regions.Region;
import com.lypaka.areamanager.Regions.RegionPermissions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * Called when the server runs a permission check on a player entering or leaving an area that has permissions to check
 * Cancelling the event will allow the player to bypass the permission check
 */
@Cancelable
public class RegionPermissionsEvent extends Event {

    private final ServerPlayerEntity player;
    private final Region region;
    private final RegionPermissions permissions;

    public RegionPermissionsEvent (ServerPlayerEntity player, Region region, RegionPermissions permissions) {

        this.player = player;
        this.region = region;
        this.permissions = permissions;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Region getRegion() {

        return this.region;

    }

    public RegionPermissions getPermissions() {

        return this.permissions;

    }

}

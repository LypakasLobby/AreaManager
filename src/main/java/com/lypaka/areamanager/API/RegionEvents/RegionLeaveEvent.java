package com.lypaka.areamanager.API.RegionEvents;

import com.lypaka.areamanager.Regions.Region;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class RegionLeaveEvent extends Event {

    private final ServerPlayerEntity player;
    private final Region region;
    private boolean canLeave;

    public RegionLeaveEvent (ServerPlayerEntity player, Region region, boolean canLeave) {

        this.player = player;
        this.region = region;
        this.canLeave = canLeave;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Region getRegion() {

        return this.region;

    }

    public boolean playerCanLeave() {

        return this.canLeave;

    }

    public void setCanLeave (boolean canLeave) {

        this.canLeave = canLeave;

    }

}

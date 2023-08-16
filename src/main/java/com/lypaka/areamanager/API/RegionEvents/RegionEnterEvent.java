package com.lypaka.areamanager.API.RegionEvents;

import com.lypaka.areamanager.Regions.Region;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class RegionEnterEvent extends Event {

    private final ServerPlayerEntity player;
    private final Region region;
    private boolean canEnter;

    public RegionEnterEvent (ServerPlayerEntity player, Region region, boolean canEnter) {

        this.player = player;
        this.region = region;
        this.canEnter = canEnter;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Region getRegion() {

        return this.region;

    }

    public boolean playerCanEnter() {

        return this.canEnter;

    }

    public void setCanEnter (boolean canEnter) {

        this.canEnter = canEnter;

    }

}

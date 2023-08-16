package com.lypaka.areamanager.API.AreaEvents;

import com.lypaka.areamanager.Areas.Area;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class AreaEnterEvent extends Event {

    private final ServerPlayerEntity player;
    private final Area area;
    private boolean canEnter;

    public AreaEnterEvent (ServerPlayerEntity player, Area area, boolean canEnter) {

        this.player = player;
        this.area = area;
        this.canEnter = canEnter;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Area getArea() {

        return this.area;

    }

    public boolean playerCanEnter() {

        return this.canEnter;

    }

    public void setCanEnter (boolean canEnter) {

        this.canEnter = canEnter;

    }

}

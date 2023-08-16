package com.lypaka.areamanager.API.AreaEvents;

import com.lypaka.areamanager.Areas.Area;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class AreaLeaveEvent extends Event {

    private final ServerPlayerEntity player;
    private final Area area;
    private boolean canLeave;

    public AreaLeaveEvent (ServerPlayerEntity player, Area area, boolean canLeave) {

        this.player = player;
        this.area = area;
        this.canLeave = canLeave;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Area getArea() {

        return this.area;

    }

    public boolean playerCanLeave() {

        return this.canLeave;

    }

    public void setCanLeave (boolean canLeave) {

        this.canLeave = canLeave;

    }

}

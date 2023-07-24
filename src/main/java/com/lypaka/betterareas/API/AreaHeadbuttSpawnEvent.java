package com.lypaka.betterareas.API;

import com.lypaka.betterareas.Areas.Area;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class AreaHeadbuttSpawnEvent extends Event {

    private final ServerPlayerEntity player;
    private final Area area;
    private final Pokemon playerPokemon;
    private final BlockPos triggerPos;
    private Pokemon toSpawn;

    public AreaHeadbuttSpawnEvent (ServerPlayerEntity player, Area area, Pokemon playerPokemon, BlockPos triggerPos, Pokemon toSpawn) {

        this.player = player;
        this.area = area;
        this.playerPokemon = playerPokemon;
        this.triggerPos = triggerPos;
        this.toSpawn = toSpawn;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public Area getArea() {

        return this.area;

    }

    public Pokemon getPlayerPokemon() {

        return this.playerPokemon;

    }

    public BlockPos getTriggerPos() {

        return this.triggerPos;

    }

    public Pokemon getToSpawn() {

        return this.toSpawn;

    }

    public void setToSpawn (Pokemon pokemon) {

        this.toSpawn = pokemon;

    }

}

package com.lypaka.betterareas.Utils.ExternalAbilities;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class Synchronize {

    public static boolean applies (Pokemon pokemon) {

        if (pokemon == null) return false;
        return pokemon.getAbility().getLocalizedName().equalsIgnoreCase("Synchronize");

    }

    public static void applySynchronize (Pokemon wildPokemon, Pokemon playersPokemon) {

        wildPokemon.setNature(playersPokemon.getNature());

    }

}

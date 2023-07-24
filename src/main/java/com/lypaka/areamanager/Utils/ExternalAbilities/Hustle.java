package com.lypaka.areamanager.Utils.ExternalAbilities;

import com.lypaka.areamanager.Areas.Spawns.PokemonSpawn;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;

public class Hustle {

    public static boolean applies (Pokemon pokemon) {

        if (pokemon == null) return false;
        return pokemon.getAbility().getLocalizedName().equalsIgnoreCase("Hustle");

    }

    public static int tryHustle (int level, PokemonSpawn spawn) {

        if (!RandomHelper.getRandomChance(50)) return level;

        return spawn.getMaxLevel();

    }

}

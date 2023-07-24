package com.lypaka.areamanager.Utils.ExternalAbilities;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class Illuminate {

    public static boolean applies (Pokemon pokemon) {

        if (pokemon == null) return false;
        return pokemon.getAbility().getLocalizedName().equalsIgnoreCase("Illuminate");

    }

}

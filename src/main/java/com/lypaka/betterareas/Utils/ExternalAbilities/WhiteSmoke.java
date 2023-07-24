package com.lypaka.betterareas.Utils.ExternalAbilities;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class WhiteSmoke {

    public static boolean applies (Pokemon pokemon) {

        if (pokemon == null) return false;
        return pokemon.getAbility().getLocalizedName().equalsIgnoreCase("WhiteSmoke") || pokemon.getAbility().getLocalizedName().equalsIgnoreCase("White Smoke");

    }

}

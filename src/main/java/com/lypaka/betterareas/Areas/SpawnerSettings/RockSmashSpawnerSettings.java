package com.lypaka.betterareas.Areas.SpawnerSettings;

import java.util.List;

public class RockSmashSpawnerSettings {

    private final double autoBattleChance;
    private final boolean clearSpawns;
    private final int cooldown;
    private final List<String> customRockSmashRockIDs;
    private final boolean despawnAfterBattle;
    private final int despawnTimer;
    private final boolean reducePP;
    private final boolean requireMove;
    private final boolean usePixelmonsSystem;

    public RockSmashSpawnerSettings (double autoBattleChance, boolean clearSpawns, int cooldown, List<String> customRockSmashRockIDs, boolean despawnAfterBattle,
                                    int despawnTimer, boolean reducePP, boolean requireMove, boolean usePixelmonsSystem) {

        this.autoBattleChance = autoBattleChance;
        this.clearSpawns = clearSpawns;
        this.cooldown = cooldown;
        this.customRockSmashRockIDs = customRockSmashRockIDs;
        this.despawnAfterBattle = despawnAfterBattle;
        this.despawnTimer = despawnTimer;
        this.reducePP = reducePP;
        this.requireMove = requireMove;
        this.usePixelmonsSystem = usePixelmonsSystem;

    }

    public double getAutoBattleChance() {

        return this.autoBattleChance;

    }

    public boolean doesClearSpawns() {

        return this.clearSpawns;

    }

    public int getCooldown() {

        return this.cooldown;

    }

    public List<String> getCustomRockSmashRockIDs() {

        return this.customRockSmashRockIDs;

    }

    public boolean doesDespawnAfterBattle() {

        return this.despawnAfterBattle;

    }

    public int getDespawnTimer() {

        return this.despawnTimer;

    }

    public boolean doesReducePP() {

        return this.reducePP;

    }

    public boolean doesRequireMove() {

        return this.requireMove;

    }

    public boolean doesUsePixelmonsSystem() {

        return this.usePixelmonsSystem;

    }

}

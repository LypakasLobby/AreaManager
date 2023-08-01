package com.lypaka.areamanager.Areas.SpawnerSettings;

import java.util.List;

public class GrassSpawnerSettings {

    private final boolean autoBattle;
    private final List<String> blockIDs;
    private final boolean despawnAfterBattle;
    private final double spawnChance;

    public GrassSpawnerSettings (boolean autoBattle, List<String> blockIDs, boolean despawnAfterBattle, double spawnChance) {

        this.autoBattle = autoBattle;
        this.blockIDs = blockIDs;
        this.despawnAfterBattle = despawnAfterBattle;
        this.spawnChance = spawnChance;

    }

    public boolean doesAutoBattle() {

        return this.autoBattle;

    }

    public List<String> getBlockIDs() {

        return this.blockIDs;

    }

    public boolean doesDespawnAfterBattle() {

        return this.despawnAfterBattle;

    }

    public double getSpawnChance() {

        return this.spawnChance;

    }

}

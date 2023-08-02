package com.lypaka.areamanager.Areas.SpawnerSettings;

import java.util.List;
import java.util.Map;

public class GrassSpawnerSettings {

    private final boolean autoBattle;
    private final List<String> blockIDs;
    private final boolean despawnAfterBattle;
    private final Map<String, String> messagesMap;
    private final double spawnChance;

    public GrassSpawnerSettings (boolean autoBattle, List<String> blockIDs, boolean despawnAfterBattle, Map<String, String> messagesMap, double spawnChance) {

        this.autoBattle = autoBattle;
        this.blockIDs = blockIDs;
        this.despawnAfterBattle = despawnAfterBattle;
        this.messagesMap = messagesMap;
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

    public Map<String, String> getMessagesMap() {

        return this.messagesMap;

    }

    public double getSpawnChance() {

        return this.spawnChance;

    }

}

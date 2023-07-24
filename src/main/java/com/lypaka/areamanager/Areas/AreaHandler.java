package com.lypaka.areamanager.Areas;

import com.google.common.reflect.TypeToken;
import com.lypaka.areamanager.Areas.SpawnerSettings.FishSpawnerSettings;
import com.lypaka.areamanager.Areas.SpawnerSettings.HeadbuttSpawnerSettings;
import com.lypaka.areamanager.Areas.SpawnerSettings.NaturalSpawnerSettings;
import com.lypaka.areamanager.Areas.SpawnerSettings.RockSmashSpawnerSettings;
import com.lypaka.areamanager.Areas.Spawns.*;
import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.ConfigGetters;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ComplexConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import net.minecraft.world.World;
import net.minecraft.world.storage.ServerWorldInfo;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class AreaHandler {

    public static Map<String, Area> areaMap = new HashMap<>();
    public static Map<Area, AreaSpawns> areaSpawnMap = new HashMap<>();
    public static Map<Area, List<UUID>> playersInArea = new HashMap<>();

    public static void loadAreas() throws IOException, ObjectMappingException {

        String[] files = new String[]{"settings.conf"};
        for (String area : ConfigGetters.areaNames) {

            Path dir = ConfigUtils.checkDir(Paths.get("./config/areamanager/areas/" + area));
            BasicConfigManager bcm = new BasicConfigManager(files, dir, AreaManager.class, AreaManager.MOD_NAME, AreaManager.MOD_ID, AreaManager.logger);
            bcm.init();

            String displayName = bcm.getConfigNode(0, "General-Settings", "Area-Display-Name").getString();
            int maxX = bcm.getConfigNode(0, "General-Settings", "Area-Location", "Max-X").getInt();
            int maxY = bcm.getConfigNode(0, "General-Settings", "Area-Location", "Max-Y").getInt();
            int maxZ = bcm.getConfigNode(0, "General-Settings", "Area-Location", "Max-Z").getInt();
            int minX = bcm.getConfigNode(0, "General-Settings", "Area-Location", "Min-X").getInt();
            int minY = bcm.getConfigNode(0, "General-Settings", "Area-Location", "Min-Y").getInt();
            int minZ = bcm.getConfigNode(0, "General-Settings", "Area-Location", "Min-Z").getInt();
            String worldName = bcm.getConfigNode(0, "General-Settings", "Area-Location", "World-Name").getString();
            String enterMessage = bcm.getConfigNode(0, "General-Settings", "Area-Messages", "Enter").getString();
            String leaveMessage = bcm.getConfigNode(0, "General-Settings", "Area-Messages", "Leave").getString();
            String plainName = bcm.getConfigNode(0, "General-Settings", "Area-Plain-Name").getString();
            boolean cancelsConcussions = bcm.getConfigNode(0, "General-Settings", "Cancels-Concussions").getBoolean();
            String enterPermissionMessage = bcm.getConfigNode(0, "General-Settings", "Permissions", "Enter", "Message").getString();
            List<String> enterPermissions = bcm.getConfigNode(0, "General-Settings", "Permissions", "Enter", "Permissions").getList(TypeToken.of(String.class));
            String enterTeleportLocation = bcm.getConfigNode(0, "General-Settings", "Permissions", "Enter", "Teleport").getString();
            String leavePermissionMessage = bcm.getConfigNode(0, "General-Settings", "Permissions", "Leave", "Message").getString();
            List<String> leavePermissions = bcm.getConfigNode(0, "General-Settings", "Permissions", "Leave", "Permissions").getList(TypeToken.of(String.class));
            String leaveTeleportLocation = bcm.getConfigNode(0, "General-Settings", "Permissions", "Leave", "Teleport").getString();
            AreaPermissions permissions = new AreaPermissions(enterPermissionMessage, enterPermissions, enterTeleportLocation, leavePermissionMessage, leavePermissions, leaveTeleportLocation);
            boolean preventSwimming = bcm.getConfigNode(0, "General-Settings", "Prevent-Swimming").getBoolean();
            int priority = bcm.getConfigNode(0, "General-Settings", "Priority").getInt();
            int radius = bcm.getConfigNode(0, "General-Settings", "Radius").getInt();
            int underground = bcm.getConfigNode(0, "General-Settings", "Underground").getInt();

            boolean clearFishSpawns = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Fish-Spawner", "Clear-Spawns").getBoolean();
            boolean despawnAfterFishBattle = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Fish-Spawner", "Despawn-After-Battle").getBoolean();
            int fishDespawnTimer = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Fish-Spawner", "Despawn-Timer").getInt();
            FishSpawnerSettings fishSpawnerSettings = new FishSpawnerSettings(clearFishSpawns, despawnAfterFishBattle, fishDespawnTimer);

            double headbuttAutoBattleChance = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Auto-Battle-Chance").getDouble();
            boolean clearHeadbuttSpawns = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Clear-Spawns").getBoolean();
            int headbuttCooldown = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Cooldown").getInt();
            List<String> customHeadbuttTreeIDs = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Custom-Headbutt-Tree-IDs").getList(TypeToken.of(String.class));
            boolean despawnHeadbuttAfterBattle = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Despawn-After-Battle").getBoolean();
            int headbuttDespawnTimer = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Despawn-Timer").getInt();
            boolean reduceHeadbuttPP = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Reduce-PP").getBoolean();
            boolean requireHeadbuttMove = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Require-Move").getBoolean();
            boolean usePixelmonsHeadbuttSystem = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Headbutt-Spawner", "Use-Pixelmons-System").getBoolean();
            HeadbuttSpawnerSettings headbuttSpawnerSettings = new HeadbuttSpawnerSettings(headbuttAutoBattleChance, clearHeadbuttSpawns, headbuttCooldown, customHeadbuttTreeIDs,
                    despawnHeadbuttAfterBattle, headbuttDespawnTimer, reduceHeadbuttPP, requireHeadbuttMove, usePixelmonsHeadbuttSystem);

            double naturalAutoBattleChance = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Natural-Spawner", "Auto-Battle-Chance").getDouble();
            boolean clearNaturalSpawns = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Natural-Spawner", "Clear-Spawns").getBoolean();
            boolean despawnNaturalAfterBattle = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Natural-Spawner", "Despawn-After-Battle").getBoolean();
            int naturalDespawnTimer = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Natural-Spawner", "Despawn-Timer").getInt();
            boolean limitSpawns = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Natural-Spawner", "Limit-Spawns").getBoolean();
            Map<String, String> messages = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Natural-Spawner", "Messages").getValue(new TypeToken<Map<String, String>>() {});
            boolean preventPixelmonSpawns = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Natural-Spawner", "Prevent-Pixelmon-Spawns").getBoolean();
            int spawnInterval = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Natural-Spawner", "Spawn-Interval").getInt();
            NaturalSpawnerSettings naturalSpawnerSettings = new NaturalSpawnerSettings(naturalAutoBattleChance, clearNaturalSpawns, despawnNaturalAfterBattle, naturalDespawnTimer, limitSpawns, messages, preventPixelmonSpawns, spawnInterval);

            double rockSmashAutoBattleChance = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Auto-Battle-Chance").getDouble();
            boolean clearRockSmashSpawns = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Clear-Spawns").getBoolean();
            int rockSmashCooldown = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Cooldown").getInt();
            List<String> customRockSmashIDs = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Custom-Rock-Smash-Rock-IDs").getList(TypeToken.of(String.class));
            boolean despawnRockSmashAfterBattle = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Despawn-After-Battle").getBoolean();
            int rockSmashDespawnTimer = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Despawn-Timer").getInt();
            boolean reduceRockSmashPP = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Reduce-PP").getBoolean();
            boolean requireRockSmashMove = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Require-Move").getBoolean();
            boolean usePixelmonsRockSmashSystem = bcm.getConfigNode(0, "General-Settings", "Spawner-Settings", "Rock-Smash-Spawner", "Use-Pixelmons-System").getBoolean();
            RockSmashSpawnerSettings rockSmashSpawnerSettings = new RockSmashSpawnerSettings(rockSmashAutoBattleChance, clearRockSmashSpawns, rockSmashCooldown, customRockSmashIDs,
                    despawnRockSmashAfterBattle, rockSmashDespawnTimer, reduceRockSmashPP, requireRockSmashMove, usePixelmonsRockSmashSystem);

            Area a = new Area(area, displayName, maxX, maxY, maxZ, minX, minY, minZ, worldName, enterMessage, leaveMessage, plainName, cancelsConcussions, preventSwimming,
                    permissions, fishSpawnerSettings, headbuttSpawnerSettings, naturalSpawnerSettings, rockSmashSpawnerSettings, priority, radius, underground);
            a.create();

            AreaManager.areaConfigManager.put(area, bcm);

            List<String> fishSpawns = bcm.getConfigNode(0, "Fish-Spawns").getList(TypeToken.of(String.class));
            ComplexConfigManager fcm = new ComplexConfigManager(fishSpawns, "fish-spawns", "fishSpawnTemplate.conf", dir, AreaManager.class, AreaManager.MOD_NAME, AreaManager.MOD_ID, AreaManager.logger);
            fcm.init();
            List<FishSpawn> fishSpawnsList = new ArrayList<>();
            for (int i = 0; i < fishSpawns.size(); i++) {

                String species = fcm.getConfigNode(i, "Pokemon-Data", "Species").getString();
                String form = fcm.getConfigNode(i, "Pokemon-Data", "Form").getString();
                int minLevel = fcm.getConfigNode(i, "Pokemon-Data", "Min-Level").getInt();
                int maxLevel = fcm.getConfigNode(i, "Pokemon-Data", "Max-Level").getInt();
                Map<String, Map<String, Map<String, Map<String, String>>>> spawnData = fcm.getConfigNode(i, "Spawn-Data").getValue(new TypeToken<Map<String, Map<String, Map<String, Map<String, String>>>>>() {});

                FishSpawn fishSpawn = new FishSpawn(species, form, minLevel, maxLevel, spawnData);
                fishSpawnsList.add(fishSpawn);

            }

            List<String> headbuttSpawns = bcm.getConfigNode(0, "Headbutt-Spawns").getList(TypeToken.of(String.class));
            ComplexConfigManager hcm = new ComplexConfigManager(headbuttSpawns, "headbutt-spawns", "headbuttSpawnTemplate.conf", dir, AreaManager.class, AreaManager.MOD_NAME, AreaManager.MOD_ID, AreaManager.logger);
            hcm.init();
            List<HeadbuttSpawn> headbuttSpawnsList = new ArrayList<>();
            for (int i = 0; i < headbuttSpawns.size(); i++) {

                String species = hcm.getConfigNode(i, "Pokemon-Data", "Species").getString();
                String form = hcm.getConfigNode(i, "Pokemon-Data", "Form").getString();
                int minLevel = hcm.getConfigNode(i, "Pokemon-Data", "Min-Level").getInt();
                int maxLevel = hcm.getConfigNode(i, "Pokemon-Data", "Max-Level").getInt();
                Map<String, Map<String, Map<String, String>>> spawnData = hcm.getConfigNode(i, "Spawn-Data").getValue(new TypeToken<Map<String, Map<String, Map<String, String>>>>() {});

                HeadbuttSpawn headbuttSpawn = new HeadbuttSpawn(species, form, minLevel, maxLevel, spawnData);
                headbuttSpawnsList.add(headbuttSpawn);

            }

            List<String> naturalSpawns = bcm.getConfigNode(0, "Natural-Spawns").getList(TypeToken.of(String.class));
            ComplexConfigManager ncm = new ComplexConfigManager(naturalSpawns, "natural-spawns", "naturalSpawnTemplate.conf", dir, AreaManager.class, AreaManager.MOD_NAME, AreaManager.MOD_ID, AreaManager.logger);
            ncm.init();
            List<NaturalSpawn> naturalSpawnsList = new ArrayList<>();
            for (int i = 0; i < naturalSpawns.size(); i++) {

                String species = ncm.getConfigNode(i, "Pokemon-Data", "Species").getString();
                String form = ncm.getConfigNode(i, "Pokemon-Data", "Form").getString();
                int minLevel = ncm.getConfigNode(i, "Pokemon-Data", "Min-Level").getInt();
                int maxLevel = ncm.getConfigNode(i, "Pokemon-Data", "Max-Level").getInt();

                Map<String, Map<String, Map<String, String>>> spawnData = ncm.getConfigNode(i, "Spawn-Data").getValue(new TypeToken<Map<String, Map<String, Map<String, String>>>>() {});
                NaturalSpawn naturalSpawn = new NaturalSpawn(species, form, minLevel, maxLevel, spawnData);
                naturalSpawnsList.add(naturalSpawn);

            }

            List<String> rockSmashSpawns = bcm.getConfigNode(0, "Rock-Smash-Spawns").getList(TypeToken.of(String.class));
            ComplexConfigManager rcm = new ComplexConfigManager(rockSmashSpawns, "rock-smash-spawns", "rockSmashTemplate.conf", dir, AreaManager.class, AreaManager.MOD_NAME, AreaManager.MOD_ID, AreaManager.logger);
            rcm.init();
            List<RockSmashSpawn> rockSmashSpawnsList = new ArrayList<>();
            for (int i = 0; i < rockSmashSpawns.size(); i++) {

                String species = rcm.getConfigNode(i, "Pokemon-Data", "Species").getString();
                String form = rcm.getConfigNode(i, "Pokemon-Data", "Form").getString();
                int minLevel = rcm.getConfigNode(i, "Pokemon-Data", "Min-Level").getInt();
                int maxLevel = rcm.getConfigNode(i, "Pokemon-Data", "Max-Level").getInt();

                Map<String, Map<String, Map<String, String>>> spawnData = rcm.getConfigNode(i, "Spawn-Data").getValue(new TypeToken<Map<String, Map<String, Map<String, String>>>>() {});
                RockSmashSpawn rockSmashSpawn = new RockSmashSpawn(species, form, minLevel, maxLevel, spawnData);
                rockSmashSpawnsList.add(rockSmashSpawn);

            }

            AreaSpawns spawns = new AreaSpawns(a, fishSpawnsList, headbuttSpawnsList, naturalSpawnsList, rockSmashSpawnsList);
            areaSpawnMap.put(a, spawns);

        }

    }

    public static Area getFromName (String name) {

        Area area = null;
        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {

            if (entry.getKey().equalsIgnoreCase(name)) {

                area = entry.getValue();
                break;

            }

        }

        return area;

    }

    public static List<Area> getSortedAreas (int x, int y, int z, World world) {

        List<Area> areas = getFromLocation(x, y, z, world);
        List<Area> sortedAreas = new ArrayList<>(areas.size());
        Map<Integer, Area> priorityMap = new HashMap<>();
        for (Area a : areas) {

            priorityMap.put(a.getPriority(), a);

        }
        List<Integer> priorities = new ArrayList<>(priorityMap.keySet());
        Collections.sort(priorities);
        for (int i = 0; i < priorities.size(); i++) {

            sortedAreas.add(i, priorityMap.get(priorities.get(i)));

        }

        return sortedAreas;

    }

    public static Area getHighestPriorityArea (int x, int y, int z, World world) {

        List<Area> areas = getFromLocation(x, y, z, world);
        Map<Integer, Area> priorityMap = new HashMap<>();
        for (Area a : areas) {

            priorityMap.put(a.getPriority(), a);

        }
        List<Integer> priorities = new ArrayList<>(priorityMap.keySet());
        Collections.sort(priorities);
        return priorityMap.get(priorities.get(priorities.size() - 1));

    }

    public static List<Area> getFromLocation (int x, int y, int z, World world) {

        String currentWorld = ((ServerWorldInfo) world.getWorldInfo()).getWorldName();
        List<Area> areas = new ArrayList<>();
        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {

            Area a = entry.getValue();
            int maxX = a.getMaxX();
            int maxY = a.getMaxY();
            int maxZ = a.getMaxZ();
            int minX = a.getMinX();
            int minY = a.getMinY();
            int minZ = a.getMinZ();
            String worldName = a.getWorldName();

            if (x >= minX && x <= maxX) {

                if (y >= minY && y <= maxY) {

                    if (z >= minZ && z <= maxZ) {

                        if (currentWorld.equalsIgnoreCase(worldName)) {

                            areas.add(a);

                        }

                    }

                }

            }

        }

        return areas;

    }

}

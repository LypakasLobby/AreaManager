package com.lypaka.areamanager;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static List<String> areaNames;
    public static boolean disablePixelmonsSpawner;

    public static int mainMenuRows;
    public static String mainMenuTitle;
    public static Map<String, Map<String, String>> mainMenuSlotsMap;
    public static int spawnMainMenuRows;
    public static String spawnMainMenuTitle;
    public static Map<String, Map<String, String>> spawnMainMenuSlotsMap;
    public static String allSpawnsMenuFormatName;
    public static List<String> allSpawnsMenuFormatLore;
    public static int allSpawnsMenuRows;
    public static String allSpawnsMenuTitle;
    public static Map<String, Map<String, String>> allSpawnsMenuSlotsMap;
    public static String possibleSpawnsMenuFormatName;
    public static List<String> possibleSpawnsMenuFormatLore;
    public static int possibleSpawnsMenuRows;
    public static String possibleSpawnsMenuTitle;
    public static Map<String, Map<String, String>> possibleSpawnsMenuSlotsMap;

    public static void load() throws ObjectMappingException {

        areaNames = AreaManager.configManager.getConfigNode(0, "Area-Names").getList(TypeToken.of(String.class));
        disablePixelmonsSpawner = AreaManager.configManager.getConfigNode(0, "Disable-Pixelmons-Spawner").getBoolean();

        mainMenuRows = AreaManager.configManager.getConfigNode(2, "Main-Menu", "General", "Rows").getInt();
        mainMenuTitle = AreaManager.configManager.getConfigNode(2, "Main-Menu", "General", "Title").getString();
        mainMenuSlotsMap = AreaManager.configManager.getConfigNode(2, "Main-Menu", "Slots").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        spawnMainMenuRows = AreaManager.configManager.getConfigNode(2, "Spawn-Main-Menu", "General", "Rows").getInt();
        spawnMainMenuTitle = AreaManager.configManager.getConfigNode(2, "Spawn-Main-Menu", "General", "Title").getString();
        spawnMainMenuSlotsMap = AreaManager.configManager.getConfigNode(2, "Spawn-Main-Menu", "Slots").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        allSpawnsMenuFormatName = AreaManager.configManager.getConfigNode(2, "Spawns-All", "Format", "Display-Name").getString();
        allSpawnsMenuFormatLore = AreaManager.configManager.getConfigNode(2, "Spawns-All", "Format", "Lore").getList(TypeToken.of(String.class));
        allSpawnsMenuRows = AreaManager.configManager.getConfigNode(2, "Spawns-All", "General", "Rows").getInt();
        allSpawnsMenuTitle = AreaManager.configManager.getConfigNode(2, "Spawns-All", "General", "Title").getString();
        allSpawnsMenuSlotsMap = AreaManager.configManager.getConfigNode(2, "Spawns-All", "Slots").getValue(new TypeToken<Map<String, Map<String, String>>>() {});
        possibleSpawnsMenuFormatName = AreaManager.configManager.getConfigNode(2, "Spawns-Possible", "Format", "Display-Name").getString();
        possibleSpawnsMenuFormatLore = AreaManager.configManager.getConfigNode(2, "Spawns-Possible", "Format", "Lore").getList(TypeToken.of(String.class));
        possibleSpawnsMenuRows = AreaManager.configManager.getConfigNode(2, "Spawns-Possible", "General", "Rows").getInt();
        possibleSpawnsMenuTitle = AreaManager.configManager.getConfigNode(2, "Spawns-Possible", "General", "Title").getString();
        possibleSpawnsMenuSlotsMap = AreaManager.configManager.getConfigNode(2, "Spawns-Possible", "Slots").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

    }

}

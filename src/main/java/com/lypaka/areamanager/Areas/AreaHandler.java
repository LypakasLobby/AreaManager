package com.lypaka.areamanager.Areas;

import com.google.common.reflect.TypeToken;
import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.ConfigGetters;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
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
    public static Map<Area, List<UUID>> playersInArea = new HashMap<>();

    public static void loadAreas() throws IOException, ObjectMappingException {

        String[] files = new String[]{"settings.conf"};
        for (String area : ConfigGetters.areaNames) {

            Path dir = ConfigUtils.checkDir(Paths.get("./config/areamanager/areas/" + area));
            BasicConfigManager bcm = new BasicConfigManager(files, dir, AreaManager.class, AreaManager.MOD_NAME, AreaManager.MOD_ID, AreaManager.logger);
            bcm.init();

            String displayName = bcm.getConfigNode(0, "Area-Display-Name").getString();
            int maxX = bcm.getConfigNode(0, "Area-Location", "Max-X").getInt();
            int maxY = bcm.getConfigNode(0, "Area-Location", "Max-Y").getInt();
            int maxZ = bcm.getConfigNode(0, "Area-Location", "Max-Z").getInt();
            int minX = bcm.getConfigNode(0, "Area-Location", "Min-X").getInt();
            int minY = bcm.getConfigNode(0, "Area-Location", "Min-Y").getInt();
            int minZ = bcm.getConfigNode(0, "Area-Location", "Min-Z").getInt();
            String worldName = bcm.getConfigNode(0, "Area-Location", "World-Name").getString();
            String enterMessage = bcm.getConfigNode(0, "Area-Messages", "Enter").getString();
            String leaveMessage = bcm.getConfigNode(0, "Area-Messages", "Leave").getString();
            String plainName = bcm.getConfigNode(0, "Area-Plain-Name").getString();
            String enterPermissionMessage = bcm.getConfigNode(0, "Permissions", "Enter", "Message").getString();
            List<String> enterPermissions = bcm.getConfigNode(0, "Permissions", "Enter", "Permissions").getList(TypeToken.of(String.class));
            String enterTeleportLocation = bcm.getConfigNode(0, "Permissions", "Enter", "Teleport").getString();
            String leavePermissionMessage = bcm.getConfigNode(0, "Permissions", "Leave", "Message").getString();
            List<String> leavePermissions = bcm.getConfigNode(0, "Permissions", "Leave", "Permissions").getList(TypeToken.of(String.class));
            String leaveTeleportLocation = bcm.getConfigNode(0, "Permissions", "Leave", "Teleport").getString();
            AreaPermissions permissions = new AreaPermissions(enterPermissionMessage, enterPermissions, enterTeleportLocation, leavePermissionMessage, leavePermissions, leaveTeleportLocation);
            boolean killsForSwimming = bcm.getConfigNode(0, "Swim-Settings", "Kill-For-Swimming").getBoolean();
            boolean teleportsForSwimming = bcm.getConfigNode(0, "Swim-Settings", "Teleport-For-Swimming").getBoolean();
            int priority = bcm.getConfigNode(0, "Priority").getInt();
            int radius = bcm.getConfigNode(0, "Radius").getInt();
            int underground = bcm.getConfigNode(0, "Underground").getInt();

            Area a = new Area(area, displayName, maxX, maxY, maxZ, minX, minY, minZ, worldName, enterMessage, leaveMessage, plainName, killsForSwimming, teleportsForSwimming,
                    permissions, priority, radius, underground);
            a.create();

            AreaManager.areaConfigManager.put(area, bcm);

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

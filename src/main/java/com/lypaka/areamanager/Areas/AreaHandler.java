package com.lypaka.areamanager.Areas;

import com.lypaka.areamanager.Regions.Region;
import com.lypaka.areamanager.Regions.RegionHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.storage.ServerWorldInfo;

import java.util.*;

public class AreaHandler {

    public static Map<Area, List<UUID>> playersInArea = new HashMap<>();

    public static List<Area> getAreasAtPlayer (ServerPlayerEntity player) {

        return getFromLocation(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), player.world);

    }

    public static Area getFromName (String regionName, String areaName) {

        Area area = null;
        Region region = RegionHandler.regionMap.get(regionName);
        List<Area> areas = region.getAreas();
        for (Area a : areas) {

            if (a.getName().equalsIgnoreCase(areaName)) {

                area = a;
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

        List<Area> areas = new ArrayList<>();
        String currentWorld = ((ServerWorldInfo) world.getWorldInfo()).getWorldName();
        for (Map.Entry<String, Region> regionMap : RegionHandler.regionMap.entrySet()) {

            Region r = regionMap.getValue();
            int maxX = r.getMaxX();
            int maxY = r.getMaxY();
            int maxZ = r.getMaxZ();
            int minX = r.getMinX();
            int minY = r.getMinY();
            int minZ = r.getMinZ();
            String worldName = r.getWorldName();
            if (worldName.equalsIgnoreCase(currentWorld)) {

                if (x >= minX && x <= maxX) {

                    if (y >= minY && y <= maxY) {

                        if (z >= minZ && z <= maxZ) {

                            List<Area> regionAreas = r.getAreas();
                            for (Area a : regionAreas) {

                                int areaMaxX = a.getMaxX();
                                int areaMaxY = a.getMaxY();
                                int areaMaxZ = a.getMaxZ();
                                int areaMinX = a.getMinX();
                                int areaMinY = a.getMinY();
                                int areaMinZ = a.getMinZ();
                                String areaWorldName = a.getWorldName();

                                if (currentWorld.equalsIgnoreCase(areaWorldName)) {

                                    if (x >= areaMinX && x <= areaMaxX) {

                                        if (y >= areaMinY && y <= areaMaxY) {

                                            if (z >= areaMinZ && z <= areaMaxZ) {

                                                areas.add(a);

                                            }

                                        }

                                    }

                                }

                            }

                            break;

                        }

                    }

                }

            }

        }

        return areas;

    }

}

package com.lypaka.areamanager.Areas;

import com.lypaka.areamanager.API.AreaEvents.AreaEnterEvent;
import com.lypaka.areamanager.API.AreaEvents.AreaLeaveEvent;
import com.lypaka.areamanager.API.AreaEvents.AreaPermissionsEvent;
import com.lypaka.areamanager.API.AreaEvents.AreaSwimEvent;
import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.Regions.Region;
import com.lypaka.areamanager.Regions.RegionHandler;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.world.World;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.common.MinecraftForge;

import java.util.*;

public class AreaHandler {

    public static Map<String, Map<Area, List<UUID>>> playersInArea = new HashMap<>();
    private static final Map<UUID, Integer> swimCounter = new HashMap<>();

    public static void runSwimCode (ServerPlayerEntity player, Area area) {

        if (player.isInWater()) {

            if (player.isCreative() || player.isSpectator()) return;

            if (player.getRidingEntity() == null) {

                if (area.killsForSwimming()) {

                    AreaSwimEvent.Kill killEvent = new AreaSwimEvent.Kill(player, area);
                    MinecraftForge.EVENT_BUS.post(killEvent);
                    if (!killEvent.isCanceled()) {

                        int counter = 0;
                        if (swimCounter.containsKey(player.getUniqueID())) {

                            counter = swimCounter.get(player.getUniqueID());

                        }
                        counter++;
                        if (counter >= 5) {

                            player.onKillCommand();
                            swimCounter.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));

                        } else {

                            swimCounter.put(player.getUniqueID(), counter);

                        }

                    }

                } else if (area.teleportsForSwimming()) {

                    AreaSwimEvent.Teleport teleportEvent = new AreaSwimEvent.Teleport(player, area);
                    MinecraftForge.EVENT_BUS.post(teleportEvent);
                    if (!teleportEvent.isCanceled()) {

                        PlayerLocation playerLocation = PlayerDataHandler.playerLocationMap.get(player.getUniqueID());
                        int x = playerLocation.getLastLandLocation()[0];
                        int y = playerLocation.getLastLandLocation()[1];
                        int z = playerLocation.getLastLandLocation()[2];
                        player.setPosition(x, y, z);

                    }

                }

            } else {

                swimCounter.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));

            }

        } else {

            swimCounter.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));

        }

    }

    public static boolean canPlayerEnterArea (ServerPlayerEntity player, Area area) {

        AreaPermissions permissions = area.getPermissions();
        boolean hasAreaPermission = true;
        for (String p : permissions.getEnterPermissions()) {

            if (!PermissionHandler.hasPermission(player, p)) {

                hasAreaPermission = false;
                break;

            }

        }
        AreaPermissionsEvent areaPermissionsEvent = new AreaPermissionsEvent(player, area, permissions);
        MinecraftForge.EVENT_BUS.post(areaPermissionsEvent);
        if (areaPermissionsEvent.isCanceled()) hasAreaPermission = true;
        AreaEnterEvent areaEnterEvent = new AreaEnterEvent(player, area, hasAreaPermission);
        MinecraftForge.EVENT_BUS.post(areaEnterEvent);
        if (areaEnterEvent.isCanceled()) hasAreaPermission = false;
        if (!areaEnterEvent.playerCanEnter()) hasAreaPermission = false;
        return hasAreaPermission;

    }

    public static boolean canPlayerLeaveArea (ServerPlayerEntity player, Area area) {

        boolean hasPermission = true;
        AreaPermissions permissions = area.getPermissions();
        for (String p : permissions.getLeavePermissions()) {

            if (!PermissionHandler.hasPermission(player, p)) {

                hasPermission = false;
                break;

            }

        }
        AreaPermissionsEvent permissionsEvent = new AreaPermissionsEvent(player, area, permissions);
        MinecraftForge.EVENT_BUS.post(permissionsEvent);
        if (permissionsEvent.isCanceled()) hasPermission = true;
        AreaLeaveEvent areaLeaveEvent = new AreaLeaveEvent(player, area, hasPermission);
        MinecraftForge.EVENT_BUS.post(areaLeaveEvent);
        if (areaLeaveEvent.isCanceled()) hasPermission = false;
        if (!areaLeaveEvent.playerCanLeave()) hasPermission = false;
        return hasPermission;

    }

    public static Area getAreaPlayerCantLeave (ServerPlayerEntity player) {

        Area area = null;
        boolean hasPermission = true;
        for (Area a : getAreasAtPlayer(player)) {

            AreaPermissions permissions = a.getPermissions();
            for (String p : permissions.getLeavePermissions()) {

                if (!PermissionHandler.hasPermission(player, p)) {

                    hasPermission = false;
                    break;

                }

            }
            AreaPermissionsEvent permissionsEvent = new AreaPermissionsEvent(player, a, permissions);
            MinecraftForge.EVENT_BUS.post(permissionsEvent);
            if (permissionsEvent.isCanceled()) hasPermission = true;
            if (!hasPermission) {

                area = a;
                break;

            }

        }

        return area;

    }

    public static void teleportPlayerToAreaFailedToEnterLocation (ServerPlayerEntity player, Area area) {

        AreaPermissions permissions = area.getPermissions();
        if (!permissions.getEnterTeleportLocation().equalsIgnoreCase("x,y,z")) {

            int tpX = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[0]);
            int tpY = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[1]);
            int tpZ = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[2]);
            player.setPositionAndUpdate(tpX, tpY, tpZ);
            if (!permissions.getEnterMessage().equals("")) {

                player.sendMessage(FancyText.getFormattedText(permissions.getEnterMessage()), player.getUniqueID());

            }

        } else {

            AreaManager.logger.warn("Attempted to teleport player to \"failed to enter\" area location, but that value has not been set for Area: " + area.getName() + "!");

        }

    }

    public static void teleportPlayerToAreaFailedToLeaveLocation (ServerPlayerEntity player, Area area) {

        AreaPermissions permissions = area.getPermissions();
        if (!permissions.getLeaveTeleportLocation().equalsIgnoreCase("x,y,z")) {

            int tpX = Integer.parseInt(permissions.getLeaveTeleportLocation().split(",")[0]);
            int tpY = Integer.parseInt(permissions.getLeaveTeleportLocation().split(",")[1]);
            int tpZ = Integer.parseInt(permissions.getLeaveTeleportLocation().split(",")[2]);
            player.setPositionAndUpdate(tpX, tpY, tpZ);
            if (!permissions.getLeaveMessage().equals("")) {

                player.sendMessage(FancyText.getFormattedText(permissions.getLeaveMessage()), player.getUniqueID());

            }

        } else {

            AreaManager.logger.warn("Attempted to teleport player to \"failed to leave\" area location, but that value has not been set for Area: " + area.getName() + "!");

        }

    }

    public static void removePlayerFromArea (ServerPlayerEntity player, Area area) {

        Region region = RegionHandler.getRegionAtPlayer(player);
        playersInArea.get(region.getName()).get(area).removeIf(e -> e.toString().equalsIgnoreCase(player.getUniqueID().toString()));

    }

    public static void addPlayerToArea (ServerPlayerEntity player, Region region, Area area) {

        String regionName;
        if (region == null) {

            regionName = "None";

        } else {

            regionName = region.getName();

        }
        List<UUID> uuids = new ArrayList<>();
        Map<Area, List<UUID>> map = new HashMap<>();
        if (playersInArea.containsKey(regionName)) {

            map = playersInArea.get(regionName);
            if (map.containsKey(area)) uuids = map.get(area);

        }
        uuids.add(player.getUniqueID());
        map.put(area, uuids);
        playersInArea.put(regionName, map);

        STitlePacket title = new STitlePacket(STitlePacket.Type.TITLE,
                FancyText.getFormattedText(area.getEnterMessage().replace("%plainName%", area.getPlainName())),
                10, 10, 10);
        player.connection.sendPacket(title);

    }

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

    public static List<Area> getSortedAreas (ServerPlayerEntity player) {

        return getSortedAreas(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), player.world);

    }

    public static Area getHighestPriorityArea (int x, int y, int z, World world) {

        List<Area> areas = getFromLocation(x, y, z, world);
        Map<Integer, Area> priorityMap = new HashMap<>();
        for (Area a : areas) {

            priorityMap.put(a.getPriority(), a);

        }
        List<Integer> priorities = new ArrayList<>(priorityMap.keySet());
        Collections.sort(priorities);
        return priorityMap.get(priorities.get(0));

    }

    public static Area getHighestPriorityArea (ServerPlayerEntity player) {

        return getHighestPriorityArea(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), player.world);

    }

    public static Area getLowestPriorityArea (int x, int y, int z, World world) {

        try {

            List<Area> areas = getFromLocation(x, y, z, world);
            Map<Integer, Area> priorityMap = new HashMap<>();
            for (Area a : areas) {

                priorityMap.put(a.getPriority(), a);

            }
            List<Integer> priorities = new ArrayList<>(priorityMap.keySet());
            Collections.sort(priorities);
            return priorityMap.get(priorities.get(priorities.size() - 1));

        } catch (IndexOutOfBoundsException er) {

            return null;

        }

    }

    public static Area getLowestPriorityArea (ServerPlayerEntity player) {

        return getLowestPriorityArea(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), player.world);

    }

    public static List<Area> getFromLocation (int x, int y, int z, World world) {

        List<Area> areas = new ArrayList<>();
        String currentWorld = WorldMap.getWorldName(world);
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

    public static boolean areaHasPlayer (Region region, Area area, UUID uuid) {

        boolean has = false;
        if (playersInArea.containsKey(region.getName())) {

            Map<Area, List<UUID>> players = playersInArea.get(region.getName());
            if (players.containsKey(area)) {

                List<UUID> uuids = players.get(area);
                has = uuids.contains(uuid);

            }

        }

        return has;

    }

    public static boolean areaHasPlayer (Region region, Area area, ServerPlayerEntity player) {

        return areaHasPlayer(region, area, player.getUniqueID());

    }

}

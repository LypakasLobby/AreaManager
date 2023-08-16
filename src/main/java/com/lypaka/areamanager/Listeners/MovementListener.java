package com.lypaka.areamanager.Listeners;

import com.lypaka.areamanager.API.AreaEvents.AreaEnterEvent;
import com.lypaka.areamanager.API.AreaEvents.AreaLeaveEvent;
import com.lypaka.areamanager.API.AreaEvents.AreaPermissionsEvent;
import com.lypaka.areamanager.API.RegionEvents.RegionEnterEvent;
import com.lypaka.areamanager.API.RegionEvents.RegionLeaveEvent;
import com.lypaka.areamanager.API.RegionEvents.RegionPermissionsEvent;
import com.lypaka.areamanager.AreaManager;
import com.lypaka.areamanager.Areas.Area;
import com.lypaka.areamanager.Areas.AreaHandler;
import com.lypaka.areamanager.Areas.AreaPermissions;
import com.lypaka.areamanager.Regions.Region;
import com.lypaka.areamanager.Regions.RegionHandler;
import com.lypaka.areamanager.Regions.RegionPermissions;
import com.lypaka.lypakautils.API.PlayerMovementEvent;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = AreaManager.MOD_ID)
public class MovementListener {

    private static final Map<UUID, Integer> swimCounter = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerLandMovement (PlayerMovementEvent.Land event) {

        ServerPlayerEntity player = event.getPlayer();
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        int z = player.getPosition().getZ();
        World world = player.world;
        Region playerRegion = RegionHandler.getRegionAtPlayer(player);
        List<Area> playerAreas = AreaHandler.getAreasAtPlayer(player);

        RegionHandler.playersInRegion.entrySet().removeIf(entry -> {

            Region region = entry.getKey();
            List<Area> areas = region.getAreas();
            AreaHandler.playersInArea.entrySet().removeIf(area -> {

                if (!areas.contains(area.getKey())) {

                    AreaPermissions permissions = area.getKey().getPermissions();
                    boolean hasPermission = true;
                    for (String p : permissions.getLeavePermissions()) {

                        if (!PermissionHandler.hasPermission(player, p)) {

                            hasPermission = false;
                            break;

                        }

                    }
                    AreaPermissionsEvent permissionsEvent = new AreaPermissionsEvent(player, area.getKey(), permissions);
                    MinecraftForge.EVENT_BUS.post(permissionsEvent);
                    if (permissionsEvent.isCanceled()) hasPermission = true;
                    AreaLeaveEvent leaveEvent = new AreaLeaveEvent(player, area.getKey(), hasPermission);
                    MinecraftForge.EVENT_BUS.post(leaveEvent);
                    if (!leaveEvent.isCanceled()) {

                        if (leaveEvent.playerCanLeave()) {

                            if (!area.getKey().getLeaveMessage().equalsIgnoreCase("")) {

                                STitlePacket title = new STitlePacket(STitlePacket.Type.TITLE,
                                        FancyText.getFormattedText(area.getKey().getLeaveMessage().replace("%plainName%", area.getKey().getPlainName())),
                                        10, 10, 10);
                                player.connection.sendPacket(title);

                            }
                            return true;

                        } else {

                            if (!area.getKey().getPermissions().getLeaveTeleportLocation().equalsIgnoreCase("x,y,z")) {

                                int tpX = Integer.parseInt(area.getKey().getPermissions().getLeaveTeleportLocation().split(",")[0]);
                                int tpY = Integer.parseInt(area.getKey().getPermissions().getLeaveTeleportLocation().split(",")[1]);
                                int tpZ = Integer.parseInt(area.getKey().getPermissions().getLeaveTeleportLocation().split(",")[2]);
                                player.setPositionAndUpdate(tpX, tpY, tpZ);
                                if (!permissions.getLeaveMessage().equals("")) {

                                    player.sendMessage(FancyText.getFormattedText(permissions.getLeaveMessage()), player.getUniqueID());

                                }

                            }
                            return false;

                        }

                    } else {

                        if (!area.getKey().getPermissions().getLeaveTeleportLocation().equalsIgnoreCase("x,y,z")) {

                            int tpX = Integer.parseInt(area.getKey().getPermissions().getLeaveTeleportLocation().split(",")[0]);
                            int tpY = Integer.parseInt(area.getKey().getPermissions().getLeaveTeleportLocation().split(",")[1]);
                            int tpZ = Integer.parseInt(area.getKey().getPermissions().getLeaveTeleportLocation().split(",")[2]);
                            player.setPositionAndUpdate(tpX, tpY, tpZ);
                            if (!permissions.getLeaveMessage().equals("")) {

                                player.sendMessage(FancyText.getFormattedText(permissions.getLeaveMessage()), player.getUniqueID());

                            }

                        }
                        return false;

                    }

                }

                return false;

            });

            for (Area a : areas) {

                List<UUID> players = new ArrayList<>();
                if (AreaHandler.playersInArea.containsKey(a)) {

                    players = AreaHandler.playersInArea.get(a);

                }
                if (!players.contains(player.getUniqueID())) {

                    AreaPermissions permissions = a.getPermissions();
                    boolean hasPermission = true;
                    for (String p : permissions.getEnterPermissions()) {

                        if (!PermissionHandler.hasPermission(player, p)) {

                            hasPermission = false;
                            break;

                        }

                    }
                    AreaPermissionsEvent permissionsEvent = new AreaPermissionsEvent(player, a, permissions);
                    MinecraftForge.EVENT_BUS.post(permissionsEvent);
                    if (permissionsEvent.isCanceled()) hasPermission = true;
                    AreaEnterEvent enterEvent = new AreaEnterEvent(player, a, hasPermission);
                    MinecraftForge.EVENT_BUS.post(enterEvent);
                    if (!enterEvent.isCanceled()) {

                        if (enterEvent.playerCanEnter()) {

                            if (!a.getEnterMessage().equalsIgnoreCase("")) {

                                STitlePacket title = new STitlePacket(STitlePacket.Type.TITLE,
                                        FancyText.getFormattedText(a.getEnterMessage().replace("%plainName%", a.getPlainName())),
                                        10, 10, 10);
                                player.connection.sendPacket(title);

                            }
                            players.add(player.getUniqueID());

                        } else {

                            if (!permissions.getEnterTeleportLocation().equalsIgnoreCase("")) {

                                int tpX = Integer.parseInt(a.getPermissions().getEnterTeleportLocation().split(",")[0]);
                                int tpY = Integer.parseInt(a.getPermissions().getEnterTeleportLocation().split(",")[1]);
                                int tpZ = Integer.parseInt(a.getPermissions().getEnterTeleportLocation().split(",")[2]);
                                player.setPositionAndUpdate(tpX, tpY, tpZ);
                                if (!permissions.getEnterMessage().equals("")) {

                                    player.sendMessage(FancyText.getFormattedText(permissions.getEnterMessage()), player.getUniqueID());

                                }

                            }

                        }

                    } else {

                        if (!permissions.getEnterTeleportLocation().equalsIgnoreCase("")) {

                            int tpX = Integer.parseInt(a.getPermissions().getEnterTeleportLocation().split(",")[0]);
                            int tpY = Integer.parseInt(a.getPermissions().getEnterTeleportLocation().split(",")[1]);
                            int tpZ = Integer.parseInt(a.getPermissions().getEnterTeleportLocation().split(",")[2]);
                            player.setPositionAndUpdate(tpX, tpY, tpZ);
                            if (!permissions.getEnterMessage().equals("")) {

                                player.sendMessage(FancyText.getFormattedText(permissions.getEnterMessage()), player.getUniqueID());

                            }

                        }

                    }

                }

                boolean teleports = true;
                boolean doesNothing = false;
                if (a.killsForSwimming() && !a.teleportsForSwimming()) teleports = false;
                if (!a.killsForSwimming() && !a.teleportsForSwimming()) doesNothing = true;

                if (!doesNothing) {

                    if (player.isInWater()) {

                        if (!teleports) {

                            if (player.getRidingEntity() == null) {

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

                            } else {

                                swimCounter.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));

                            }

                        } else {

                            PlayerLocation location = PlayerDataHandler.playerLocationMap.get(player.getUniqueID());
                            int tpX = location.getLastLandLocation()[0];
                            int tpY = location.getLastLandLocation()[1];
                            int tpZ = location.getLastLandLocation()[2];

                            player.setPositionAndUpdate(tpX, tpY, tpZ);

                        }

                    } else {

                        swimCounter.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));

                    }

                } else {

                    swimCounter.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));

                }

                AreaHandler.playersInArea.put(a, players);

            }
            if (!region.getName().equalsIgnoreCase(playerRegion.getName())) {

                RegionPermissions regionPermissions = region.getPermissions();
                boolean hasPermission = true;
                for (String p : regionPermissions.getLeavePermissions()) {

                    if (!PermissionHandler.hasPermission(player, p)) {

                        hasPermission = false;
                        break;

                    }

                }
                RegionPermissionsEvent permissionsEvent = new RegionPermissionsEvent(player, region, regionPermissions);
                MinecraftForge.EVENT_BUS.post(permissionsEvent);
                if (permissionsEvent.isCanceled()) hasPermission = true;
                RegionLeaveEvent leaveEvent = new RegionLeaveEvent(player, region, hasPermission);
                MinecraftForge.EVENT_BUS.post(leaveEvent);
                if (!leaveEvent.isCanceled()) {

                    if (leaveEvent.playerCanLeave()) {

                        return true;

                    } else {

                        if (!region.getPermissions().getLeaveTeleportLocation().equalsIgnoreCase("x,y,z")) {

                            int tpX = Integer.parseInt(region.getPermissions().getLeaveTeleportLocation().split(",")[0]);
                            int tpY = Integer.parseInt(region.getPermissions().getLeaveTeleportLocation().split(",")[1]);
                            int tpZ = Integer.parseInt(region.getPermissions().getLeaveTeleportLocation().split(",")[2]);
                            player.setPositionAndUpdate(tpX, tpY, tpZ);

                        }
                        return false;

                    }

                } else {

                    if (!region.getPermissions().getLeaveTeleportLocation().equalsIgnoreCase("x,y,z")) {

                        int tpX = Integer.parseInt(region.getPermissions().getLeaveTeleportLocation().split(",")[0]);
                        int tpY = Integer.parseInt(region.getPermissions().getLeaveTeleportLocation().split(",")[1]);
                        int tpZ = Integer.parseInt(region.getPermissions().getLeaveTeleportLocation().split(",")[2]);
                        player.setPositionAndUpdate(tpX, tpY, tpZ);

                    }
                    return false;

                }

            }

            return false;

        });
        for (Map.Entry<Region, List<UUID>> regions : RegionHandler.playersInRegion.entrySet()) {

            Region region = regions.getKey();
            List<UUID> players = regions.getValue();

            if (!players.contains(player.getUniqueID())) {

                RegionPermissions permissions = region.getPermissions();
                boolean hasPermission = true;
                for (String p : permissions.getEnterPermissions()) {

                    if (!PermissionHandler.hasPermission(player, p)) {

                        hasPermission = false;
                        break;

                    }

                }
                RegionPermissionsEvent permissionsEvent = new RegionPermissionsEvent(player, region, permissions);
                MinecraftForge.EVENT_BUS.post(permissionsEvent);
                if (permissionsEvent.isCanceled()) hasPermission = true;
                RegionEnterEvent enterEvent = new RegionEnterEvent(player, region, hasPermission);
                MinecraftForge.EVENT_BUS.post(enterEvent);
                if (!enterEvent.isCanceled()) {

                    if (enterEvent.playerCanEnter()) {

                        players.add(player.getUniqueID());

                    } else {

                        if (!permissions.getEnterTeleportLocation().equalsIgnoreCase("")) {

                            int tpX = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[0]);
                            int tpY = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[1]);
                            int tpZ = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[2]);
                            player.setPositionAndUpdate(tpX, tpY, tpZ);

                        }

                    }

                } else {

                    if (!permissions.getEnterTeleportLocation().equalsIgnoreCase("")) {

                        int tpX = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[0]);
                        int tpY = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[1]);
                        int tpZ = Integer.parseInt(permissions.getEnterTeleportLocation().split(",")[2]);
                        player.setPositionAndUpdate(tpX, tpY, tpZ);

                    }

                }

            }

        }

    }

}

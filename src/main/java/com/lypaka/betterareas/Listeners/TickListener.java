package com.lypaka.betterareas.Listeners;

import com.lypaka.betterareas.API.AreaEnterEvent;
import com.lypaka.betterareas.API.AreaLeaveEvent;
import com.lypaka.betterareas.Areas.Area;
import com.lypaka.betterareas.Areas.AreaHandler;
import com.lypaka.betterareas.Areas.AreaPermissions;
import com.lypaka.betterareas.Spawners.FishSpawner;
import com.lypaka.betterareas.Spawners.HeadbuttSpawner;
import com.lypaka.betterareas.Spawners.NaturalSpawner;
import com.lypaka.betterareas.Spawners.RockSmashSpawner;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.JoinListener;
import com.lypaka.lypakautils.PermissionHandler;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.*;

/**
 * Let it be known that I hate....HATE...counting ticks
 */
public class TickListener {

    private static int count = -1;
    private static final Map<UUID, Integer> swimCounter = new HashMap<>();

    @SubscribeEvent
    public void onTick (TickEvent.ServerTickEvent event) {

        if (event.side == LogicalSide.SERVER) {

            count++;
            if (count >= 20) {

                count = -1;
                for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                    ServerPlayerEntity player = entry.getValue();
                    int x = player.getPosition().getX();
                    int y = player.getPosition().getY();
                    int z = player.getPosition().getZ();
                    World world = player.world;
                    List<Area> areas = AreaHandler.getFromLocation(x, y, z, world);
                    AreaHandler.playersInArea.entrySet().removeIf(area -> {

                        if (!areas.contains(area.getKey())) {

                            FishSpawner.pokemonSpawnedMap.entrySet().removeIf(a -> {

                                if (a.getKey() == area.getKey()) {

                                    if (area.getKey().getFishSpawnerSettings().doesClearSpawns()) {

                                        Map<UUID, List<PixelmonEntity>> spawns = FishSpawner.pokemonSpawnedMap.get(a.getKey());
                                        if (spawns.containsKey(player.getUniqueID())) {

                                            List<PixelmonEntity> pokemon = spawns.get(player.getUniqueID());
                                            for (PixelmonEntity entity : pokemon) {

                                                if (entity.battleController == null) {

                                                    entity.remove();

                                                }

                                            }

                                        }

                                        return true;

                                    }

                                }

                                return false;

                            });
                            HeadbuttSpawner.pokemonSpawnedMap.entrySet().removeIf(a -> {

                                if (a.getKey() == area.getKey()) {

                                    if (area.getKey().getHeadbuttSpawnerSettings().doesClearSpawns()) {

                                        Map<UUID, List<PixelmonEntity>> spawns = HeadbuttSpawner.pokemonSpawnedMap.get(a.getKey());
                                        if (spawns.containsKey(player.getUniqueID())) {

                                            List<PixelmonEntity> pokemon = spawns.get(player.getUniqueID());
                                            for (PixelmonEntity entity : pokemon) {

                                                if (entity.battleController == null) {

                                                    entity.remove();

                                                }

                                            }

                                        }

                                        return true;

                                    }

                                }

                                return false;

                            });
                            NaturalSpawner.pokemonSpawnedMap.entrySet().removeIf(a -> {

                                if (a.getKey() == area.getKey()) {

                                    if (area.getKey().getNaturalSpawnerSettings().doesClearSpawns()) {

                                        Map<UUID, List<PixelmonEntity>> spawns = NaturalSpawner.pokemonSpawnedMap.get(a.getKey());
                                        if (spawns.containsKey(player.getUniqueID())) {

                                            List<PixelmonEntity> pokemon = spawns.get(player.getUniqueID());
                                            for (PixelmonEntity entity : pokemon) {

                                                if (entity.battleController == null) {

                                                    entity.remove();

                                                }

                                            }

                                        }

                                        return true;

                                    }

                                }

                                return false;

                            });
                            RockSmashSpawner.pokemonSpawnedMap.entrySet().removeIf(a -> {

                                if (a.getKey() == area.getKey()) {

                                    if (area.getKey().getRockSmashSpawnerSettings().doesClearSpawns()) {

                                        Map<UUID, List<PixelmonEntity>> spawns = RockSmashSpawner.pokemonSpawnedMap.get(a.getKey());
                                        if (spawns.containsKey(player.getUniqueID())) {

                                            List<PixelmonEntity> pokemon = spawns.get(player.getUniqueID());
                                            for (PixelmonEntity entity : pokemon) {

                                                if (entity.battleController == null) {

                                                    entity.remove();

                                                }

                                            }

                                        }

                                        return true;

                                    }

                                }

                                return false;

                            });

                            AreaPermissions permissions = area.getKey().getPermissions();
                            boolean hasPermission = true;
                            for (String p : permissions.getLeavePermissions()) {

                                if (!PermissionHandler.hasPermission(player, p)) {

                                    hasPermission = false;
                                    break;

                                }

                            }
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

                        if (a.doesPreventSwimming()) {

                            if (player.isInWater()) {

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

                                swimCounter.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));

                            }

                        } else {

                            swimCounter.entrySet().removeIf(e -> e.getKey().toString().equalsIgnoreCase(player.getUniqueID().toString()));

                        }

                        AreaHandler.playersInArea.put(a, players);

                    }

                }

            }

        }

    }

}

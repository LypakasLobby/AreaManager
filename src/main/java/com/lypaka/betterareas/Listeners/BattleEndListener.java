package com.lypaka.betterareas.Listeners;

import com.lypaka.betterareas.Areas.Area;
import com.lypaka.betterareas.Areas.AreaHandler;
import com.lypaka.betterareas.Spawners.FishSpawner;
import com.lypaka.betterareas.Spawners.HeadbuttSpawner;
import com.lypaka.betterareas.Spawners.NaturalSpawner;
import com.lypaka.betterareas.Spawners.RockSmashSpawner;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class BattleEndListener {

    @SubscribeEvent
    public void onBattleEnd (BattleEndEvent event) {

        WildPixelmonParticipant wpp;
        PlayerParticipant pp;
        BattleController bcb = event.getBattleController();

        if (bcb.participants.get(0) instanceof WildPixelmonParticipant && bcb.participants.get(1) instanceof PlayerParticipant) {

            wpp = (WildPixelmonParticipant) bcb.participants.get(0);
            pp = (PlayerParticipant) bcb.participants.get(1);

        } else if (bcb.participants.get(0) instanceof PlayerParticipant && bcb.participants.get(1) instanceof WildPixelmonParticipant) {

            wpp = (WildPixelmonParticipant) bcb.participants.get(1);
            pp = (PlayerParticipant) bcb.participants.get(0);

        } else {

            return;

        }

        ServerPlayerEntity player = pp.player;
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        int z = player.getPosition().getZ();
        PixelmonEntity pixelmon = wpp.controlledPokemon.get(0).entity;
        List<Area> areas = AreaHandler.getFromLocation(x, y, z, player.world);
        if (areas.size() == 0) return;

        Area currentArea = AreaHandler.getHighestPriorityArea(x, y, z, player.world);
        String spawner = null;
        if (FishSpawner.spawnedPokemonUUIDs.contains(pixelmon.getUniqueID())) {

            spawner = "Fish";

        } else if (HeadbuttSpawner.spawnedPokemonUUIDs.contains(pixelmon.getUniqueID())) {

            spawner = "Headbutt";

        } else if (NaturalSpawner.spawnedPokemonUUIDs.contains(pixelmon.getUniqueID())) {

            spawner = "Natural";

        } else if (RockSmashSpawner.spawnedPokemonUUIDs.contains(pixelmon.getUniqueID())) {

            spawner = "RockSmash";

        }
        if (spawner == null) return;
        switch (spawner) {

            case "Fish":
                if (currentArea.getFishSpawnerSettings().doesDespawnAfterBattle()) {

                    FishSpawner.spawnedPokemonUUIDs.removeIf(entry -> {

                        if (entry.toString().equalsIgnoreCase(pixelmon.getUniqueID().toString())) {

                            if (pixelmon.battleController == null) {

                                pixelmon.remove();

                            }
                            return true;

                        }

                        return false;

                    });

                }
                break;

            case "Headbutt":
                if (currentArea.getHeadbuttSpawnerSettings().doesDespawnAfterBattle()) {

                    HeadbuttSpawner.spawnedPokemonUUIDs.removeIf(entry -> {

                        if (entry.toString().equalsIgnoreCase(pixelmon.getUniqueID().toString())) {

                            if (pixelmon.battleController == null) {

                                pixelmon.remove();

                            }
                            return true;

                        }

                        return false;

                    });

                }
                break;

            case "Natural":
                if (currentArea.getNaturalSpawnerSettings().doesDespawnAfterBattle()) {

                    NaturalSpawner.spawnedPokemonUUIDs.removeIf(entry -> {

                        if (entry.toString().equalsIgnoreCase(pixelmon.getUniqueID().toString())) {

                            if (pixelmon.battleController == null) {

                                pixelmon.remove();

                            }
                            return true;

                        }

                        return false;

                    });

                }
                break;

            case "RockSmash":
                if (currentArea.getRockSmashSpawnerSettings().doesDespawnAfterBattle()) {

                    RockSmashSpawner.spawnedPokemonUUIDs.removeIf(entry -> {

                        if (entry.toString().equalsIgnoreCase(pixelmon.getUniqueID().toString())) {

                            if (pixelmon.battleController == null) {

                                pixelmon.remove();

                            }
                            return true;

                        }

                        return false;

                    });

                }
                break;

        }

    }

}

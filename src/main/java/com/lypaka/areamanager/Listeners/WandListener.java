package com.lypaka.areamanager.Listeners;

import com.lypaka.areamanager.Wand.WandHandler;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class WandListener {

    @SubscribeEvent
    public void onRightClick (PlayerInteractEvent.RightClickBlock event) {

        if (event.getHand() == Hand.OFF_HAND) return;
        if (event.getSide() == LogicalSide.CLIENT) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        if (!player.isCreative()) return;
        ItemStack item = player.getHeldItem(Hand.MAIN_HAND);
        if (item.getOrCreateTag().contains("AreaManagerWand")) {

            event.setCanceled(true);
            int x = event.getPos().getX();
            int y = event.getPos().getY();
            int z = event.getPos().getZ();
            String world = WorldMap.getWorldName(player);
            WandHandler.addPosition(player, x, y, z, world, 1);

        }

    }

    @SubscribeEvent
    public void onLeftClick (PlayerInteractEvent.LeftClickBlock event) {

        if (event.getHand() == Hand.OFF_HAND) return;
        if (event.getSide() == LogicalSide.CLIENT) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        if (!player.isCreative()) return;
        ItemStack item = player.getHeldItem(Hand.MAIN_HAND);
        if (item.getOrCreateTag().contains("AreaManagerWand")) {

            event.setCanceled(true);
            if (player.isSneaking()) {

                WandHandler.clearPositions(player);

            } else {

                int x = event.getPos().getX();
                int y = event.getPos().getY();
                int z = event.getPos().getZ();
                String world = WorldMap.getWorldName(player);
                WandHandler.addPosition(player, x, y, z, world, 0);

            }

        }

    }

    @SubscribeEvent
    public void onLeftClickEmpty (PlayerInteractEvent.LeftClickEmpty event) {

        if (event.getHand() == Hand.OFF_HAND) return;
        if (event.getSide() == LogicalSide.CLIENT) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        if (!player.isCreative()) return;
        ItemStack item = player.getHeldItem(Hand.MAIN_HAND);
        if (item.getOrCreateTag().contains("AreaManagerWand")) {

            event.setCanceled(true);
            WandHandler.clearPositions(player);

        }

    }

}

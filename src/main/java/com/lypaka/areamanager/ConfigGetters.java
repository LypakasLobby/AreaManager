package com.lypaka.areamanager;

import com.google.common.reflect.TypeToken;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import net.minecraft.item.ItemStack;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

public class ConfigGetters {

    public static List<String> regionNames;
    public static String wandDisplayName;
    public static String wandID;

    public static void load() throws ObjectMappingException {

        regionNames = AreaManager.configManager.getConfigNode(0, "Regions").getList(TypeToken.of(String.class));
        if (AreaManager.configManager.getConfigNode(0, "Wand", "Display-Name").isVirtual()) {

            wandDisplayName = "&4Lazy Wand";
            wandID = "minecraft:golden_axe";
            AreaManager.configManager.getConfigNode(0, "Wand", "Display-Name").setValue(wandDisplayName);
            AreaManager.configManager.getConfigNode(0, "Wand", "ID").setValue(wandID);
            AreaManager.configManager.save();

        } else {

            wandDisplayName = AreaManager.configManager.getConfigNode(0, "Wand", "Display-Name").getString();
            wandID = AreaManager.configManager.getConfigNode(0, "Wand", "ID").getString();

        }

    }

    public static ItemStack getWand() {

        ItemStack wand = ItemStackBuilder.buildFromStringID(wandID);
        wand.setDisplayName(FancyText.getFormattedText(wandDisplayName));
        wand.getOrCreateTag().putBoolean("AreaManagerWand", true);
        return wand;

    }

}

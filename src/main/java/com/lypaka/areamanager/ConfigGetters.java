package com.lypaka.areamanager;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

public class ConfigGetters {

    public static List<String> regionNames;

    public static void load() throws ObjectMappingException {

        regionNames = AreaManager.configManager.getConfigNode(0, "Regions").getList(TypeToken.of(String.class));

    }

}

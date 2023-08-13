package com.lypaka.areamanager;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static List<String> areaNames;

    public static void load() throws ObjectMappingException {

        areaNames = AreaManager.configManager.getConfigNode(0, "Area-Names").getList(TypeToken.of(String.class));

    }

}

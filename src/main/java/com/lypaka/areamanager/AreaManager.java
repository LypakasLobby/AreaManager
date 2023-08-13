package com.lypaka.areamanager;

import com.lypaka.areamanager.Areas.AreaHandler;
import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("areamanager")
public class AreaManager {

    public static final String MOD_ID = "areamanager";
    public static final String MOD_NAME = "AreaManager";
    public static final Logger logger = LogManager.getLogger("AreaManager");
    public static BasicConfigManager configManager;
    public static Map<String, BasicConfigManager> areaConfigManager = new HashMap<>();

    public AreaManager() throws IOException, ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/areamanager"));
        String[] files = new String[]{"areamanager.conf"};
        configManager = new BasicConfigManager(files, dir, AreaManager.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigGetters.load();
        AreaHandler.loadAreas();

    }

}

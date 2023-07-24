package com.lypaka.betterareas;

import com.lypaka.betterareas.Areas.AreaHandler;
import com.lypaka.betterareas.Utils.HeldItemUtils;
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
@Mod("betterareas")
public class BetterAreas {

    public static final String MOD_ID = "betterareas";
    public static final String MOD_NAME = "BetterAreas";
    public static final Logger logger = LogManager.getLogger("BetterAreas");
    public static BasicConfigManager configManager;
    public static Map<String, BasicConfigManager> areaConfigManager = new HashMap<>();

    public BetterAreas() throws IOException, ObjectMappingException {

        Path dir = ConfigUtils.checkDir(Paths.get("./config/betterareas"));
        String[] files = new String[]{"betterareas.conf", "heldItems.conf", "guiSettings.conf"};
        configManager = new BasicConfigManager(files, dir, BetterAreas.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        ConfigGetters.load();
        AreaHandler.loadAreas();
        HeldItemUtils.load();

    }

}

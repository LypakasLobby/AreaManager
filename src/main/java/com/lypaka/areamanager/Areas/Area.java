package com.lypaka.areamanager.Areas;

import com.lypaka.areamanager.Areas.SpawnerSettings.FishSpawnerSettings;
import com.lypaka.areamanager.Areas.SpawnerSettings.HeadbuttSpawnerSettings;
import com.lypaka.areamanager.Areas.SpawnerSettings.NaturalSpawnerSettings;
import com.lypaka.areamanager.Areas.SpawnerSettings.RockSmashSpawnerSettings;

public class Area {

    private final String name;
    private final String displayName;
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private final int minX;
    private final int minY;
    private final int minZ;
    private final String worldName;
    private final String enterMessage;
    private final String leaveMessage;
    private final String plainName;
    private final boolean cancelConcussions;
    private final boolean preventSwimming;
    private final AreaPermissions permissions;
    private final FishSpawnerSettings fishSpawnerSettings;
    private final HeadbuttSpawnerSettings headbuttSpawnerSettings;
    private final NaturalSpawnerSettings naturalSpawnerSettings;
    private final RockSmashSpawnerSettings rockSmashSpawnerSettings;
    private final int priority;
    private final int radius;
    private final int underground;

    public Area (String name, String displayName, int maxX, int maxY, int maxZ, int minX, int minY, int minZ, String worldName, String enterMessage, String leaveMessage,
                 String plainName, boolean cancelConcussions, boolean preventSwimming, AreaPermissions permissions, FishSpawnerSettings fishSpawnerSettings,
                 HeadbuttSpawnerSettings headbuttSpawnerSettings, NaturalSpawnerSettings naturalSpawnerSettings, RockSmashSpawnerSettings rockSmashSpawnerSettings, int priority,
                 int radius, int underground) {

        this.name = name;
        this.displayName = displayName;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.worldName = worldName;
        this.enterMessage = enterMessage;
        this.leaveMessage = leaveMessage;
        this.plainName = plainName;
        this.cancelConcussions = cancelConcussions;
        this.preventSwimming = preventSwimming;
        this.permissions = permissions;
        this.fishSpawnerSettings = fishSpawnerSettings;
        this.headbuttSpawnerSettings = headbuttSpawnerSettings;
        this.naturalSpawnerSettings = naturalSpawnerSettings;
        this.rockSmashSpawnerSettings = rockSmashSpawnerSettings;
        this.priority = priority;
        this.radius = radius;
        this.underground = underground;

    }

    public void create() {

        AreaHandler.areaMap.put(this.name, this);

    }

    public String getName() {

        return this.name;

    }

    public String getDisplayName() {

        return this.displayName;

    }

    public int getMaxX() {

        return this.maxX;

    }

    public int getMaxY() {

        return this.maxY;

    }

    public int getMaxZ() {

        return this.maxZ;

    }

    public int getMinX() {

        return this.minX;

    }

    public int getMinY() {

        return this.minY;

    }

    public int getMinZ() {

        return this.minZ;

    }

    public String getWorldName() {

        return this.worldName;

    }

    public String getEnterMessage() {

        return this.enterMessage;

    }

    public String getLeaveMessage() {

        return this.leaveMessage;

    }

    public String getPlainName() {

        return this.plainName;

    }

    public boolean cancelsConcussions() {

        return this.cancelConcussions;

    }

    public boolean doesPreventSwimming() {

        return this.preventSwimming;

    }

    public AreaPermissions getPermissions() {

        return this.permissions;

    }

    public FishSpawnerSettings getFishSpawnerSettings() {

        return this.fishSpawnerSettings;

    }

    public HeadbuttSpawnerSettings getHeadbuttSpawnerSettings() {

        return this.headbuttSpawnerSettings;

    }

    public NaturalSpawnerSettings getNaturalSpawnerSettings() {

        return this.naturalSpawnerSettings;

    }

    public RockSmashSpawnerSettings getRockSmashSpawnerSettings() {

        return this.rockSmashSpawnerSettings;

    }

    public int getPriority() {

        return this.priority;

    }

    public int getRadius() {

        return this.radius;

    }

    public int getUnderground() {

        return this.underground;

    }

}

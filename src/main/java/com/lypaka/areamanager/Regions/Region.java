package com.lypaka.areamanager.Regions;

import com.lypaka.areamanager.Areas.Area;

import java.util.List;

public class Region {

    private final String name;
    private final String displayName;
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private final int minX;
    private final int minY;
    private final int minZ;
    private final String worldName;
    private final RegionPermissions permissions;
    private final List<Area> areas;

    public Region (String name, String displayName, int maxX, int maxY, int maxZ, int minX, int minY, int minZ, String worldName, RegionPermissions permissions, List<Area> areas) {

        this.name = name;
        this.displayName = displayName;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.worldName = worldName;
        this.permissions = permissions;
        this.areas = areas;

    }

    public void create() {

        RegionHandler.regionMap.put(this.name, this);

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

    public RegionPermissions getPermissions() {

        return this.permissions;

    }

    public List<Area> getAreas() {

        return this.areas;

    }

}

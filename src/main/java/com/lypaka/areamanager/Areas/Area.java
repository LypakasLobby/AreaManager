package com.lypaka.areamanager.Areas;

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
    private final boolean killForSwimming;
    private final boolean teleportForSwimming;
    private final AreaPermissions permissions;
    private final int priority;
    private final int radius;
    private final int underground;

    public Area (String name, String displayName, int maxX, int maxY, int maxZ, int minX, int minY, int minZ, String worldName, String enterMessage, String leaveMessage,
                 String plainName, boolean killForSwimming, boolean teleportForSwimming, AreaPermissions permissions, int priority, int radius, int underground) {

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
        this.killForSwimming = killForSwimming;
        this.teleportForSwimming = teleportForSwimming;
        this.permissions = permissions;
        this.priority = priority;
        this.radius = radius;
        this.underground = underground;

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

    public boolean killsForSwimming() {

        return this.killForSwimming;

    }

    public boolean teleportsForSwimming() {

        return this.teleportForSwimming;

    }

    public AreaPermissions getPermissions() {

        return this.permissions;

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

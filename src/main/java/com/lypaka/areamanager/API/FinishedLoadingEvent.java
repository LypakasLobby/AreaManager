package com.lypaka.areamanager.API;

import net.minecraftforge.eventbus.api.Event;

/**
 * Called when AreaManager finishes loading all the areas
 * To be used by other mods that depend on AreaManager, so that they don't try to load their shit until AFTER AreaManager finishes loading its shit
 */
public class FinishedLoadingEvent extends Event {

    public FinishedLoadingEvent() {}

}

package com.lypaka.areamanager.API;

import net.minecraftforge.eventbus.api.Event;

/**
 * Called when AreaManager finishes loading all the areas
 * To be used by other mods that depend on AreaManager, so that they don't try to load their shit until AFTER AreaManager finishes loading its shit
 * If you make a mod that depends on AreaManager and loads shit into storage that would crash if AreaManager's shit isn't done loading yet, then have your mod load its shit on this event listener
 */
public class FinishedLoadingEvent extends Event {

    public FinishedLoadingEvent() {}

}

package com.lypaka.betterareas.Areas.Spawns;

import com.lypaka.betterareas.Areas.Area;

import java.util.List;

public class AreaSpawns {

    private final Area area;
    private final List<FishSpawn> fishSpawns;
    private final List<HeadbuttSpawn> headbuttSpawns;
    private final List<NaturalSpawn> naturalSpawns;
    private final List<RockSmashSpawn> rockSmashSpawns;

    public AreaSpawns (Area area, List<FishSpawn> fish, List<HeadbuttSpawn> headbutt, List<NaturalSpawn> natural, List<RockSmashSpawn> rockSmash) {

        this.area = area;
        this.fishSpawns = fish;
        this.headbuttSpawns = headbutt;
        this.naturalSpawns = natural;
        this.rockSmashSpawns = rockSmash;

    }

    public Area getArea() {

        return this.area;

    }

    public List<FishSpawn> getFishSpawns() {

        return this.fishSpawns;

    }

    public List<HeadbuttSpawn> getHeadbuttSpawns() {

        return this.headbuttSpawns;

    }

    public List<NaturalSpawn> getNaturalSpawns() {

        return this.naturalSpawns;

    }

    public List<RockSmashSpawn> getRockSmashSpawns() {

        return this.rockSmashSpawns;

    }

}

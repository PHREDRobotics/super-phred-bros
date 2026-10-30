package com.superphred.types;

public enum Tile {
    AIR(false),
    BORDER(true),
    STONE(true),
    DIRT(true),
    CLOUD(true),
    PLAYER_SPAWN(false),
    GEAR_SPAWN(false),
    DOUGHNUT_SPAWN(false);
    

    public final boolean isSolid;

    Tile(boolean isSolid) {
        this.isSolid = isSolid;
    }
}

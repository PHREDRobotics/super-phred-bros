package com.superphred.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.superphred.Constants;

public class Gear extends Entity {
    public Gear(Vector2 startPos) {
        super(startPos.x, startPos.y, Constants.TILE_SIZE, Constants.TILE_SIZE, new Texture("textures/entities/gear.png"), false);
    }
}

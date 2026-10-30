package com.superphred.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
    public Player(Vector2 startPos) {
        super(startPos.x, startPos.y, 20, 30, new Texture("textures/entities/player.png"), true);
    }
    
    public Player() {
        super(0, 0, 20, 30, new Texture("textures/entities/player.png"), true);
    }
    
    public void move(float velocity) {
        this.velocity.x = velocity;
    }
    
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    
    public void jump(float jumpVelocity) {
        if (velocity.y == 0) {
            this.velocity.y = jumpVelocity;
        }
    }
}

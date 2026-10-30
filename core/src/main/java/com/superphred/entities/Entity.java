package com.superphred.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.superphred.Constants;

public abstract class Entity {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Rectangle bounds;
    protected boolean hasGravity;
    protected TextureRegion texture;
    
    public Entity(float x, float y, float width, float height, Texture texture, boolean hasGravity) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2();
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = new TextureRegion(texture);
        this.hasGravity = hasGravity;
    }

    public void updateGravity(float deltaTime) {
        if (hasGravity) {
            velocity.y -= Constants.GRAVITY * deltaTime;
        }
    }
    
    public void updateBounds() {
        bounds.setPosition(new Vector2(position.x * Constants.TILE_SIZE, position.y * Constants.TILE_SIZE));
    }
    
    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
    
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }
    
    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public void render(Batch batch) {
        batch.draw(
            texture,
            position.x * Constants.TILE_SIZE,
            position.y * Constants.TILE_SIZE,
            Constants.TILE_SIZE,
            Constants.TILE_SIZE
        );
    }
    
    public void dispose() {}
}

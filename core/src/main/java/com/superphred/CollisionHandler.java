package com.superphred;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.superphred.entities.Entity;
import com.superphred.entities.Player;
import com.superphred.level.Level;

public class CollisionHandler {

    public void update(ArrayList<Entity> entities, Level level, float delta) {
        for (Entity entity : entities) {
            Rectangle testBounds = new Rectangle(
                entity.getBounds()
            );
            
            entity.updateGravity(delta);

            // Test X movement
            testBounds.x += entity.getVelocity().x * delta * Constants.TILE_SIZE;

            if (!collidesTile(testBounds, level)) {
                entity.getPosition().x += entity.getVelocity().x * delta;
            } else {
                entity.setVelocity(0, entity.getVelocity().y);
            }

            entity.updateBounds();

            // Test Y movement
            testBounds.set(entity.getBounds());
            testBounds.y += entity.getVelocity().y * delta * Constants.TILE_SIZE;

            if (!collidesTile(testBounds, level)) {
                entity.getPosition().y += entity.getVelocity().y * delta;
            } else {
                entity.setVelocity(entity.getVelocity().x, 0);
            }

            entity.updateBounds();
        }
    }
    
    public void update(Entity entity, Level level, float delta) {
        ArrayList<Entity> list = new ArrayList<Entity>();
        list.add(entity);

        update(list, level, delta);
    }

    private boolean collidesTile(Rectangle bounds, Level level) {
        for (int x = 0; x < level.getWidth(); x++) {
            for (int y = 0; y < level.getHeight(); y++) {
                if (!level.getTile(x, y).isSolid) {
                    continue;
                }

                Rectangle tileBounds = new Rectangle(
                    x * Constants.TILE_SIZE,
                    y * Constants.TILE_SIZE,
                    Constants.TILE_SIZE,
                    Constants.TILE_SIZE
                );

                if (bounds.overlaps(tileBounds)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    public ArrayList<Entity> doEntityCollisions(Player player, ArrayList<Entity> entities, Level level) {
        Rectangle playerBounds = player.getBounds();
        ArrayList<Entity> collidedEntities = new ArrayList<Entity>(); 

        for (Entity entity : entities) {
            if (entity.getBounds().overlaps(playerBounds)) {
                collidedEntities.add(entity);
            }
        }
        
        return collidedEntities;
    }
}
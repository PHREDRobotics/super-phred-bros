package com.superphred.level;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.superphred.Constants;
import com.superphred.entities.Doughnut;
import com.superphred.entities.Entity;
import com.superphred.entities.Gear;
import com.superphred.types.Tile;

public class Level {
    private String name;
    private int width;
    private int height;
    
    private Texture stoneTexture = new Texture("textures/tiles/stone.png");
    private Texture dirtTexture = new Texture("textures/tiles/dirt.png");
    private Texture grassTexture = new Texture("textures/tiles/grass.png");
    private Texture cloudTexture = new Texture("textures/tiles/cloud.png");

    private Tile[][] tiles;
    
    public Level(String name, Tile[][] levelTiles) {
        this.name = name;
        this.height = levelTiles.length;
        this.width = levelTiles[0].length;
        this.tiles = levelTiles;
    }
    
    public void render(Batch batch) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (tiles[y][x]) {
                    case AIR -> {}
                    case STONE -> {
                        batch.draw(stoneTexture, x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);
                    }
                    case DIRT -> {
                        batch.draw(dirtTexture, x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);

                    }
                    case CLOUD -> {
                        batch.draw(cloudTexture, x * Constants.TILE_SIZE, y* Constants.TILE_SIZE);
                    }
                    case GRASS ->{
                        batch.draw(grassTexture, x * Constants.TILE_SIZE, y * Constants.TILE_SIZE);
                    }
                    default -> {}
                }
            }
        }
    }
    
    public Vector2 getPlayerSpawn() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (tiles[y][x] == Tile.PLAYER_SPAWN) {
                    return new Vector2(x, y);
                }
            }
        }
        throw new IllegalStateException("No spawn point found in level: " + name);
    }
    
    public ArrayList<Entity> getEntities() {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (tiles[y][x]) {
                    case GEAR_SPAWN -> {
                        entities.add(new Gear(new Vector2(x, y)));
                    }
                    case DOUGHNUT_SPAWN -> {
                        entities.add(new Doughnut(new Vector2(x, y)));
                    }
                    default -> {}
                }
            }
        }
        
        return entities;
    }
    
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.AIR; // Out of bounds, treat as air
        }
        return tiles[y][x];
    }
    
    public String getName() {
        return name;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void dispose() {
        
    }
}

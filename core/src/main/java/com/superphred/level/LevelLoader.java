package com.superphred.level;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.superphred.types.Tile;

public class LevelLoader {
    public static Optional<Level> loadNextLevel(int currentLevel) {
        FileHandle levelOrder = Gdx.files.internal("level_order.txt");
        
        String[] levels = levelOrder.readString().split("\\R");

        if (currentLevel < 0 || currentLevel >= levels.length) {
            return Optional.empty();
        }

        return Optional.of(loadLevel("levels/" + levels[currentLevel]));
    }

    public static Level loadLevel(String levelName) {
        FileHandle file = Gdx.files.internal(levelName);

        String[] lines = file.readString().split("\\R");
        
        Tile[][] tiles = new Tile[lines.length][];

        for (int y = 0; y < lines.length; y++) {
            int row = lines.length - 1 - y;
            tiles[row] = new Tile[lines[y].length()];
            for (int x = 0; x < lines[y].length(); x++) {
                tiles[row][x] = switch (lines[y].charAt(x)) {
                    case '.' -> Tile.AIR;
                    case '|' -> Tile.BORDER;
                    case '#' -> Tile.STONE;
                    case '%' -> Tile.DIRT;
                    case 'c' -> Tile.CLOUD;
                    case 'G' -> Tile.GRASS;
                    case 'p' -> Tile.PLAYER_SPAWN;
                    case 'g' -> Tile.GEAR_SPAWN;
                    case 'd' -> Tile.DOUGHNUT_SPAWN;
                    default -> throw new IllegalArgumentException(
                        "Unknown tile: " + lines[y].charAt(x)
                    );
                };
                
                System.out.println("Loaded tile: " + lines[y].charAt(x) + " at position: (" + x + ", " + y + ")");
            }
        }
        return new Level(levelName, tiles);
    }
}


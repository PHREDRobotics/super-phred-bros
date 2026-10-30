package com.superphred;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.superphred.entities.Doughnut;
import com.superphred.entities.Entity;
import com.superphred.entities.Player;
import com.superphred.level.Level;
import com.superphred.level.LevelLoader;

public class GameScreen implements Screen {
    private SuperPhred game;
    private Level level;
    private Player player;
    private ArrayList<Entity> entities;
    private CollisionHandler collisionHandler;
    private OrthographicCamera camera;
    private Stage hudStage;
    private BitmapFont hudFont;
    private Label deathLabel;
    private int deathCount = 0;
    
    public GameScreen(final SuperPhred game) {
        this.game = game;
        player = new Player();
        collisionHandler = new CollisionHandler();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 16 * Constants.TILE_SIZE, 9 * Constants.TILE_SIZE);
        hudStage = new Stage(new ScreenViewport());
        hudFont = new BitmapFont();
        hudFont.getData().setScale(2f);

        Label.LabelStyle hudStyle = new Label.LabelStyle(
            hudFont,
            Color.WHITE
        );
        deathLabel = new Label("Safety Violations: 0", hudStyle);

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        table.add(deathLabel).pad(10);

        hudStage.addActor(table);

        Gdx.input.setInputProcessor(hudStage);
    }

    @Override 
    public void show() {
        if (LevelLoader.loadNextLevel(game.currentLevel).isPresent()) {
            this.level = LevelLoader.loadNextLevel(game.currentLevel).get();
        } else {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
        
        player.setPosition(level.getPlayerSpawn());
        entities = level.getEntities();
    }
    
    @Override
    public void render(float delta) {
        

        input();
        logic(delta);
        draw(delta);
    }
    

    public void input() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.move(-8f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.move(8f);
        } else {
            player.move(0);
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.jump(13f);
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            reset();
        }
    }
    
    public void logic(float delta) {
        collisionHandler.update(entities, level, delta);
        collisionHandler.update(player, level, delta);
        
        for (Entity entity : collisionHandler.doEntityCollisions(player, entities, level)) {
            if (entity instanceof Doughnut) {
                win();
            }
            die();
        }
    }
    
    public void draw(float delta) {
        Gdx.gl.glClearColor(0, 0, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        camera.position.set(
            player.getPosition().x * Constants.TILE_SIZE,
            player.getPosition().y * Constants.TILE_SIZE,
            0
        );

        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        level.render(game.batch);
        player.render(game.batch);
        for (Entity entity : entities) {
            entity.render(game.batch);
        }
        
        game.batch.end();

        hudStage.getViewport().apply();
        hudStage.act(delta);
        hudStage.draw();
    }

    public void reset() {
        player = new Player(level.getPlayerSpawn());
        entities = level.getEntities();
    }

    public void win() {
        game.currentLevel += 1;
        game.setScreen(new WinScreen(game));
        dispose();
    }
    
    public void die() {
        deathCount++;
        System.out.println(deathCount);
        deathLabel.setText("Safety Violations: " + deathCount);
        reset();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
	    hudStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        level.dispose();
        hudStage.dispose();

        for (Entity entity : entities) {
            entity.dispose();
        }
    }
}

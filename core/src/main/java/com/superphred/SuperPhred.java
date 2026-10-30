package com.superphred;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SuperPhred extends Game {
    public SpriteBatch batch;
    public Viewport viewport;
    public BitmapFont font;
    public int currentLevel = 0;

    @Override
    public void create() { 
        batch = new SpriteBatch();
        viewport = new FitViewport(16, 9);
        font = new BitmapFont();
        
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

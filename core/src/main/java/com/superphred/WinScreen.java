package com.superphred;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class WinScreen implements Screen {
    final SuperPhred game;

    public WinScreen(final SuperPhred game) {
        this.game = game;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

		game.batch.begin();

        int levelNum = Gdx.files.internal("level_order.txt").readString().split("\\R").length;
        
        if (this.game.currentLevel + 1 > levelNum) {
            game.font.draw(game.batch, "You completed the game!", 1, 2.0f);
            game.font.draw(game.batch, "Good job.", 1, 1.5f);
            game.font.draw(game.batch, "Click to crash the game.", 1, 1.0f);
            game.batch.end();
        } else {
            game.font.draw(game.batch, "You won!", 1, 1.5f);
            game.font.draw(game.batch, "Click to continue", 1, 1f);
            game.batch.end();
        }

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}

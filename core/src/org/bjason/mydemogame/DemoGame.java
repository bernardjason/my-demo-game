package org.bjason.mydemogame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DemoGame extends ApplicationAdapter {
    SpriteBatch batch;
    Snake snake;
    Prize prize;
    BitmapFont font;
    boolean started = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        snake = new Snake();
        prize = new Prize();
        font = new BitmapFont();

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown (int screenX, int screenY, int pointer, int button) {
                started = true;
                return true;
            }
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                        Gdx.app.exit();
                        break;
                    case Input.Keys.LEFT:
                        snake.leftRight(-1.0f);
                        break;
                    case Input.Keys.RIGHT:
                        snake.leftRight(1.0f);
                        break;
                    case Input.Keys.UP:
                        snake.upDown(1.0f);
                        break;
                    case Input.Keys.DOWN:
                        snake.upDown(-1.0f);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if ( started ) mainGamePlay();
        else {
            batch.begin();
            font.draw(batch, "click on screen to start...",199,Gdx.graphics.getHeight()/2.0f);
            batch.end();

        }
    }

    private void mainGamePlay() {
        float delta = Gdx.graphics.getDeltaTime();
        batch.begin();
        if (snake.lives > 0) {
            snake.update(delta);
            prize.update(delta);
            snake.checkPrize(prize);
            font.draw(batch, "Lives " + snake.lives + " score " + snake.score + " time " + snake.elapsedTime, 10, Gdx.graphics.getHeight() - font.getCapHeight());
        } else {
            font.draw(batch, "Game over..... score " + snake.score, 10, Gdx.graphics.getHeight() - font.getCapHeight());
        }
        batch.end();

        batch.begin();
        snake.draw();
        prize.draw();
        font.setColor(Color.RED);
        batch.end();
    }

    @Override
    public void dispose() {
        snake.dispose();
        prize.dispose();
        font.dispose();
        batch.dispose();
    }
}
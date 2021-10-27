package org.bjason.mydemogame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

public class DemoGame extends ApplicationAdapter {
    SpriteBatch batch;
    Snake snake;
    Prize prize;
    BitmapFont font;
    boolean started = false;
    static float SENSITIVE = 20.0f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        snake = new Snake();
        prize = new Prize();
        font = new BitmapFont();


        float scaleX = Gdx.graphics.getHeight() / 380.0f;
        float scaleY = Gdx.graphics.getWidth() / 540.0f;

        float scale = scaleX;
        if ( scaleY > scaleX ) scale = scaleY;
        font.getData().setScale(scale,scale);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(new GestureDetector(new GestureDetector.GestureAdapter() {
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                if ( deltaX < -SENSITIVE) snake.leftRight(-1.0f);
                if ( deltaX > SENSITIVE) snake.leftRight(1.0f);
                if ( deltaY > SENSITIVE) snake.upDown(-1.0f);
                if ( deltaY < -SENSITIVE) snake.upDown(1.0f);
                return true;
            }
            public boolean tap(float x, float y, int count, int button) {
                started = true;
                return true;
            }
        }));

        inputMultiplexer.addProcessor(new InputAdapter() {
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
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if ( started ) mainGamePlay();
        else {
            batch.begin();
            font.draw(batch, "click on screen to start...",50,Gdx.graphics.getHeight()/2.0f);
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
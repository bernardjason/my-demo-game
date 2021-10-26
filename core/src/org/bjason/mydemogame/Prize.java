package org.bjason.mydemogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Prize extends Rectangle {
    private final ShapeRenderer shapeRenderer;
    static float TIMER = 10;
    static float WIDTH = 10.0f;
    static float HEIGHT = 10.0f;
    private float ticks = TIMER;

    public Prize() {
        randomPosition();
        shapeRenderer = new ShapeRenderer();
        setSize(WIDTH, HEIGHT);
    }

    public void randomPosition() {
        x = WIDTH*5 + (float) (Math.random() * 100000)  % (Gdx.graphics.getWidth() - WIDTH*10);
        y = HEIGHT*5 + (float)(Math.random() * 100000)  % (Gdx.graphics.getHeight()- HEIGHT*10);

        ticks = TIMER;
    }

    public void update(float alpha) {
        ticks = ticks - alpha ;
        if ( ticks < 0 ) {
            randomPosition();
        }
    }

    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, WIDTH+1, HEIGHT+1);
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

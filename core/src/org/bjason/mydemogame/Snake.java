package org.bjason.mydemogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Stack;

public class Snake extends Rectangle {
    static float START_SPEED = 90.0f;
    static float WIDTH = 5.0f;
    static float HEIGHT = 5.0f;
    private final ShapeRenderer shapeRenderer;
    private final Stack<Rectangle> tail;
    private float ticks = 0;
    private float tailLength;
    private final Vector2 direction;
    private float speed = START_SPEED;
    private boolean hit = false;
    private long startTime = 0;
    long elapsedTime;
    int lives=3;
    int score;

    public Snake() {
        tail = new Stack<Rectangle>();
        direction = new Vector2();
        shapeRenderer = new ShapeRenderer();
        resetSnake();
    }

    private void resetSnake() {
        this.setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f);
        setSize(WIDTH, HEIGHT);
        direction.x = 1;
        direction.y = 0;
        tailLength =100;
        tail.clear();
        hit=false;
        startTime=TimeUtils.millis();
        speed = START_SPEED;
    }

    public void leftRight(float d) {
        direction.y = 0;
        direction.x = d;
        makeSureWeDoNotMissABitOnDirectionChange();
    }

    public void upDown(float d) {
        direction.y = d;
        direction.x = 0;
        makeSureWeDoNotMissABitOnDirectionChange();
    }

    private void makeSureWeDoNotMissABitOnDirectionChange() {
        Rectangle addTail = new Rectangle(this);
        tail.insertElementAt(addTail, 0);
    }

    public void update(float alpha) {
        elapsedTime = TimeUtils.timeSinceMillis(startTime) /1000;
        ticks = ticks + alpha * speed;
        tailLength = tailLength + alpha * speed / 8;
        speed = speed + alpha * 1.5f;

        Rectangle addTail = new Rectangle(this);

        float addX = direction.x * speed * alpha;
        float addY = direction.y * speed * alpha;
        x = x + addX;
        y = y + addY;

        addTail.setSize(Math.abs(addTail.x -x), Math.abs(addTail.y - y));
        if (addTail.width == 0) addTail.setWidth(WIDTH);
        if (addTail.height == 0) addTail.setHeight(HEIGHT);
        if (addTail.x >= x ) addTail.x = x;
        if (addTail.y >= y ) addTail.y = y;

        tail.insertElementAt(addTail, 0);

        if (x < 0.0 || x > Gdx.graphics.getWidth() - WIDTH) {
            hit=true;
        }
        if (y < 0.0 || y > Gdx.graphics.getHeight() - HEIGHT) {
            hit=true;
        }
        while (tail.size() > (int) tailLength) {
            tail.pop();
        }
        for (int i = (int) WIDTH * 2; i < tail.size(); i++) {
            Rectangle r = tail.get(i);
            if (r.overlaps(this)) {
                hit=true;
            }
        }
        if ( hit ) {
            lives=lives-1;
            resetSnake();
        }
    }

    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(x, y, WIDTH, HEIGHT);
        for (Rectangle r : tail) {
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }
        shapeRenderer.end();
    }

    public void checkPrize(Prize prize) {
        if (prize.overlaps(this)) {
            tailLength = tailLength /2;
            prize.randomPosition();
            score = score + (int)elapsedTime;
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

package com.timeloopescape.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Crystal {

    private float x;
    private float y;
    private float baseY;
    private float size = 40;
    private float time = 0f;

    private boolean collected = false;

    private Texture texture;
    private Rectangle bounds;

    public Crystal(float x, float y) {
        this.x = x;
        this.y = y;
        this.baseY = y;

        texture = new Texture("items/crystal.png");
        bounds = new Rectangle(x, y, size, size);
    }

    public void update(float delta) {
        if (collected) {
            return;
        }

        time += delta;
        y = baseY + (float) Math.sin(time * 2.5f) * 6f;
        bounds.setPosition(x, y);
    }

    public void draw(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, x, y, size, size);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    public void dispose() {
        texture.dispose();
    }
}

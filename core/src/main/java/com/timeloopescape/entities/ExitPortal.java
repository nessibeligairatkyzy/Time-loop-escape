package com.timeloopescape.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class ExitPortal {

    private final Rectangle bounds;
    private float time = 0f;

    public ExitPortal(float x, float y) {
        this.bounds = new Rectangle(x, y, 70, 90);
    }

    public void update(float delta) {
        time += delta;
    }

    public void render(ShapeRenderer shapeRenderer) {
        float centerX = bounds.x + bounds.width / 2f;
        float centerY = bounds.y + bounds.height / 2f;

        shapeRenderer.setColor(0.45f, 0.25f, 0.9f, 0.7f);
        shapeRenderer.circle(centerX, centerY, 42 + (float) Math.sin(time * 3f) * 3f);

        shapeRenderer.setColor(0.25f, 0.9f, 1f, 0.8f);
        shapeRenderer.circle(centerX, centerY, 25 + (float) Math.cos(time * 4f) * 2f);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

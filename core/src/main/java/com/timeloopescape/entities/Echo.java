package com.timeloopescape.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Echo {

    private float x;
    private float y;
    private float targetX;
    private float targetY;

    private float width = 80;
    private float height = 80;
    private float speed = 120f;

    private Texture texture;
    private String label;
    private float pulseTime = 0f;

    private boolean reachedTarget = false;

    public Echo(float x, float y, float targetX, float targetY, String label) {
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.label = label;

        texture = new Texture("player/idle.png");
    }

    public void update(float delta) {
        pulseTime += delta;

        if (!reachedTarget) {
            moveToTarget(delta);
        }
    }

    private void moveToTarget(float delta) {
        float dx = targetX - x;
        float dy = targetY - y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance < 5f) {
            x = targetX;
            y = targetY;
            reachedTarget = true;
            return;
        }

        x += (dx / distance) * speed * delta;
        y += (dy / distance) * speed * delta;
    }

    public boolean hasReachedTarget() {
        return reachedTarget;
    }

    public void draw(SpriteBatch batch, BitmapFont font) {
        float alpha = 0.45f + (float) Math.sin(pulseTime * 3f) * 0.12f;

        batch.setColor(0.55f, 0.9f, 1f, alpha);
        batch.draw(texture, x, y, width, height);

        batch.setColor(1f, 1f, 1f, 1f);

        font.getData().setScale(1.0f);
        font.draw(batch, "∞", x + 32, y + 105);
        font.draw(batch, label, x - 10, y + 95);
    }

    public void dispose() {
        texture.dispose();
    }
}

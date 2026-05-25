package com.timeloopescape.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.timeloopescape.observer.Observer;

public class Door implements Observer {

    private final Rectangle bounds;
    private boolean open = false;

    public Door(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    @Override
    public void onNotify(String event) {
        if (event.equals("SWITCH_ACTIVATED")) {
            open = true;
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (open) {
            shapeRenderer.setColor(0.2f, 0.9f, 1f, 0.35f);
        } else {
            shapeRenderer.setColor(0.12f, 0.08f, 0.18f, 1f);
        }

        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

        shapeRenderer.setColor(0.6f, 0.4f, 0.95f, 1f);
        shapeRenderer.rectLine(bounds.x, bounds.y, bounds.x + bounds.width, bounds.y + bounds.height, 2);
        shapeRenderer.rectLine(bounds.x + bounds.width, bounds.y, bounds.x, bounds.y + bounds.height, 2);
    }

    public boolean isOpen() {
        return open;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

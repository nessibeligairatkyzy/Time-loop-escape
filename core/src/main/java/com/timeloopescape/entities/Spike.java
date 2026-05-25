package com.timeloopescape.entities;

import com.badlogic.gdx.math.Rectangle;

public class Spike {

    private Rectangle bounds;

    public Spike(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
// Team Member 1 — Hazard Logic:
// This class represents dangerous spike areas that cause Game Over
// when the player touches them.

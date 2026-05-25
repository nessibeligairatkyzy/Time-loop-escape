package com.timeloopescape.entities;

import com.badlogic.gdx.math.Rectangle;

public class Platform {

    private Rectangle bounds;

    public Platform(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
// Team Member 1 — Platform Logic:
// This class represents platform collision zones used for jumping
// and level navigation.

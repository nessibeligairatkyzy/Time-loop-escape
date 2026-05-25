package com.timeloopescape.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.timeloopescape.observer.Observer;
import com.timeloopescape.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class Switch implements Subject {

    private final Rectangle bounds;
    private final List<Observer> observers = new ArrayList<>();

    private boolean activated = false;

    public Switch(float x, float y) {
        this.bounds = new Rectangle(x, y, 52, 24);
    }

    public void activate() {
        if (!activated) {
            activated = true;
            notifyObservers("SWITCH_ACTIVATED");
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (activated) {
            shapeRenderer.setColor(0.25f, 0.95f, 1f, 1f);
        } else {
            shapeRenderer.setColor(0.75f, 0.45f, 1f, 1f);
        }

        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

        shapeRenderer.setColor(1f, 1f, 1f, 0.9f);
        shapeRenderer.circle(bounds.x + bounds.width / 2f, bounds.y + bounds.height / 2f, 6);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer observer : observers) {
            observer.onNotify(event);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isActivated() {
        return activated;
    }
}

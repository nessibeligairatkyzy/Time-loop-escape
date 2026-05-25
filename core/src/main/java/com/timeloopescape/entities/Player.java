package com.timeloopescape.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    private float x;
    private float y;
    private float velocityY = 0;

    private boolean onGround = true;
    private boolean moving = false;

    private Sound jumpSound;
    private Sound footstepSound;
    private float stepTimer = 0f;

    private final float gravity = -900f;
    private final float jumpPower = 520f;

    private final float width = 80;
    private final float height = 80;
    private final float speed = 240f;

    private boolean facingRight = true;

    private Texture idleTexture;
    private Texture runTexture;
    private Texture jumpTexture;
    private Texture currentTexture;

    private final Rectangle bounds;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;

        idleTexture = new Texture("player/idle.png");
        runTexture = new Texture("player/run.png");
        jumpTexture = new Texture("player/jump.png");

        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        footstepSound = Gdx.audio.newSound(Gdx.files.internal("sounds/footsteps.wav"));

        currentTexture = idleTexture;
        bounds = new Rectangle(this.x, this.y, width, height);
    }

    public void update(float delta) {
        moving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed * delta;
            moving = true;
            facingRight = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed * delta;
            moving = true;
            facingRight = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && onGround) {
            velocityY = jumpPower;
            onGround = false;
            jumpSound.play(0.7f);
        }

        velocityY += gravity * delta;
        y += velocityY * delta;

        if (moving && onGround) {
            stepTimer += delta;

            if (stepTimer >= 0.35f) {
                footstepSound.play(0.3f);
                stepTimer = 0f;
            }
        } else {
            stepTimer = 0f;
        }

        updateTexture();

        keepInsideScreen();
        bounds.setPosition(x, y);
    }

    private void updateTexture() {
        if (!onGround) {
            currentTexture = jumpTexture;
        } else if (moving) {
            currentTexture = runTexture;
        } else {
            currentTexture = idleTexture;
        }
    }

    public void draw(SpriteBatch batch) {
        if (facingRight) {
            batch.draw(currentTexture, x, y, width, height);
        } else {
            batch.draw(currentTexture, x + width, y, -width, height);
        }
    }

    private void keepInsideScreen() {
        if (x < 0) {
            x = 0;
        }

        if (x + width > Gdx.graphics.getWidth()) {
            x = Gdx.graphics.getWidth() - width;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        bounds.setPosition(x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;

        if (onGround) {
            velocityY = 0;
            updateTexture();
        }
    }

    public void dispose() {
        idleTexture.dispose();
        runTexture.dispose();
        jumpTexture.dispose();
        jumpSound.dispose();
        footstepSound.dispose();
    }
}

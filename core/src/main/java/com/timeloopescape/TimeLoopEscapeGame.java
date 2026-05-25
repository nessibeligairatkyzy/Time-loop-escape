package com.timeloopescape;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.timeloopescape.screens.MainMenuScreen;

// Main game class — initializes the game and sets the first screen
public class TimeLoopEscapeGame extends Game {

    // Shared SpriteBatch used across all screens
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Start with the main menu screen
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        // Free memory when game closes
        batch.dispose();
        super.dispose();
    }
}

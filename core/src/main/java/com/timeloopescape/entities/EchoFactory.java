package com.timeloopescape.entities;

// Team Member 1 — Factory Pattern:
// This class creates different Echo types for the game.
// It separates Echo creation logic from GameScreen.

public class EchoFactory {

    public static Echo createActivatorEcho() {
        return new Echo(100, 110, 900, 310, "ACTIVATOR");
    }

    public static Echo createGuardianEcho() {
        return new Echo(900, 390, 900, 390, "GUARDIAN");
    }
}

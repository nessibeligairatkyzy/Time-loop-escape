# Project Structure

```
TimeLoopEscapeFinal/
│
├── assets/
│   ├── menu/
│   │   └── menu_background.png
│   │
│   ├── player/
│   │   ├── idle.png
│   │   ├── run.png
│   │   └── jump.png
│   │
│   ├── sounds/
│   │   ├── background.mp3
│   │   ├── click.wav
│   │   ├── coin.wav
│   │   ├── footsteps.wav
│   │   ├── jump.wav
│   │   └── magicportal.wav
│   │
│   └── items/
│       └── crystal.png
│
├── core/
│   └── src/main/java/com/timeloopescape/
│       ├── entities/
│       │   ├── Player.java
│       │   ├── Crystal.java
│       │   ├── Door.java
│       │   ├── Switch.java
│       │   ├── ExitPortal.java
│       │   └── Echo.java
│       │
│       ├── observer/
│       │   ├── Observer.java
│       │   └── Subject.java
│       │
│       ├── screens/
│       │   ├── MainMenuScreen.java
│       │   └── GameScreen.java
│       │
│       └── TimeLoopEscapeGame.java
│
├── lwjgl3/
│   └── src/main/java/com/timeloopescape/lwjgl3/
│       └── Lwjgl3Launcher.java
│
├── build.gradle
├── settings.gradle
├── README.md
├── PROJECT_STRUCTURE.md
└── .gitignore
```

## Main Classes

### Player.java
Handles player movement, jumping, direction changes, animation textures, and player sound effects.

### GameScreen.java
Contains the main gameplay logic:
- Level system
- Timer
- Crystal collection
- Switch interaction
- Door and portal logic
- Hazards
- Echo system
- Game over and level complete screens

### MainMenuScreen.java
Handles the main menu: Play, Continue, Levels, Settings, Exit.

### Switch.java and Door.java
Used together with the Observer Pattern.  
The switch notifies the door when activated.

### Echo.java
Represents Mira's time clone.  
Used as a simplified Echo mechanic in the prototype.

## Design Pattern

The project demonstrates the **Observer Pattern** through the switch-door interaction system.

```
Switch (Subject)
  └── notifies → Door (Observer)
                  └── opens when switch is activated
```

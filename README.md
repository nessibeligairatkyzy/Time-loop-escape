# Time Loop Escape

**Time Loop Escape** is a 2D puzzle-platformer game created with Java and LibGDX.  
The game follows Mira, a girl trapped inside a broken time loop. To escape, she must collect time crystals, activate switches, avoid hazards, and use Echoes — mysterious time clones that help her complete the loop.

## Game Concept

Mira is stuck in a time loop. Each level represents a different stage of the loop.  
The player must collect all crystals, activate switches, open doors, and reach the portal before time runs out.

The game includes:
- 2D movement
- Jumping mechanic
- Crystal collection
- Timer system
- Switch and door interaction
- Portal escape system
- Hazard obstacles
- Sound effects and background music
- Echo clone concept
- Three playable levels

## Main Character

**Mira** is the main character of the game.  
She is a time traveler trying to escape a broken loop by collecting crystals and activating ancient mechanisms.

## Echo System

Echoes are time clones of Mira.  
In the full concept, Echoes repeat or support Mira's actions from previous loops.  
In this prototype, Echoes are implemented as simplified role-based helpers:

### Activator Echo
Appears in Level 2.  
Its role is to help activate mechanisms and support Mira's progress.

### Guardian Echo
Appears in Level 3.  
Its role is to protect the final switch from Time Corruption and help Mira break the loop.

## Levels

### Level 1 — Crystal Gate
**Objective:** Collect 4 crystals, activate the switch, open the door, and enter the portal.  
**Time limit:** 30 seconds  
**Mechanics introduced:** Movement, jumping, crystal collection, switch activation, door opening, portal escape

### Level 2 — Echo Activation
**Objective:** Collect 5 crystals and use the Activator Echo mechanic to escape.  
**Time limit:** 35 seconds  
**Mechanics introduced:** More difficult platforms, additional hazards, Echo support mechanic

### Level 3 — Final Time Gate
**Objective:** Collect 6 crystals, activate the final switch, and break the time loop.  
**Time limit:** 40 seconds  
**Mechanics introduced:** Guardian Echo, Time Corruption, final switch, final escape condition

## Controls

| Key | Action |
|-----|--------|
| A / Left Arrow | Move left |
| D / Right Arrow | Move right |
| Space | Jump |
| E | Activate switch |
| Esc | Return to menu |

## Design Pattern Used

### Observer Pattern

The project uses the **Observer Pattern** for the switch and door system.  
The switch does not directly open the door. Instead, the switch notifies its observers, and the door reacts to that notification.

```
Player presses E near Switch
↓
Switch activates
↓
Switch notifies Door
↓
Door opens
```

This makes the system more flexible and easier to extend.

## Technologies

- Java
- LibGDX
- Gradle
- IntelliJ IDEA

## Assets

Asset folders:

```
assets/
 ├── menu/
 ├── player/
 ├── sounds/
 └── items/
```

## How to Run

1. Open the project in IntelliJ IDEA
2. Wait until Gradle finishes loading
3. Run: `lwjgl3 → Lwjgl3Launcher`

## Project Status

This is a playable prototype created for a Java / LibGDX project.  
The game includes three levels, interactive objects, sound effects, timer, menu screens, and a simplified Echo mechanic.

## Future Improvements

- Full action-recording Echo system
- More Echo types: Runner, Carrier, Sacrificial, Harmonic
- More levels
- Animated traps
- Better UI animations
- Story cutscenes
- Save system

## Team Members and Contributions

This project was created by a team of two students.

### Team Member 1 — Gameplay & Core Logic Developer

Responsibilities:
- Implemented player movement, jumping, and direction control
- Created the three-level gameplay system
- Added crystal collection mechanics
- Implemented timer, game over, and level complete logic
- Developed switch, door, and portal interaction
- Added spike hazards and level obstacles
- Implemented the Observer Pattern for the switch-door system

### Team Member 2 — UI, Visual & Sound Developer

Responsibilities:
- Designed and implemented the main menu
- Added Play, Continue, Levels, Settings, and Exit menu functionality
- Created the Levels and Settings information screens
- Added player sprites: idle, run, and jump
- Added crystal visuals and game assets
- Integrated background music and sound effects
- Improved the visual atmosphere of the game
- Prepared README, project structure documentation, and GitHub repository organization

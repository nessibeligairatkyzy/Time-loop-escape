package com.timeloopescape.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.timeloopescape.TimeLoopEscapeGame;
import com.timeloopescape.entities.Crystal;
import com.timeloopescape.entities.Door;
import com.timeloopescape.entities.ExitPortal;
import com.timeloopescape.entities.Player;
import com.timeloopescape.entities.Switch;
import com.badlogic.gdx.math.Rectangle;
import com.timeloopescape.entities.Echo;
import com.timeloopescape.entities.EchoFactory;

public class GameScreen implements Screen {
    private static int savedLevel = 1;
    private int startLevel = 1;

    public static int getSavedLevel() {
        return savedLevel;
    }

    private final TimeLoopEscapeGame game;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;


    private Music backgroundMusic;
    private Sound clickSound;
    private Sound portalSound;
    private Sound coinSound;
    private Echo echo;
    private boolean echoActivatedSwitch = false;
    private boolean finalSwitchActivated = false;
    private float corruptionTime = 0f;

    private Player player;
    private Switch timeSwitch;
    private Door door;
    private ExitPortal exitPortal;
    private Crystal[] crystals;

    private Rectangle[] platforms;
    private Rectangle[] spikes;

    private int currentLevel = 1;
    private int score = 0;
    private float timer = 30f;

    private boolean levelComplete = false;
    private boolean gameOver = false;

    public GameScreen(TimeLoopEscapeGame game, int startLevel) {
        this.game = game;
        this.startLevel = startLevel;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getData().setScale(1.8f);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();

        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
        portalSound = Gdx.audio.newSound(Gdx.files.internal("sounds/magicportal.wav"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));

        loadLevel(startLevel);
    }


    private void loadLevel(int level) {
        savedLevel = level;

        currentLevel = level;
        score = 0;
        levelComplete = false;
        gameOver = false;
        echo = null;
        echoActivatedSwitch = false;
        finalSwitchActivated = false;
        corruptionTime = 0f;

        if (level == 1) {
            timer = 30f;

            player = new Player(90, 110);
            timeSwitch = new Switch(430, 115);
            door = new Door(720, 90, 42, 150);
            exitPortal = new ExitPortal(1030, 105);

            crystals = new Crystal[] {
                new Crystal(250, 140),
                new Crystal(500, 140),
                new Crystal(800, 140),
                new Crystal(960, 140)
            };

            platforms = new Rectangle[] {
                new Rectangle(0, 80, 1280, 30),
                new Rectangle(230, 180, 160, 20),
                new Rectangle(500, 240, 160, 20),
                new Rectangle(760, 180, 160, 20)
            };

            spikes = new Rectangle[] {
                new Rectangle(600, 80, 100, 30)
            };

        } else if (level == 2) {
            echo = EchoFactory.createActivatorEcho();
            timer = 35f;

            player = new Player(80, 110);
            timeSwitch = new Switch(900, 310);
            door = new Door(1040, 90, 42, 150);
            exitPortal = new ExitPortal(1140, 105);

            crystals = new Crystal[] {
                new Crystal(220, 140),
                new Crystal(360, 230),
                new Crystal(560, 320),
                new Crystal(760, 230),
                new Crystal(940, 330)
            };

            platforms = new Rectangle[] {
                new Rectangle(0, 80, 1280, 30),
                new Rectangle(300, 200, 170, 20),
                new Rectangle(520, 290, 170, 20),
                new Rectangle(740, 200, 170, 20),
                new Rectangle(880, 300, 180, 20)
            };

            spikes = new Rectangle[] {
                new Rectangle(420, 80, 100, 30),
                new Rectangle(660, 80, 100, 30)
            };

        } else if (level == 3) {
            echo = EchoFactory.createGuardianEcho();
            timer = 40f;

            player = new Player(70, 110);
            timeSwitch = new Switch(980, 390);
            door = new Door(1100, 330, 42, 150);
            exitPortal = new ExitPortal(1180, 345);

            crystals = new Crystal[] {
                new Crystal(200, 140),
                new Crystal(340, 220),
                new Crystal(500, 300),
                new Crystal(680, 220),
                new Crystal(850, 300),
                new Crystal(1030, 420)
            };

            platforms = new Rectangle[] {
                new Rectangle(0, 80, 1280, 30),
                new Rectangle(170, 180, 140, 20),
                new Rectangle(320, 260, 140, 20),
                new Rectangle(480, 340, 140, 20),
                new Rectangle(660, 260, 140, 20),
                new Rectangle(830, 340, 160, 20),
                new Rectangle(980, 420, 180, 20)
            };

            spikes = new Rectangle[] {
                new Rectangle(300, 80, 100, 30),
                new Rectangle(560, 80, 100, 30),
                new Rectangle(760, 80, 100, 30)
            };
        }

        timeSwitch.addObserver(door);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.015f, 0.015f, 0.035f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawBackground();
        drawPlatforms();
        drawSpikes();
        drawTimeCorruption();

        exitPortal.render(shapeRenderer);
        timeSwitch.render(shapeRenderer);
        door.render(shapeRenderer);
        shapeRenderer.end();

        batch.begin();

        font.getData().setScale(1.8f);
        font.draw(batch, "LEVEL: " + currentLevel, 30, 700);
        font.draw(batch, "TIME: " + (int) timer, 30, 660);
        font.draw(batch, "CRYSTALS: " + score + "/" + crystals.length, 30, 620);
        font.draw(batch, "A/D - MOVE   SPACE - JUMP   E - ACTIVATE", 30, 580);

        if (currentLevel == 3) {
            font.getData().setScale(1.2f);
            font.draw(batch, "GUARDIAN ECHO: protecting the final switch", 30, 545);
        }

        for (Crystal crystal : crystals) {
            crystal.draw(batch);
        }
        if (echo != null) {
            echo.draw(batch, font);
        }

        player.draw(batch);
        batch.end();

        if (levelComplete) {
            drawLevelCompleteOverlay();
        }

        if (gameOver) {
            drawGameOverOverlay();
        }
    }

    private void update(float delta) {

        if (gameOver) {
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                clickSound.play();
                loadLevel(currentLevel);
            }
            return;
        }

        if (levelComplete) {
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                clickSound.play();

                if (currentLevel < 3) {
                    loadLevel(currentLevel + 1);
                } else {
                    game.setScreen(new MainMenuScreen(game));
                }
            }
            return;
        }

        timer -= delta;

        if (timer <= 0) {
            timer = 0;
            gameOver = true;
            return;
        }

        player.update(delta);
        corruptionTime += delta;
        if (echo != null) {
            echo.update(delta);
        }

        if (currentLevel == 2
            && echo != null
            && echo.hasReachedTarget()
            && !echoActivatedSwitch) {

            clickSound.play();
            timeSwitch.activate();
            echoActivatedSwitch = true;
        }
        handlePlatformCollisions();
        handleSpikeCollisions();
        handleFall();

        for (Crystal crystal : crystals) {
            crystal.update(delta);

            if (!crystal.isCollected()
                && player.getBounds().overlaps(crystal.getBounds())) {

                crystal.collect();
                coinSound.play();
                score++;
            }
        }

        if (player.getBounds().overlaps(timeSwitch.getBounds())
            && Gdx.input.isKeyJustPressed(Input.Keys.E)) {

            if (currentLevel == 3) {
                if (score == crystals.length && echo != null) {
                    clickSound.play();
                    timeSwitch.activate();
                    finalSwitchActivated = true;
                }
            } else {
                clickSound.play();
                timeSwitch.activate();
            }
        }

        if (!levelComplete) {

            if (currentLevel == 3 && finalSwitchActivated) {
                portalSound.play(1.0f);
                levelComplete = true;
            }

            if (currentLevel != 3
                && door.isOpen()
                && score == crystals.length
                && player.getBounds().overlaps(exitPortal.getBounds())) {

                portalSound.play(1.0f);
                levelComplete = true;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            clickSound.play();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void handlePlatformCollisions() {
        Rectangle playerBounds = player.getBounds();

        for (Rectangle platform : platforms) {
            if (playerBounds.overlaps(platform)) {
                boolean falling = player.getVelocityY() <= 0;
                boolean playerAbovePlatform = playerBounds.y >= platform.y + platform.height - 25;

                if (falling && playerAbovePlatform) {
                    player.setPosition(player.getX(), platform.y + platform.height);
                    player.setVelocityY(0);
                    player.setOnGround(true);
                }
            }
        }
    }

    private void handleSpikeCollisions() {
        for (Rectangle spike : spikes) {
            if (player.getBounds().overlaps(spike)) {
                gameOver = true;
                return;
            }
        }
    }

    private void handleFall() {
        if (player.getY() < 20) {
            gameOver = true;
        }
    }

    private void drawBackground() {
        if (currentLevel == 1) {
            shapeRenderer.setColor(0.025f, 0.02f, 0.05f, 1f);
        } else if (currentLevel == 2) {
            shapeRenderer.setColor(0.01f, 0.04f, 0.08f, 1f);
        } else {
            shapeRenderer.setColor(0.08f, 0.01f, 0.05f, 1f);
        }

        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer.setColor(0.09f, 0.04f, 0.16f, 1f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), 260);

        shapeRenderer.setColor(0.18f, 0.08f, 0.32f, 0.8f);
        shapeRenderer.circle(1060, 420, 110);

        shapeRenderer.setColor(0.08f, 0.7f, 1f, 0.5f);
        shapeRenderer.circle(1060, 420, 70);
    }

    private void drawPlatforms() {
        for (Rectangle platform : platforms) {
            shapeRenderer.setColor(0.12f, 0.1f, 0.18f, 1f);
            shapeRenderer.rect(platform.x, platform.y, platform.width, platform.height);

            shapeRenderer.setColor(0.2f, 0.9f, 1f, 1f);
            shapeRenderer.rect(platform.x, platform.y + platform.height - 3, platform.width, 3);
        }
    }

    private void drawSpikes() {
        for (Rectangle spike : spikes) {
            shapeRenderer.setColor(1f, 0.2f, 0.55f, 1f);

            float startX = spike.x;
            float y = spike.y;
            float spikeWidth = 20;

            for (float x = startX; x < spike.x + spike.width; x += spikeWidth) {
                shapeRenderer.triangle(
                    x, y,
                    x + spikeWidth / 2f, y + spike.height,
                    x + spikeWidth, y
                );
            }
        }
    }
    private void drawTimeCorruption() {
        if (currentLevel != 3) {
            return;
        }

        float pulse = (float) Math.sin(corruptionTime * 5f) * 8f;

        shapeRenderer.setColor(0.9f, 0.05f, 0.25f, 0.55f);
        shapeRenderer.circle(980, 410, 55 + pulse);

        shapeRenderer.setColor(0.35f, 0.02f, 0.08f, 0.85f);
        shapeRenderer.circle(980, 410, 32 + pulse / 2f);

        shapeRenderer.setColor(1f, 0.2f, 0.45f, 1f);
        shapeRenderer.rect(940, 405, 80, 6);
        shapeRenderer.rect(975, 370, 6, 80);
    }

    private void drawLevelCompleteOverlay() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.005f, 0.005f, 0.02f, 0.92f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer.setColor(0.18f, 0.04f, 0.35f, 1f);
        shapeRenderer.rect(230, 155, 820, 410);

        shapeRenderer.setColor(0.04f, 0.015f, 0.09f, 1f);
        shapeRenderer.rect(250, 175, 780, 370);

        shapeRenderer.setColor(0.15f, 0.9f, 1f, 1f);
        shapeRenderer.rect(250, 535, 780, 6);
        shapeRenderer.rect(250, 175, 780, 6);

        shapeRenderer.setColor(0.55f, 0.25f, 1f, 1f);
        shapeRenderer.rect(250, 175, 6, 370);
        shapeRenderer.rect(1024, 175, 6, 370);

        shapeRenderer.setColor(0.35f, 0.08f, 0.65f, 1f);
        shapeRenderer.rect(445, 245, 390, 70);

        shapeRenderer.setColor(0.12f, 0.04f, 0.25f, 1f);
        shapeRenderer.rect(455, 255, 370, 50);

        shapeRenderer.end();

        batch.begin();
        font.getData().setScale(3.2f);

        if (currentLevel < 3) {
            font.draw(batch, "LEVEL COMPLETE!", 345, 440);
            font.getData().setScale(1.5f);
            font.draw(batch, "Next level is opening...", 485, 365);
            font.getData().setScale(2f);
            font.draw(batch, "NEXT LEVEL", 535, 290);
        } else {
            font.draw(batch, "LOOP BROKEN!", 405, 440);
            font.getData().setScale(1.5f);
            font.draw(batch, "Mira escaped the time loop", 460, 365);
            font.getData().setScale(2f);
            font.draw(batch, "MENU", 590, 290);
        }

        batch.end();
    }

    private void drawGameOverOverlay() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.005f, 0.005f, 0.02f, 0.85f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        batch.begin();
        font.getData().setScale(3f);
        font.draw(batch, "GAME OVER", 490, 430);

        font.getData().setScale(1.5f);
        font.draw(batch, "Click or press ENTER to retry", 430, 360);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
        batch.dispose();
        backgroundMusic.dispose();
        clickSound.dispose();
        portalSound.dispose();
        coinSound.dispose();
        if (echo != null) {
            echo.dispose();
        }

        if (crystals != null) {
            for (Crystal crystal : crystals) {
                crystal.dispose();
            }
        }

        player.dispose();

    }
}
// Team Member 1 Айзада— Gameplay/Core Logic:
// This class controls the main gameplay process, including the level system,
// timer, crystals, hazards, Echo mechanics, switch-door interaction,
// portal escape logic, Game Over, and Level Complete states.

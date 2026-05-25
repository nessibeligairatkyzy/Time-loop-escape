package com.timeloopescape.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.timeloopescape.TimeLoopEscapeGame;

public class MainMenuScreen implements Screen {

    private final TimeLoopEscapeGame game;

    private Texture background;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private Sound clickSound;

    private final float buttonX = 488;
    private final float buttonWidth = 305;
    private final float buttonHeight = 42;

    private final float startButtonY = 331;
    private final float gap = 53;

    private final float backButtonX = 490;
    private final float backButtonY = 95;
    private final float backButtonWidth = 300;
    private final float backButtonHeight = 50;

    private int menuState = 0;
    // 0 = main menu
    // 1 = settings
    // 2 = levels

    public MainMenuScreen(TimeLoopEscapeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("menu/menu_background.png"));
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));

        createCustomCursor();
    }

    private void createCustomCursor() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);

        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        pixmap.setColor(0.75f, 0.45f, 1f, 1f);
        pixmap.fillTriangle(3, 2, 28, 14, 10, 21);

        pixmap.setColor(1f, 1f, 1f, 0.9f);
        pixmap.drawCircle(14, 14, 4);

        Cursor cursor = Gdx.graphics.newCursor(pixmap, 3, 2);
        Gdx.graphics.setCursor(cursor);

        pixmap.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.01f, 0.01f, 0.025f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBackground();

        if (menuState == 0) {
            drawHoverFrame();
            handleMainMenuInput();if (menuState == 0) {
                drawHoverFrame();
                handleMainMenuInput();
            }
        } else {
            drawInfoPanel();
            handleSubMenuInput();
        }
    }

    private void drawBackground() {
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1280, 720);
        game.batch.end();
    }



    private void drawHoverFrame() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0; i < 5; i++) {
            float y = startButtonY - i * gap;

            if (isInside(mouseX, mouseY, buttonX, y, buttonWidth, buttonHeight)) {
                shapeRenderer.setColor(0.55f, 0.9f, 1f, 1f);
                shapeRenderer.rect(buttonX, y, buttonWidth, buttonHeight);
            }
        }

        shapeRenderer.end();
    }

    private void handleMainMenuInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            clickSound.play();
            Gdx.app.exit();
        }

        if (Gdx.input.justTouched()) {
            clickSound.play();

            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float playY = startButtonY;
            float continueY = startButtonY - gap;
            float levelsY = startButtonY - 2 * gap;
            float settingsY = startButtonY - 3 * gap;
            float exitY = startButtonY - 4 * gap;

            if (isInside(mouseX, mouseY, buttonX, playY, buttonWidth, buttonHeight)) {
                game.setScreen(new GameScreen(game, 1));
            }

            if (isInside(mouseX, mouseY, buttonX, continueY, buttonWidth, buttonHeight)) {
                game.setScreen(new GameScreen(game, GameScreen.getSavedLevel()));
            }

            if (isInside(mouseX, mouseY, buttonX, levelsY, buttonWidth, buttonHeight)) {
                menuState = 2;
            }

            if (isInside(mouseX, mouseY, buttonX, settingsY, buttonWidth, buttonHeight)) {
                menuState = 1;
            }

            if (isInside(mouseX, mouseY, buttonX, exitY, buttonWidth, buttonHeight)) {
                Gdx.app.exit();
            }
        }
    }

    private void drawInfoPanel() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.005f, 0.005f, 0.02f, 0.92f);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer.setColor(0.04f, 0.015f, 0.09f, 1f);
        shapeRenderer.rect(250, 135, 780, 470);

        shapeRenderer.setColor(0.15f, 0.9f, 1f, 1f);
        shapeRenderer.rect(250, 600, 780, 5);
        shapeRenderer.rect(250, 135, 780, 5);

        shapeRenderer.setColor(0.55f, 0.25f, 1f, 1f);
        shapeRenderer.rect(250, 135, 5, 470);
        shapeRenderer.rect(1025, 135, 5, 470);

        if (isInside(mouseX, mouseY, backButtonX, backButtonY, backButtonWidth, backButtonHeight)) {
            shapeRenderer.setColor(0.45f, 0.9f, 1f, 1f);
        } else {
            shapeRenderer.setColor(0.35f, 0.08f, 0.65f, 1f);
        }

        shapeRenderer.rect(backButtonX, backButtonY, backButtonWidth, backButtonHeight);

        shapeRenderer.end();

        game.batch.begin();

        if (menuState == 1) {
            drawSettingsText();
        }

        if (menuState == 2) {
            drawLevelsText();
        }

        font.getData().setScale(1.7f);
        font.draw(game.batch, "BACK TO MENU", 540, 130);

        game.batch.end();
    }

    private void drawSettingsText() {
        font.getData().setScale(2.4f);
        font.draw(game.batch, "SETTINGS", 540, 555);

        font.getData().setScale(1.45f);
        font.draw(game.batch, "Music: ON", 390, 470);
        font.draw(game.batch, "Sound Effects: ON", 390, 430);
        font.draw(game.batch, "Controls:", 390, 370);
        font.draw(game.batch, "A / D  - Move", 430, 330);
        font.draw(game.batch, "SPACE  - Jump", 430, 295);
        font.draw(game.batch, "E      - Activate switch", 430, 260);
        font.draw(game.batch, "ESC    - Return to menu", 430, 225);
    }

    private void drawLevelsText() {
        font.getData().setScale(2.4f);
        font.draw(game.batch, "LEVELS", 570, 555);

        font.getData().setScale(1.35f);

        font.draw(game.batch, "LEVEL 1: Crystal Gate", 330, 485);
        font.draw(game.batch, "Task: collect 4 crystals, activate switch, enter portal", 360, 450);
        font.draw(game.batch, "Time: 30 seconds", 360, 420);

        font.draw(game.batch, "LEVEL 2: Echo Activation", 330, 355);
        font.draw(game.batch, "Task: collect 5 crystals, use Activator Echo, escape", 360, 320);
        font.draw(game.batch, "Time: 35 seconds", 360, 290);

        font.draw(game.batch, "LEVEL 3: Final Time Gate", 330, 225);
        font.draw(game.batch, "Task: collect 6 crystals, Guardian Echo protects switch", 360, 190);
        font.draw(game.batch, "Time: 40 seconds", 360, 160);
    }

    private void handleSubMenuInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            clickSound.play();
            menuState = 0;
        }

        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (isInside(mouseX, mouseY, backButtonX, backButtonY, backButtonWidth, backButtonHeight)) {
                clickSound.play();
                menuState = 0;
            }
        }
    }

    private boolean isInside(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        background.dispose();
        shapeRenderer.dispose();
        font.dispose();
        clickSound.dispose();
    }
}

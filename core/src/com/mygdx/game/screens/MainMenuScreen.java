package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.DifficultSettings;
import com.mygdx.game.GameResources;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.UIObjects.ButtonView;
import com.mygdx.game.UIObjects.ChooseNumberView;
import com.mygdx.game.UIObjects.ImageView;
import com.mygdx.game.UIObjects.MovingBackgroundView;
import com.mygdx.game.UIObjects.TextView;
import com.mygdx.game.managers.MemoryManager;

public class MainMenuScreen extends ScreenAdapter {
    MyGdxGame game;
    SpriteBatch batch;
    OrthographicCamera camera;
    int difficultLevel;

    MovingBackgroundView movingBackgroundView;
    ImageView fullBlackoutView;
    TextView textView;
    ButtonView startButton;
    ButtonView settingsButton;
    ButtonView exitButton;
    ChooseNumberView chooseLevel;

    long showTime;

    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
        batch = game.getBatch();
        camera = game.getCamera();
        difficultLevel = MemoryManager.loadDifficultLevel();
        DifficultSettings.setDifficultLevel(difficultLevel);

        movingBackgroundView = new MovingBackgroundView(0, GameResources.BACKGROUND_IMG_PATH);
        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_ING_PATH);
        textView = new TextView(game.getBigWhiteFont(), 180, 960, "Space Cleaner");
        startButton = new ButtonView(140, 646, 440, 70, GameResources.LONG_BUTTON_IMG_PATH, "start", game.getBlackFont());
        settingsButton = new ButtonView(140, 551, 440, 70, GameResources.LONG_BUTTON_IMG_PATH, "settings", game.getBlackFont());
        exitButton = new ButtonView(140, 456, 440, 70, GameResources.LONG_BUTTON_IMG_PATH, "exit", game.getBlackFont());
        chooseLevel = new ChooseNumberView(280, 812, difficultLevel, game.getBigWhiteFont());
    }

    @Override
    public void show() {
        showTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        if (TimeUtils.millis() - showTime > 50)
            handleInput();
        draw();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (startButton.isHit(touch.x, touch.y))
                game.startGame();
            if (settingsButton.isHit(touch.x, touch.y))
                game.openSettings();
            if (exitButton.isHit(touch.x, touch.y))
                game.exit();
            int newDifficultLevel = difficultLevel;
            if (chooseLevel.isMinusHit(touch.x, touch.y))
                newDifficultLevel = chooseLevel.decrease();
            if (chooseLevel.isPlusHit(touch.x, touch.y))
                newDifficultLevel = chooseLevel.increase();
            if (newDifficultLevel != difficultLevel) {
                difficultLevel = newDifficultLevel;
                DifficultSettings.setDifficultLevel(difficultLevel);
            }
        }
    }

    private void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();

        movingBackgroundView.draw(batch);
        fullBlackoutView.draw(batch);
        textView.draw(batch);
        startButton.draw(batch);
        settingsButton.draw(batch);
        exitButton.draw(batch);
        chooseLevel.draw(batch);

        batch.end();
    }

    public void dispose() {
        fullBlackoutView.dispose();
        movingBackgroundView.dispose();
        textView.dispose();
        startButton.dispose();
        settingsButton.dispose();
        exitButton.dispose();
        chooseLevel.dispose();
    }

    public int getDifficultLevel() {
        return difficultLevel;
    }
}

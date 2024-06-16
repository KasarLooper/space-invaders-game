package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.UIObjects.ButtonView;
import com.mygdx.game.UIObjects.ImageView;
import com.mygdx.game.UIObjects.MovingBackgroundView;
import com.mygdx.game.UIObjects.TextView;
import com.mygdx.game.managers.MemoryManager;

public class SettingsScreen extends ScreenAdapter {
    MyGdxGame game;
    SpriteBatch batch;
    OrthographicCamera camera;

    MovingBackgroundView backgroundView;
    ImageView blackout;
    TextView textView;
    ButtonView returnButton;
    TextView clearRecordsButton;
    TextView soundsSwitchButton;
    TextView musicSwitchButton;

    boolean isSoundsOn = true;
    boolean isMusicOn = true;

    public SettingsScreen(MyGdxGame game) {
        this.game = game;
        batch = game.getBatch();
        camera = game.getCamera();

        isSoundsOn = game.getAudioManager().isSoundsOn();
        isMusicOn = game.getAudioManager().isMusicOn();

        backgroundView = new MovingBackgroundView(0, GameResources.BACKGROUND_IMG_PATH);
        blackout = new ImageView(90, 340, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        textView = new TextView(game.getBigWhiteFont(), 250, 1000, "Settings");
        returnButton = new ButtonView(280, 420, 160, 80, GameResources.SHORT_BUTTON_IMG_PATH, "return", game.getBlackFont());
        clearRecordsButton = new TextView(game.getWhiteFont(),  170, 580, "clear records");
        soundsSwitchButton = new TextView(game.getWhiteFont(), 170, 650, "sounds: " + (isSoundsOn ? "on" : "off"));
        musicSwitchButton = new TextView(game.getWhiteFont(), 170, 720, "music: " + (isMusicOn ? "on" : "off"));
    }

    @Override
    public void render(float delta) {
        handleInput();

        ScreenUtils.clear(1, 1, 1, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        backgroundView.draw(batch);
        blackout.draw(batch);
        textView.draw(batch);
        returnButton.draw(batch);
        clearRecordsButton.draw(batch);
        soundsSwitchButton.draw(batch);
        musicSwitchButton.draw(batch);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (returnButton.isHit(touch.x, touch.y))
                game.applySettings();
            else if (musicSwitchButton.isHit(touch.x, touch.y)) {
                isMusicOn = !isMusicOn;
                game.getAudioManager().setMusicOn(isMusicOn);
                musicSwitchButton.setText("music: " + (isMusicOn ? "on" : "off"));
                System.out.println(game.getAudioManager().getMusicFlag());
            }
            else if (soundsSwitchButton.isHit(touch.x, touch.y)) {
                isSoundsOn = !isSoundsOn;
                game.getAudioManager().setSoundsOn(isSoundsOn);
                soundsSwitchButton.setText("sounds: " + (isSoundsOn ? "on" : "off"));
            }
            else if (clearRecordsButton.isHit(touch.x, touch.y)) {
                MemoryManager.clearRecords();
            }
        }
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        blackout.dispose();
        textView.dispose();
        returnButton.dispose();
        clearRecordsButton.dispose();
        soundsSwitchButton.dispose();
        musicSwitchButton.dispose();
    }
}

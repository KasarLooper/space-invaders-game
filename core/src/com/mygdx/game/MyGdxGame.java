package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.managers.AudioManager;
import com.mygdx.game.managers.ContactManager;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.SettingsScreen;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	OrthographicCamera camera;
	GameScreen gameScreen;
	MainMenuScreen mainMenuScreen;
	SettingsScreen settingsScreen;
	World world;
	float accumulator = 0;
	BitmapFont whiteFont;
	BitmapFont bigWhiteFont;
	BitmapFont blackFont;
	AudioManager audioManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
		whiteFont = FontBuilder.generate(35, Color.WHITE, GameResources.FONT_PATH);
		blackFont = FontBuilder.generate(30, Color.BLACK, GameResources.FONT_PATH);
		bigWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.FONT_PATH);
		audioManager = new AudioManager(MemoryManager.loadMusicOn(), MemoryManager.loadSoundsOn());

		Box2D.init();
		world = new World(new Vector2(0f, 0f), true);
		world.setContactListener(new ContactManager());

		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingsScreen(this);

		setScreen(mainMenuScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += delta;
		if (accumulator >= GameSettings.STEP_TIME) {
			accumulator -= GameSettings.STEP_TIME;
			world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
		}
	}

	public void startGame() {
		setScreen(gameScreen);
	}

	public void openSettings() {
		setScreen(settingsScreen);
	}

	public void exit() {
		MemoryManager.saveDifficultLevel(mainMenuScreen.getDifficultLevel());
		Gdx.app.exit();
	}

	public void applySettings() {
		setScreen(mainMenuScreen);
		MemoryManager.saveMusicOn(audioManager.isMusicOn());
		MemoryManager.saveSoundsOn(audioManager.isSoundsOn());
	}

	public void returnToMainMenu() {
		screen.hide();
		setScreen(mainMenuScreen);
		gameScreen.restartGame();
	}

	public World getWorld() {
		return world;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public BitmapFont getWhiteFont() {
		return whiteFont;
	}

	public BitmapFont getBlackFont() {
		return blackFont;
	}

	public BitmapFont getBigWhiteFont() {
		return bigWhiteFont;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AudioManager getAudioManager() {
		return audioManager;
	}

	public int getDifficultLevel() {
		return mainMenuScreen.getDifficultLevel();
	}
}

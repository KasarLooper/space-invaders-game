package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GameState;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.UIObjects.ButtonView;
import com.mygdx.game.UIObjects.ImageView;
import com.mygdx.game.UIObjects.MovingBackgroundView;
import com.mygdx.game.UIObjects.RecordsListView;
import com.mygdx.game.UIObjects.TextView;
import com.mygdx.game.gameObjects.BossObject;
import com.mygdx.game.gameObjects.BulletObject;
import com.mygdx.game.UIObjects.LineView;
import com.mygdx.game.gameObjects.FlyDownObject;
import com.mygdx.game.gameObjects.GameObject;
import com.mygdx.game.gameObjects.GunObject;
import com.mygdx.game.gameObjects.MedicineObject;
import com.mygdx.game.gameObjects.ShipObject;
import com.mygdx.game.gameObjects.TrashObject;
import com.mygdx.game.managers.MemoryManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameScreen extends ScreenAdapter {
    int score = 0;
    boolean isBossAlive = false;
    BossObject boss;
    SpriteBatch batch;
    MyGdxGame game;
    GameSession session;
    OrthographicCamera camera;
    ShipObject ship;
    ArrayList<FlyDownObject> flyDownArray;
    ArrayList<BulletObject> bulletArray;

    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;
    LineView lineView;
    TextView scoreTextView;
    ButtonView playButtonView;

    ImageView fullBlackoutView;
    ButtonView continueButton;
    ButtonView homeButton;
    TextView pauseTextView;

    TextView recordsTextView;
    TextView levelTextView;
    RecordsListView tableRecords;
    ButtonView homeButton2;

    public GameScreen(MyGdxGame game) {
        this.game = game;
        session = new GameSession();
        batch = game.getBatch();
        camera = game.getCamera();
        ship = new ShipObject(GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH, game.getWorld(), GameSettings.SHIP_BIT);
        flyDownArray = new ArrayList<>();
        bulletArray = new ArrayList<>();

        backgroundView = new MovingBackgroundView(GameSettings.BACKGROUND_SPEED, GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        lineView = new LineView(1215, GameResources.LIFE_IMG_PATH);
        lineView.setHp(ship.getHp());
        scoreTextView = new TextView(game.getWhiteFont(), 50, 1215, "Score: 0");
        playButtonView = new ButtonView(605, 1200, 46, 54, GameResources.PLAY_BUTTON_IMG_PATH);

        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_ING_PATH);
        continueButton = new ButtonView(160, 650, 160, 80, GameResources.SHORT_BUTTON_IMG_PATH, "Continue", game.getBlackFont());
        homeButton = new ButtonView(380, 650, 160, 80, GameResources.SHORT_BUTTON_IMG_PATH, "Home", game.getBlackFont());
        pauseTextView = new TextView(game.getBigWhiteFont(), 280, 860, "Pause");

        recordsTextView = new TextView(game.getBigWhiteFont(), 210, 960, "Last records");
        levelTextView = new TextView(game.getWhiteFont(), 300, 880);
        tableRecords = new RecordsListView(game.getWhiteFont(), 800);
        homeButton2 = new ButtonView(280, 400, 160, 80, GameResources.SHORT_BUTTON_IMG_PATH, "Home", game.getBlackFont());
    }

    @Override
    public void show() {
        session.startGame();
        ship.initHP();
    }

    @Override
    public void render(float delta) {
        handleInput();
        if (session.getState() == GameState.PLAYING)
            updateObjects();
        draw();
    }

    private void updateObjects() {
        if (session.shouldSpawnTrash())
            flyDownArray.add(getFlyDownObject());

        if (ship.needToShoot()) {
            BulletObject bulletObject = new BulletObject(ship.getX(), ship.getY() + GameSettings.SHIP_HEIGHT / 2 + 50,
                    GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT, GameResources.BULLET_ING_PATH, game.getWorld(),
                    GameSettings.BULLET_BIT, false);
            bulletArray.add(bulletObject);
            game.getAudioManager().shoot();
        }

        if (isBossAlive && boss.needToShoot()) {
            BulletObject bulletObject = new BulletObject(boss.getX(), boss.getY() - GameSettings.SHIP_HEIGHT / 2 - 50,
                    GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT, GameResources.ENEMY_BULLET_IMG_PATH, game.getWorld(),
                    GameSettings.BULLET_BIT, true);
            bulletArray.add(bulletObject);
            game.getAudioManager().shoot();
        }

        if (isBossAlive) boss.move(ship.getX());

        if (!ship.isAlive()) {
            levelTextView.setText("Level: " + game.getDifficultLevel());
            session.endGame(score, game.getDifficultLevel());
            tableRecords.setRecords(MemoryManager.loadTableRecords(game.getDifficultLevel()));
        }

        updateFlyDownObjects();
        updateBullets();
        game.stepWorld();
        lineView.setHp(ship.getHp());
        backgroundView.move();
    }

    Random rd = new Random();
    int bound;

    private FlyDownObject getFlyDownObject() {
        if (!isBossAlive) bound = GameSettings.BOUND;
        int n = rd.nextInt(bound);
        switch (n) {
            case 0:
                return new MedicineObject(GameSettings.MEDICINE_WIDTH, GameSettings.MEDICINE_HEIGHT,
                        GameResources.MEDICINE_IMG_PATH, game.getWorld(), GameSettings.FLY_DOWN_OBJECT_BIT, ship.getX());
            case 1:
                return new GunObject(GameSettings.GUN_WIDTH, GameSettings.GUN_HEIGHT,
                        GameResources.GUN_IMG_PATH, game.getWorld(), GameSettings.FLY_DOWN_OBJECT_BIT, ship.getX());
            case 2: {
                bound = 2;
                isBossAlive = true;
                boss = new BossObject(GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT, GameResources.ENEMY_SHIP_IMG_PATH, game.getWorld(),
                        GameSettings.FLY_DOWN_OBJECT_BIT);
                return boss;
            }
            default: {
                return new TrashObject(GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                        GameResources.TRASH_IMG_PATH, game.getWorld(), GameSettings.FLY_DOWN_OBJECT_BIT);
            }
        }
    }

    private void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        batch.begin();

        backgroundView.draw(batch);
        topBlackoutView.draw(batch);
        lineView.draw(batch);
        scoreTextView.draw(batch);
        playButtonView.draw(batch);

        ship.draw(batch);
        for (FlyDownObject object : flyDownArray)
            object.draw(batch);
        for (BulletObject bullet : bulletArray)
            bullet.draw(batch);

        if (session.getState() == GameState.PAUSED) {
            fullBlackoutView.draw(batch);
            continueButton.draw(batch);
            homeButton.draw(batch);
            pauseTextView.draw(batch);
        }

        if (session.getState() == GameState.ENDED) {
            fullBlackoutView.draw(batch);
            recordsTextView.draw(batch);
            levelTextView.draw(batch);
            tableRecords.draw(batch);
            homeButton2.draw(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        ship.dispose();
        batch.dispose();
        for (FlyDownObject object : flyDownArray)
            object.dispose();
        for (BulletObject bullet : bulletArray)
            bullet.dispose();

        lineView.dispose();
        topBlackoutView.dispose();
        backgroundView.dispose();
        scoreTextView.dispose();
        playButtonView.dispose();

        fullBlackoutView.dispose();
        continueButton.dispose();
        homeButton.dispose();
        pauseTextView.dispose();

        recordsTextView.dispose();
        levelTextView.dispose();
        tableRecords.dispose();
        homeButton2.dispose();
    }

    private void handleInput() {
        if (session.isFreezeInput()) return;
        switch (session.getState()) {
            case PLAYING: {
                if (Gdx.input.isTouched()) {
                    Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                    if (playButtonView.isHit(touch.x, touch.y)) {
                        session.pauseGame();
                        break;
                    } else if (!topBlackoutView.isHit(touch.x, touch.y))
                        ship.move(touch.x, touch.y);
                }
                break;
            }
            case PAUSED: {
                if (Gdx.input.isTouched()) {
                    Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                    if (continueButton.isHit(touch.x, touch.y))
                        session.resumeGame();
                    if (homeButton.isHit(touch.x, touch.y)) {
                        session.endGame(score, game.getDifficultLevel());
                        game.returnToMainMenu();
                    }
                }
                break;
            }
            case ENDED: {
                if (Gdx.input.justTouched()) {
                    Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                    if (homeButton2.isHit(touch.x, touch.y)) {
                        game.returnToMainMenu();
                    }
                }
                break;
            }
        }
    }

    public void restartGame() {
        for (GameObject object : flyDownArray)
            game.getWorld().destroyBody(object.getBody());
        for (GameObject object : bulletArray)
            game.getWorld().destroyBody(object.getBody());
        game.getWorld().destroyBody(ship.getBody());

        ship = new ShipObject(GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH, game.getWorld(), GameSettings.SHIP_BIT);
        flyDownArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        session = new GameSession();
        score = 0;
        bound = GameSettings.BOUND;
        isBossAlive = false;
        scoreTextView.setText("Score: " + score);
    }

    private void updateFlyDownObjects() {
        Iterator<FlyDownObject> iterator = flyDownArray.iterator();
        while (iterator.hasNext()) {
            FlyDownObject current = iterator.next();
            if (current.hasToBeDestroyed()) {
                if (!current.isAlive()) {
                    score += current.getPoints();
                    scoreTextView.setText("Score: " + score);
                    game.getAudioManager().explode();
                    if (current instanceof BossObject) {
                        bound = GameSettings.BOUND;
                        isBossAlive = false;
                    }
                }
                game.getWorld().destroyBody(current.getBody());
                iterator.remove();
            }
        }
    }

    private void updateBullets() {
        Iterator<BulletObject> iterator = bulletArray.iterator();
        while (iterator.hasNext()) {
            BulletObject current = iterator.next();
            if (current.hasToBeDestroyed()) {
                game.getWorld().destroyBody(current.getBody());
                iterator.remove();
            }
        }
    }
}

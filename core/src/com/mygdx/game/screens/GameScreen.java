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
import com.mygdx.game.SpaceInvadersGame;
import com.mygdx.game.UIObjects.ButtonView;
import com.mygdx.game.UIObjects.ImageView;
import com.mygdx.game.UIObjects.MovingBackgroundView;
import com.mygdx.game.UIObjects.TextView;
import com.mygdx.game.gameObjects.BulletObject;
import com.mygdx.game.UIObjects.LineView;
import com.mygdx.game.gameObjects.ShipObject;
import com.mygdx.game.gameObjects.TrashObject;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    SpriteBatch batch;
    SpaceInvadersGame game;
    GameSession session;
    OrthographicCamera camera;
    ShipObject ship;
    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;

    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;
    LineView lineView;
    TextView scoreTextView;
    ButtonView playButtonView;

    public GameScreen(SpaceInvadersGame game) {
        this.game = game;
        session = new GameSession();
        batch = new SpriteBatch();
        camera = game.getCamera();
        ship = new ShipObject(GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH, game.getWorld(), GameSettings.SHIP_BIT);
        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();

        backgroundView = new MovingBackgroundView(GameSettings.BACKGROUND_SPEED, GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        lineView = new LineView(1215, GameResources.LIFE_IMG_PATH);
        lineView.setHp(ship.getHp());
        scoreTextView = new TextView(game.getFont(), 50, 1215, "Score: 0");
        playButtonView = new ButtonView(605, 1200, 46, 54, GameResources.PLAY_BUTTON_PATH);
    }

    @Override
    public void show() {
        session.startGame();
    }

    @Override
    public void render(float delta) {
        game.stepWorld();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        if (Gdx.input.isTouched()) {
            Vector3 vector3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            ship.move(vector3.x, vector3.y);
        }

        updateTrash();
        updateBullet();
        lineView.setHp(ship.getHp());

        batch.begin();
        backgroundView.draw(batch);
        topBlackoutView.draw(batch);
        lineView.draw(batch);
        scoreTextView.draw(batch);
        playButtonView.draw(batch);

        ship.draw(batch);
        for (TrashObject trash : trashArray)
            trash.draw(batch);
        for (BulletObject bullet : bulletArray)
            bullet.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        ship.dispose();
        batch.dispose();
        for (TrashObject trash : trashArray)
            trash.dispose();
        for (BulletObject bullet : bulletArray)
            bullet.dispose();

        lineView.dispose();
        topBlackoutView.dispose();
        backgroundView.dispose();
        scoreTextView.dispose();
        playButtonView.dispose();
    }

    private void updateTrash() {
        if (session.shouldSpawnTrash()) {
            TrashObject trashObject = new TrashObject(GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                    GameResources.TRASH_IMG_PATH, game.getWorld(), GameSettings.TRASH_BIT);
            trashArray.add(trashObject);
        }
        trashArray.removeIf(TrashObject::deleteIfNeed);
    }

    private void updateBullet() {
        if (ship.needToShoot()) {
            BulletObject bulletObject = new BulletObject(ship.getX(), ship.getY() + GameSettings.SHIP_HEIGHT / 2,
                    GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT, GameResources.BULLET_ING_PATH, game.getWorld(),
                    GameSettings.BULLET_BIT);
            bulletArray.add(bulletObject);
        }
        bulletArray.removeIf(BulletObject::deleteIfNeed);
    }
}

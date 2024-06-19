package com.mygdx.game.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.DifficultSettings;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class BossObject extends TrashObject implements AbleToUseHelpObjects {
    private static int paddingHorizontal = 30;
    private long lastShootTime;
    private long startShootFasterTime;
    private boolean isShootFaster;
    private int leftRedFrames;
    private int leftGreenFrames;
    private Texture red;
    private Texture green;

    public BossObject(int width, int height, String texturePath, World world, short cBits) {
        super(width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2, width, height, texturePath, world, cBits);
        body.setLinearVelocity(new Vector2(0, 0));
        body.setLinearDamping(10f);
        hasPoints = true;
        hp = DifficultSettings.getEnemyHP();
        points = 5;
        leftRedFrames = -1;
        leftGreenFrames = -1;
        red = new Texture(GameResources.ENEMY_SHIP_RED_IMG_PATH);
        green = new Texture(GameResources.ENEMY_SHIP_GREEN_IMG_PATH);
    }

    public boolean needToShoot() {
        long shootingCoolDown = DifficultSettings.getEnemyShootingCoolDown();
        if (TimeUtils.millis() - startShootFasterTime >= GameSettings.SHOOT_FASTER_TIME) isShootFaster = false;
        if (isShootFaster) shootingCoolDown /= 3;
        if (TimeUtils.millis() - lastShootTime >= shootingCoolDown) {
            lastShootTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    @Override
    public void draw(Batch batch) {
        if (leftRedFrames >= 0) batch.draw(red, getX() - width / 2f, getY() - height / 2f, width, height);
        else if (leftGreenFrames >= 0) batch.draw(green, getX() - width / 2f, getY() - height / 2f, width, height);
        else super.draw(batch);
    }

    public void updateTexture() {
        leftRedFrames--;
        leftGreenFrames--;
    }

    @Override
    public void hit(GameObject other) {
        super.hit(other);
        if (other instanceof BulletObject) {
            leftRedFrames = GameSettings.BACKLIGHT_FRAMES;
            leftGreenFrames = -1;
        }
    }

    private void backLightGreen() {
        leftGreenFrames = GameSettings.BACKLIGHT_FRAMES;
        leftRedFrames = -1;
    }

    @Override
    public void hill() {
        hp = Math.min(DifficultSettings.getMaxEnemyHP(), hp + 1);
        backLightGreen();
    }

    @Override
    public void shootFaster() {
        startShootFasterTime = TimeUtils.millis();
        isShootFaster = true;
        backLightGreen();
    }

    private static Random rd = new Random();
    private static int maxPadding = 100;

    public void move(int playerX) {
        if (rd.nextBoolean()) {
            int newX = playerX + (rd.nextInt(maxPadding) - maxPadding) * 2;
            int newY = (GameSettings.SCREEN_HEIGHT / 2 + 1180) / 2;
            body.applyForceToCenter(new Vector2((newX - getX()) * GameSettings.SHIP_FORCE_RATIO,
                    (newY - getY()) * GameSettings.SHIP_FORCE_RATIO) , true);
        }
    }
}

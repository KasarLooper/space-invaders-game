package com.mygdx.game.gameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.DifficultSettings;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class BossObject extends TrashObject implements AbleToUseHelpObjects {
    private static int paddingHorizontal = 30;
    private long lastShootTime;
    private long startShootFasterTime;
    private boolean isShootFaster;

    public BossObject(int width, int height, String texturePath, World world, short cBits) {
        super(width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2, width, height, texturePath, world, cBits);
        body.setLinearVelocity(new Vector2(0, 0));
        body.setLinearDamping(10f);
        hasPoints = true;
        hp = DifficultSettings.getEnemyHP();
        points = 5;
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
    public void hit(GameObject other) {
        super.hit(other);
    }

    @Override
    public void hill() {
        hp = Math.min(DifficultSettings.getMaxEnemyHP(), hp + 1);
    }

    @Override
    public void shootFaster() {
        startShootFasterTime = TimeUtils.millis();
        isShootFaster = true;
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

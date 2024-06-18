package com.mygdx.game.gameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.DifficultSettings;
import com.mygdx.game.GameSettings;

public class ShipObject extends GameObject implements AbleToUseHelpObjects {
    private int hp;
    private long lastShootTime;
    private long startShootFasterTime;
    private boolean isShootFaster;

    public ShipObject(int x, int y, int width, int height, String texturePath, World world, short cBits) {
        super(x, y, width, height, texturePath, world, cBits);
        body.setLinearDamping(10);
        isShootFaster = false;
    }

    public void initHP() {
        hp = DifficultSettings.getStartHP();
    }

    @Override
    public void hit(GameObject other) {
        if (other instanceof TrashObject || other instanceof BulletObject) hp--;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean needToShoot() {
        long shootingCoolDown = DifficultSettings.getShootingCoolDown();
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
        putInFrame();
        super.draw(batch);
    }

    public void move(float x, float y) {
        body.applyForceToCenter(new Vector2(
                        (x - getX()) * GameSettings.SHIP_FORCE_RATIO,
                        (y - getY()) * GameSettings.SHIP_FORCE_RATIO),
                true
        );
    }

    public int getHp() {
        return hp;
    }

    private void putInFrame() {
        if (getY() <= height / 2f)
            setY(height / 2);
        if (getY() > (GameSettings.SCREEN_HEIGHT - height) / 2f)
            setY((int) ((GameSettings.SCREEN_HEIGHT - height) / 2f));
        if (getX() <= -width / 2f)
            setX(GameSettings.SCREEN_WIDTH);
        if (getX() > (GameSettings.SCREEN_WIDTH + width / 2f))
            setX(0);
    }

    @Override
    public void hill() {
        hp = Math.min(hp + 1, DifficultSettings.getMaxHP());
    }

    @Override
    public void shootFaster() {
        startShootFasterTime = TimeUtils.millis();
        isShootFaster = true;
    }
}

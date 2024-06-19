package com.mygdx.game.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.DifficultSettings;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

public class ShipObject extends GameObject implements AbleToUseHelpObjects {
    private int hp;
    private long lastShootTime;
    private long startShootFasterTime;
    private boolean isShootFaster;
    private int leftRedFrames;
    private int leftGreenFrames;
    private Texture red;
    private Texture green;

    public ShipObject(int x, int y, int width, int height, String texturePath, World world, short cBits) {
        super(x, y, width, height, texturePath, world, cBits);
        body.setLinearDamping(10);
        isShootFaster = false;
        leftRedFrames = -1;
        leftGreenFrames = -1;
        red = new Texture(GameResources.SHIP_RED_IMG_PATH);
        green = new Texture(GameResources.SHIP_GREEN_IMG_PATH);
    }

    public void initHP() {
        hp = DifficultSettings.getStartHP();
    }

    @Override
    public void hit(GameObject other) {
        if (other instanceof TrashObject || other instanceof BulletObject) {
            hp--;
            leftRedFrames = GameSettings.BACKLIGHT_FRAMES;
            leftGreenFrames = -1;
        }
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
        if (leftRedFrames >= 0) batch.draw(red, getX() - width / 2f, getY() - height / 2f, width, height);
        else if (leftGreenFrames >= 0) batch.draw(green, getX() - width / 2f, getY() - height / 2f, width, height);
        else super.draw(batch);
    }

    public void updateTexture() {
        leftRedFrames--;
        leftGreenFrames--;
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

    private void backLightGreen() {
        leftGreenFrames = GameSettings.BACKLIGHT_FRAMES;
        leftRedFrames = -1;
    }

    @Override
    public void hill() {
        hp = Math.min(hp + 1, DifficultSettings.getMaxHP());
        backLightGreen();
    }

    @Override
    public void shootFaster() {
        startShootFasterTime = TimeUtils.millis();
        isShootFaster = true;
        backLightGreen();
    }
}

package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameSettings;

public class ShipObject extends GameObject {
    private int hp;
    private long lastShootTime;

    public ShipObject(int x, int y, int width, int height, String texturePath, World world, short cBits) {
        super(x, y, width, height, texturePath, world, cBits);
        body.setLinearDamping(10);
        hp = 3;
    }

    @Override
    public void hit() {
        hp--;
    }

    @Override
    public boolean deleteIfNeed() {
        return hp <= 0;
    }

    public boolean needToShoot() {
        if (TimeUtils.millis() - lastShootTime >= GameSettings.SHOOTING_COOL_DOWN) {
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
}

package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class ShipObject extends GameObject {
    public ShipObject(int x, int y, int width, int height, String texturePath, World world) {
        super(x, y, width, height, texturePath, world);
    }

    @Override
    public void draw(Batch batch) {
        putInFrame();
        super.draw(batch);
    }

    private void putInFrame() {
        if (getY() <= height / 2f)
            setY(height / 2);
        if (getY() > (GameSettings.SCREEN_HEIGHT - height) / 2)
            setY((GameSettings.SCREEN_HEIGHT - height) / 2);
        if (getX() <= -width / 2f)
            setX(GameSettings.SCREEN_WIDTH);
        if (getX() > (GameSettings.SCREEN_WIDTH + width / 2f))
            setX(0);
    }

    public void move(float x, float y) {
        body.applyForceToCenter(new Vector2((x - getX()) * GameSettings.SHIP_FORCE_RATIO,
                (y - getY()) * GameSettings.SHIP_FORCE_RATIO), true);
    }
}

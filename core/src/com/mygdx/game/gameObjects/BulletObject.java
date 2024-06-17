package com.mygdx.game.gameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DifficultSettings;
import com.mygdx.game.GameSettings;

public class BulletObject extends GameObject {
    private boolean wasHit;

    public BulletObject(int x, int y, int width, int height, String texturePath, World world, short cBits) {
        super(x, y, width, height, texturePath, world, cBits);
        body.setLinearVelocity(new Vector2(0, DifficultSettings.getBulletVelocity()));
        wasHit = false;
    }

    @Override
    public void hit(GameObject other) {
        wasHit = true;
    }

    public boolean hasToBeDestroyed() {
        return getY() - height / 2 > GameSettings.SCREEN_HEIGHT || wasHit;
    }
}

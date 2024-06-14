package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class BulletObject extends GameObject {
    private boolean wasHit;

    public BulletObject(int x, int y, int width, int height, String texturePath, World world, short cBits) {
        super(x, y, width, height, texturePath, world, cBits);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
        wasHit = false;
    }

    @Override
    public void hit() {
        wasHit = true;
    }

    public boolean deleteIfNeed() {
        boolean isNeed = getY() - height / 2 > GameSettings.SCREEN_HEIGHT || wasHit;
        if (isNeed)
            body.destroyFixture(body.getFixtureList().get(0));
        return isNeed;
    }
}

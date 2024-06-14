package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class BulletObject extends GameObject {
    public BulletObject(int x, int y, int width, int height, String texturePath, World world) {
        super(x, y, width, height, texturePath, world);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
    }

    public boolean hasToBeDestroyed() {
        return getY() - height / 2 > GameSettings.SCREEN_HEIGHT;
    }
}

package com.mygdx.game.gameObjects;

import com.badlogic.gdx.physics.box2d.World;

public class TrashObject extends FlyDownObject {
    public TrashObject(int width, int height, String texturePath, World world, short cBits) {
        super(width, height, texturePath, world, cBits);
    }

    public TrashObject(int x, int y, int width, int height, String texturePath, World world, short cBits) {
        super(x, y, width, height, texturePath, world, cBits);
    }

    @Override
    public void hit(GameObject other) {
        if (other instanceof ShipObject) {
            hp = 0;
            hasPoints = false;
        }
        else if (other instanceof BulletObject) hp--;
    }
}

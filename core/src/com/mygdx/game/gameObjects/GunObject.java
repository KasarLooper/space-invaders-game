package com.mygdx.game.gameObjects;

import com.badlogic.gdx.physics.box2d.World;

public class GunObject extends PlayerHelpObject {
    public GunObject(int width, int height, String texturePath, World world, short cBits, int playerX) {
        super(width, height, texturePath, world, cBits, playerX);
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public void hit(GameObject other) {
        hp--;
        if (other instanceof AbleToUseHelpObjects) {
            AbleToUseHelpObjects shootable = (AbleToUseHelpObjects) other;
            shootable.shootFaster();
            isHelped = true;
        }
    }
}

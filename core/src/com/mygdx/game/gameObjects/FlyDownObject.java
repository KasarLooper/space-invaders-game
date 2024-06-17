package com.mygdx.game.gameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.DifficultSettings;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class FlyDownObject extends GameObject {
    static Random random = new Random();
    private static int paddingHorizontal = 30;
    protected int hp;
    protected int points;
    protected boolean hasPoints;

    public FlyDownObject(int width, int height, String texturePath, World world, short cBits) {
        super(width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2, width, height, texturePath, world, cBits);
        body.setLinearVelocity(new Vector2(0, -DifficultSettings.getTrashVelocity()));
        hasPoints = true;
        hp = 1;
        points = 1;
    }

    protected FlyDownObject(int x, int y, int width, int height, String texturePath, World world, short cBits) {
        super(x, y, width, height, texturePath, world, cBits);
        body.setLinearVelocity(new Vector2(0, -DifficultSettings.getTrashVelocity()));
        hasPoints = false;
        hp = 1;
        points = 0;
    }

    public int getPoints() {
        return hasPoints ? points : 0;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean hasToBeDestroyed() {
        return hp <= 0 || getY() < -height / 2;
    }
}

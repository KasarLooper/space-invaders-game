package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject {
    static Random random = new Random();
    static int paddingHorizontal = 30;
    private int hp;

    public TrashObject(int width, int height, String texturePath, World world, short cBits) {
        super(width / 2 + paddingHorizontal + random.nextInt(GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width),
                GameSettings.SCREEN_HEIGHT + height / 2, width, height, texturePath, world, cBits);
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        hp = 1;
    }

    @Override
    public void hit() {
        hp--;
    }

    @Override
    public boolean deleteIfNeed() {
        boolean isNeed = hp <= 0 || getY() < -height / 2;
        if (isNeed) {
            body.destroyFixture(body.getFixtureList().get(0));
        }
        return isNeed;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}

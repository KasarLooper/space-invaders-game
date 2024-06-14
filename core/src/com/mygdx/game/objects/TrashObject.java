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
    public TrashObject(int width, int height, String texturePath, World world) {
        super(width / 2 + paddingHorizontal + random.nextInt(GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width),
                GameSettings.SCREEN_HEIGHT + height / 2, width, height, texturePath, world);
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public boolean isOutFrame() {
        return getY() < -height / 2;
    }
}

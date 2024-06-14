package com.mygdx.game.UIObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class MovingBackgroundView extends View {
    int speed;
    Texture texture;
    int y1, y2;

    public MovingBackgroundView(int speed, String pathToTexture) {
        super(0f, 0f);
        this.speed = speed;
        texture = new Texture(pathToTexture);
        y1 = 0;
        y2 = GameSettings.SCREEN_HEIGHT;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, 0, y1);
        batch.draw(texture, 0, y2);
        move();
    }

    public void move() {
        y1 -= speed;
        y2 -= speed;

        if (y1 < -GameSettings.SCREEN_HEIGHT) y1 = y2 + GameSettings.SCREEN_HEIGHT;
        if (y2 < -GameSettings.SCREEN_HEIGHT) y2 = y1 + GameSettings.SCREEN_HEIGHT;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}

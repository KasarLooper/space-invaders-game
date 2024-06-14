package com.mygdx.game.UIObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;
import com.mygdx.game.UIObjects.View;

public class LineView extends View {
    Texture texture;
    int livePadding;
    int hp;

    public LineView(float y, String imagePath) {
        super(GameSettings.SCREEN_WIDTH / 2f, y);
        texture = new Texture(imagePath);
        livePadding = 60;
    }

    public void setHp(int hp) {
        this.hp = hp;
        x = GameSettings.SCREEN_WIDTH / 2f - width * (hp / 2f) - livePadding * ((hp - 1) / 2f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (int i = 0; i < hp; i++)
            batch.draw(texture, x + (livePadding + width) * i, y);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}

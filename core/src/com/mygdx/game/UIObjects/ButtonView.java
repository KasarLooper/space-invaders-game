package com.mygdx.game.UIObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonView extends View {
    BitmapFont font;
    String text;
    Texture texture;
    float textX, textY;

    public ButtonView(float x, float y, float width, float height, String texturePath) {
        super(x, y);
        this.width = width;
        this.height = height;
        texture = new Texture(texturePath);
    }

    public ButtonView(float x, float y, float width, float height, String texturePath, String text, BitmapFont font) {
        this(x, y, width, height, texturePath);
        this.text = text;
        this.font = font;

        GlyphLayout gl = new GlyphLayout(font, text);
        textX = x + (width - gl.width) / 2;
        textY = y + (height + gl.height) / 2;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
        if (font != null) {
            //
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
        if (font != null)
            font.dispose();
    }
}

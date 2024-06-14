package com.mygdx.game.UIObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextView extends View {
    private BitmapFont font;
    private String text;

    public TextView(BitmapFont font, float x, float y) {
        super(x, y);
        this.font = font;
    }

    public TextView(BitmapFont font, int x, int y, String text) {
        this(font, x, y);
        this.text = text;

        GlyphLayout layout = new GlyphLayout(font, text);
        width = layout.width;
        height = layout.height;
    }

    public void setText(String text) {
        this.text = text;

        GlyphLayout gl = new GlyphLayout(font, text);
        width = gl.width;
        height = gl.height;
    }

    @Override
    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y + height);
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}

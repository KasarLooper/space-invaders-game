package com.mygdx.game.UIObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.GameSettings;

import java.util.ArrayList;

public class RecordsListView extends TextView {
    public RecordsListView(BitmapFont font, float y) {
        super(font, 0, y);
    }

    public void setRecords(ArrayList<Integer> table) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= table.size(); i++)
            builder.append(String.format("%d. - %d\n", i, table.get(i - 1)));
        text = builder.toString();

        GlyphLayout gl = new GlyphLayout(font, text);
        x = (GameSettings.SCREEN_WIDTH - gl.width) / 2;
    }
}

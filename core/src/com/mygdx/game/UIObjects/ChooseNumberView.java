package com.mygdx.game.UIObjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;

public class ChooseNumberView extends View {
    private TextView numberView;
    private ButtonView plusView;
    private ButtonView minusView;
    private int number;

    public ChooseNumberView(float x, float y, int defaultNumber, BitmapFont font) {
        super(x, y);
        number = defaultNumber;
        minusView = new ButtonView(x, y, 40, 40, GameResources.MINUS_IMG_PATH);
        numberView = new TextView(font,  x + minusView.width + 20, y, String.valueOf(defaultNumber));
        plusView = new ButtonView(x + minusView.width + numberView.width + 30, y - 15, 75, 75, GameResources.PLUS_IMG_PATH);
    }

    @Override
    public void draw(SpriteBatch batch) {
        numberView.draw(batch);
        plusView.draw(batch);
        minusView.draw(batch);
    }

    @Override
    public void dispose() {
        numberView.dispose();
        plusView.dispose();
        minusView.dispose();
    }

    public int increase() {
        number++;
        number = Math.min(number, 5);
        numberView.setText(String.valueOf(number));
        return number;
    }

    public int decrease() {
        number--;
        number = Math.max(number, 1);
        numberView.setText(String.valueOf(number));
        return number;
    }

    public boolean isPlusHit(float tx, float ty) {
        return plusView.isHit(tx, ty);
    }

    public boolean isMinusHit(float tx, float ty) {
        return ty > minusView.y - 50f && ty < minusView.y + 50f && minusView.isHit(tx, ty);
    }
}

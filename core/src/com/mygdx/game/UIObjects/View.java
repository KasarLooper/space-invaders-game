package com.mygdx.game.UIObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class View implements Disposable {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public View(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHit(float tx, float ty) {
        return false;
    }

    public void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
    }
}

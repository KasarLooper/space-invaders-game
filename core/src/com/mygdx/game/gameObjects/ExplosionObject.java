package com.mygdx.game.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;

import java.util.ArrayList;
import java.util.Iterator;

public class ExplosionObject {
    private Texture[] frames;
    private ArrayList<ExplosionInfo> explosionsInfo;

    public ExplosionObject() {
        frames = new Texture[GameSettings.NUMBER_OF_EXPLOSION_FRAMES];
        for (int i = 0; i < GameSettings.NUMBER_OF_EXPLOSION_FRAMES; i++)
            frames[i] = new Texture(String.format(GameResources.EXPLOSION_FRAME_PATH_PATTERN, i));
        explosionsInfo = new ArrayList<>();
    }

    public void explode(int x, int y, int width, int height) {
        explosionsInfo.add(new ExplosionInfo(frames, x, y, width, height));
    }

    public boolean isEnd() {
        return explosionsInfo.isEmpty();
    }

    public void draw(SpriteBatch batch) {
        for (ExplosionInfo current : explosionsInfo)
            current.draw(batch);
    }

    public void update() {
        explosionsInfo = new ArrayList<>();
    }

    public void nextFrame() {
        Iterator<ExplosionInfo> iterator = explosionsInfo.iterator();
        while (iterator.hasNext()) {
            ExplosionInfo current = iterator.next();
            if (current.nextFrame())
                iterator.remove();
        }
    }

    private static class ExplosionInfo {
        public Texture[] frames;
        public int currentFrame;
        public int x;
        public int y;
        public int width;
        public int height;

        public ExplosionInfo(Texture[] frames, int x, int y, int width, int height) {
            this.frames = frames;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            currentFrame = GameSettings.FRAME_DURATION * GameSettings.NUMBER_OF_EXPLOSION_FRAMES - 1;
        }

        public void draw(SpriteBatch batch) {
            batch.draw(frames[currentFrame / GameSettings.FRAME_DURATION], x, y, width, height);
        }

        public boolean nextFrame() {
            currentFrame--;
            return currentFrame < 0;
        }
    }
}

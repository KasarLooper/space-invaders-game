package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameResources;

public class AudioManager {
    private Music backgroundMusic;
    private Sound shootSound;
    private Sound explosionSound;
    private boolean isMusicOn;
    private boolean isSoundsOn;

    public AudioManager(boolean isMusicOn, boolean isSoundsOn) {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));

        this.isMusicOn = isMusicOn;
        this.isSoundsOn = isSoundsOn;

        backgroundMusic.setVolume(0.2f);
        backgroundMusic.setLooping(true);

        if (isMusicOn)
            backgroundMusic.play();
    }

    public void shoot() {
        if (isSoundsOn) shootSound.play(0.2f);
    }

    public void explode() {
        if (isSoundsOn) explosionSound.play(0.05f);
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

    public boolean isSoundsOn() {
        return isSoundsOn;
    }

    public void setMusicOn(boolean isMusicOn) {
        if (isMusicOn) backgroundMusic.play();
        else backgroundMusic.stop();
        this.isMusicOn = isMusicOn;
    }

    public void setMusicFlag(float pos) {
        backgroundMusic.setPosition(pos);
    }

    public void setSoundsOn(boolean isSoundsOn) {
        this.isSoundsOn = isSoundsOn;
    }

    public float getMusicFlag() {
        return backgroundMusic.getPosition();
    }
}

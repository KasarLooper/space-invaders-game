package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {
    long sessionStartTime;
    long nextTrashSpawnTime;

    public void startGame() {
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = (long) (sessionStartTime + GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN * getTrashPeriodCoolDown());
    }

    public boolean shouldSpawnTrash() {
        if (TimeUtils.millis() >= nextTrashSpawnTime) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }

    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }
}

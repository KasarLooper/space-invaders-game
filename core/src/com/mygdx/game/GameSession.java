package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.manager.MemoryManager;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class GameSession {
    long sessionStartTime;
    long nextTrashSpawnTime;
    GameState state;
    long sessionPauseTime;
    long sessionResumeTime;

    public void startGame() {
        state = GameState.PLAYING;
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = sessionStartTime + GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN;
    }

    public boolean isFreezeInput() {
        return TimeUtils.millis() - sessionStartTime <= GameSettings.FREEZE_HANDLE_INPUT_TIME || TimeUtils.millis() - sessionResumeTime <= GameSettings.FREEZE_HANDLE_INPUT_TIME;
    }

    public void pauseGame() {
        state = GameState.PAUSED;
        sessionPauseTime = TimeUtils.millis();
    }

    public void resumeGame() {
        state = GameState.PLAYING;
        sessionResumeTime = TimeUtils.millis();
        sessionStartTime += TimeUtils.millis() - sessionPauseTime;
        nextTrashSpawnTime += TimeUtils.millis() - sessionPauseTime;
    }

    public void endGame(int score) {
        state = GameState.ENDED;
        ArrayList<Integer> table = MemoryManager.loadTableRecords();
        boolean isAdd = false;
        if (score == 0) {
            isAdd = true;
        } else {
            for (int i = 0; i < table.size(); i++) {
                if (score > table.get(i))
                    table.add(i, score);
                if (score >= table.get(i)) {
                    isAdd = true;
                    break;
                }
            }
        }
        if (!isAdd) table.add(score);

        while (table.size() > 5)
            table.remove(table.size() - 1);
        MemoryManager.saveTableRecords(table);
    }

    public boolean shouldSpawnTrash() {
        if (TimeUtils.millis() >= nextTrashSpawnTime) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }

    public GameState getState() {
        return state;
    }

    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }
}

package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import java.util.ArrayList;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static boolean loadMusicOn() {
        return !preferences.contains("music") || preferences.getBoolean("music");
    }

    public static boolean loadSoundsOn() {
        return !preferences.contains("sounds") || preferences.getBoolean("sounds");
    }

    public static void saveMusicOn(boolean isOn) {
        preferences.putBoolean("music", isOn);
        preferences.flush();
    }

    public static void saveSoundsOn(boolean isOn) {
        preferences.putBoolean("sounds", isOn);
        preferences.flush();
    }

    public static void saveTableRecords(ArrayList<Integer> table, int level) {
        Json json = new Json();
        String tableString = json.toJson(table);
        preferences.putString("table" + level, tableString);
        preferences.flush();
    }

    public static void clearRecords() {
        for (int i = 1; i <= 5; i++)
            saveTableRecords(new ArrayList<>(), i);
    }

    public static ArrayList<Integer> loadTableRecords(int level) {
        if (!preferences.contains("table")) return new ArrayList<>();
        Json json = new Json();
        String tableString = preferences.getString("table" + level);
        if (tableString.equals("")) return new ArrayList<>();
        return json.fromJson(ArrayList.class, tableString);
    }

    public static int loadDifficultLevel() {
        if (!preferences.contains("level")) return 1;
        return preferences.getInteger("level");
    }

    public static void saveDifficultLevel(int level) {
        preferences.putInteger("level", level);
        preferences.flush();
    }
}

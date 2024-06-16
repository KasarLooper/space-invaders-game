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

    public static void saveTableRecords(ArrayList<Integer> table) {
        Json json = new Json();
        String tableString = json.toJson(table);
        preferences.putString("table", tableString);
        preferences.flush();
    }

    public static void clearRecords() {
        saveTableRecords(new ArrayList<>());
    }

    public static ArrayList<Integer> loadTableRecords() {
        if (!preferences.contains("table")) return new ArrayList<>();
        Json json = new Json();
        String tableString = preferences.getString("table");
        if (tableString.equals("")) return new ArrayList<>();
        return json.fromJson(ArrayList.class, tableString);
    }
}

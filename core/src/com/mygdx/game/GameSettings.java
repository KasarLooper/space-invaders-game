package com.mygdx.game;

public class GameSettings {
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;
    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.005f;
    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;
    public static final float SHIP_FORCE_RATIO = 1000f;
    public static final float TRASH_VELOCITY = 10f;
    public static final long STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000;

    private GameSettings() {}
}

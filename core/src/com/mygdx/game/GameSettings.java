package com.mygdx.game;

public class GameSettings {
    // Category bits
    public static final short FLY_DOWN_OBJECT_BIT = 1;
    public static final short SHIP_BIT = 2;
    public static final short BULLET_BIT = 4;
    // Screen size
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;
    // Simulation settings
    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.005f;
    // Object size
    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;
    public static final int TRASH_WIDTH = 140;
    public static final int TRASH_HEIGHT = 100;
    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 45;
    public static final int MEDICINE_WIDTH = 100;
    public static final int MEDICINE_HEIGHT = 100;
    public static final int GUN_WIDTH = 120;
    public static final int GUN_HEIGHT = 60;
    // Speed settings
    public static final float SHIP_FORCE_RATIO = 0.023f;
    public static final float TRASH_VELOCITY = 5f;
    public static final int BULLET_VELOCITY = 8;
    public static final int BACKGROUND_SPEED = 4;
    // Timing settings
    public static final long SHOOTING_COOL_DOWN = 400;
    public static final long STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000;
    public static final int FREEZE_HANDLE_INPUT_TIME = 100;
    // Player help settings
    public static final int MAX_HP = 5;
    public static final int SHOOT_FASTER_TIME = 5_000;

    private GameSettings() {}
}

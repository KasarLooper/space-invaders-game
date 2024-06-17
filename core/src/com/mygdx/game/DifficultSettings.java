package com.mygdx.game;

public class DifficultSettings {
    private static int difficultLevel;

    public static void setDifficultLevel(int difficultLevel) {
        if (difficultLevel < 1 || difficultLevel > 5) throw new IllegalArgumentException("Difficult level must be from 1 to 5. Invalid difficult level: " + difficultLevel);
        DifficultSettings.difficultLevel = difficultLevel;
    }

    public static float getTrashVelocity() {
        return 3f + 1.5f * (difficultLevel - 1);
    }

    public static float getBulletVelocity() {
        return 12f / difficultLevel;
    }

    public static long getShootingCoolDown() {
        return 100L * difficultLevel;
    }

    public static long getStartingTrashAppearanceCoolDown() {
        return 3000 / difficultLevel;
    }

    public static int getStartHP() {
        return 5 - difficultLevel + 1;
    }

    public static int getMaxHP() {
        return difficultLevel < 3 ? 5 : getStartHP() + 1;
    }
}

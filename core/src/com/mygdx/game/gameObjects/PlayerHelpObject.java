package com.mygdx.game.gameObjects;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class PlayerHelpObject extends FlyDownObject {
    private static int paddingPlayer = 75;
    private static int paddingHorizontal = 75;

    public PlayerHelpObject(int width, int height, String texturePath, World world, short cBits, int playerX) {
        super(getRandomX(playerX), GameSettings.SCREEN_HEIGHT + height / 2, width, height, texturePath, world, cBits);
    }

    private static int getRandomX(int playerX) {
        int left, right;
        boolean isLeftToPlayer = random.nextBoolean();
        if (playerX - paddingPlayer <= paddingHorizontal) {
            left = Math.max(playerX + paddingPlayer, paddingHorizontal);
            right = GameSettings.SCREEN_WIDTH - paddingHorizontal;
        } else if (playerX + paddingPlayer >= GameSettings.SCREEN_WIDTH - paddingPlayer) {
            left = paddingHorizontal;
            right = Math.min(GameSettings.SCREEN_WIDTH - paddingHorizontal, playerX - paddingPlayer);
        } else if (isLeftToPlayer) {
            left = paddingHorizontal;
            right = playerX - paddingPlayer;
        } else {
            left = playerX + paddingPlayer;
            right = GameSettings.SCREEN_WIDTH - paddingHorizontal;
        }
        return left + random.nextInt(right - left);
    }
}

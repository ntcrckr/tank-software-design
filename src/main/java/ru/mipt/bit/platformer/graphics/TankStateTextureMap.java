package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.GameObjectState;
import ru.mipt.bit.platformer.model.tank.TankState;
import ru.mipt.bit.platformer.util.GameEntityType;

import java.util.HashMap;
import java.util.Map;

public class TankStateTextureMap {
    private static final Map<GameObjectState, String> playerTankTextureMap = new HashMap<>();
    private static final Map<GameObjectState, String> enemyTankTextureMap = new HashMap<>();

    static {
        playerTankTextureMap.put(TankState.OK, "images/tank_blue.png");
        playerTankTextureMap.put(TankState.MEDIUM_DAMAGE, "images/tank_blue_medium.png");
        playerTankTextureMap.put(TankState.HEAVY_DAMAGE, "images/tank_blue_heavy.png");
        enemyTankTextureMap.put(TankState.OK, "images/tank_red.png");
        enemyTankTextureMap.put(TankState.MEDIUM_DAMAGE, "images/tank_red_medium.png");
        enemyTankTextureMap.put(TankState.HEAVY_DAMAGE, "images/tank_red_heavy.png");
    }

    public static String getTexturePath(GameEntityType gameObjectType, GameObjectState gameObjectState) {
        return switch (gameObjectType) {
            case PLAYER_TANK -> playerTankTextureMap.get(gameObjectState);
            case ENEMY_TANK -> enemyTankTextureMap.get(gameObjectState);
            default -> null;
        };
    }
}

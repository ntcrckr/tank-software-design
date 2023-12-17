package ru.mipt.bit.platformer.util;

import java.util.HashMap;
import java.util.Map;

import static ru.mipt.bit.platformer.util.GameEntityType.*;

public class AssetMappings {
    public static final Map<GameEntityType, String> graphicsPathMap = new HashMap<>();

    static {
        graphicsPathMap.put(PLAYER_TANK, "images/tank_blue.png");
        graphicsPathMap.put(ENEMY_TANK, "images/tank_red.png");
        graphicsPathMap.put(TREE, "images/greenTree.png");
        graphicsPathMap.put(BULLET, "images/bullet.png");
    }
}

package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.tank.TankBullet;

import static ru.mipt.bit.platformer.util.GameObjectType.*;

public class DefaultGameObjectInitMap implements GameObjectInitMap {
    @Override
    public GameObject getGameObject(GameObjectType gameObjectType, Coordinates coordinates) {
        return switch (gameObjectType) {
            case PLAYER_TANK -> new Tank(
                    coordinates,
                    Direction.RIGHT,
                    0.4f,
                    TankBullet.class,
                    1f,
                    0.5f,
                    20f,
                    100f,
                    PLAYER_TANK
            );
            case ENEMY_TANK -> new Tank(
                    coordinates,
                    Direction.DOWN,
                    0.6f,
                    TankBullet.class,
                    1f,
                    0.5f,
                    20f,
                    100f,
                    ENEMY_TANK
            );
            case TREE -> new Obstacle(coordinates, TREE);
            case BULLET -> throw new RuntimeException();
        };
    }
}

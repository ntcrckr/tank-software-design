package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.util.GameObjectType;

public interface GameObject extends GameEntity {
    Coordinates getCoordinates();

    Direction getDirection();

    GameObjectType getGameObjectType();
}

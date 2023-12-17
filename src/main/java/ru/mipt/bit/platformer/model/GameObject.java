package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

public interface GameObject extends GameEntity {
    Coordinates getCoordinates();

    Direction getDirection();
}

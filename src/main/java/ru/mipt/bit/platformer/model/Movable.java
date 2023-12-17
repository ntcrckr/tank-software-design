package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.basics.Coordinates;

public interface Movable extends GameObject {
    boolean blocking();

    Coordinates getDestinationCoordinates();

    float getMovementProgress();

    boolean isMoving();
}

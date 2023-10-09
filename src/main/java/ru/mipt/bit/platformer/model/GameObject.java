package ru.mipt.bit.platformer.model;

public interface GameObject {
    Coordinates getCoordinates();
    Coordinates getDestinationCoordinates();
    float getRotation();
    float getMovementProgress();
}

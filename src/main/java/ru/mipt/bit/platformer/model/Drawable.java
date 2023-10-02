package ru.mipt.bit.platformer.model;

public interface Drawable {
    Coordinates getCoordinates();
    Coordinates getDestinationCoordinates();
    float getRotation();
    float getMovementProgress();
}

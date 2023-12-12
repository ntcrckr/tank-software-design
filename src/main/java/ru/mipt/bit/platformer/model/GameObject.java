package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.basics.Coordinates;

public interface GameObject {

    Coordinates getCoordinates();

    float getRotation();

    void updateState(float deltaTime);
}

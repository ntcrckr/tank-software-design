package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.basics.Coordinates;

import java.util.List;

public interface GameObject {

    List<Coordinates> getCoordinates();

    float getRotation();

    void updateState(float deltaTime);
}

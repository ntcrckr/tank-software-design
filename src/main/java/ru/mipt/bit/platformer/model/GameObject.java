package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.basics.Coordinates;

public interface GameObject {

    Coordinates getCoordinates();

    Coordinates getDestinationCoordinates();

    float getRotation();

    void updateState(float deltaTime);

    void apply(Action action);
}

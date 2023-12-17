package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

public interface GameObject {

    Coordinates getCoordinates();

    Coordinates getDestinationCoordinates();

    Direction getDirection();

    void updateState(float deltaTime);

    void apply(Action action);
}

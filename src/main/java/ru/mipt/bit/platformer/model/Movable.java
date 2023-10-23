package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

public interface Movable extends GameObject {

    Coordinates getDestinationCoordinates();

    float getMovementProgress();

    boolean isMoving();

    void startMovement(Direction direction);

    void changeDirection(Direction direction);

    void apply(MoveAction moveAction);

    Movable afterAction(MoveAction moveAction);

    void updateState(float deltaTime);
}

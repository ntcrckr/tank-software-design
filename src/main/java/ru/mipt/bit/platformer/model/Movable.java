package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.MoveAction;

public interface Movable extends GameObject {

    boolean isMoving();

    void startMovement(Direction direction);

    void changeDirection(Direction direction);

    void apply(MoveAction moveAction);

    Movable afterAction(MoveAction moveAction);

    void updateState(float deltaTime);
}

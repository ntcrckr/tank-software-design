package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.MoveAction;

public interface Controllable extends GameObject {
    void apply(MoveAction moveAction);

    Controllable afterAction(MoveAction moveAction);

    void updateState(float deltaTime);
}

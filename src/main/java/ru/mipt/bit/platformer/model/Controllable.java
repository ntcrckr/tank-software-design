package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.Action;

public interface Controllable extends GameObject {
    void apply(Action action);

    Controllable afterAction(Action action);

    void updateState(float deltaTime);
}

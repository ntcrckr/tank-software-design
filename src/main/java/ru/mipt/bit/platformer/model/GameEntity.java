package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;

public interface GameEntity {
    Action apply(Action action);

    void updateState(float deltaTime);
}

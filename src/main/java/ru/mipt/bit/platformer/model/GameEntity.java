package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.util.GameEntityType;

public interface GameEntity {
    Action apply(Action action);

    void updateState(float deltaTime);

    GameEntityType getGameObjectType();
}

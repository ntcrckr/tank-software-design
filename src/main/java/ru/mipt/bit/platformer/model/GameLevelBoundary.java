package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.util.GameEntityType;

public class GameLevelBoundary implements GameObject {
    @Override
    public Action apply(Action action) {
        return null;
    }

    @Override
    public void updateState(float deltaTime) {
    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public GameEntityType getGameObjectType() {
        return null;
    }
}

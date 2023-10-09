package ru.mipt.bit.platformer.model.actions;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.actions.Action;

public enum MoveAction implements Action {
    RIGHT(Direction.RIGHT),
    UP(Direction.UP),
    LEFT(Direction.LEFT),
    DOWN(Direction.DOWN);

    private final Direction direction;

    MoveAction(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}

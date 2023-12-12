package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.basics.Direction;

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

package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

public class Obstacle implements GameObject {
    private final Coordinates coordinates;
    private final Direction direction;

    public Obstacle(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.direction = Direction.RIGHT;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void updateState(float deltaTime) {
        // does nothing for now
    }

    @Override
    public Action apply(Action action) {
        return null;
    }
}

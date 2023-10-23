package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

import java.util.List;

public class Obstacle implements GameObject {
    private final List<Coordinates> coordinates;
    private final Direction direction;

    public Obstacle(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
        this.direction = Direction.RIGHT;
    }

    @Override
    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }

    @Override
    public void updateState(float deltaTime) {
        // does nothing for now
    }
}

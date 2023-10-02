package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle implements Drawable {
    private final Coordinates coordinates;
    private final Direction direction;

    public Obstacle(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.direction = Direction.RIGHT;
    }

    public Obstacle(Coordinates coordinates, Direction direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return direction.getRotation();
    }
}

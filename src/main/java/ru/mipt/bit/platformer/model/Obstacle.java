package ru.mipt.bit.platformer.model;

public class Obstacle implements Drawable {
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
    public Coordinates getDestinationCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return direction.getRotation();
    }

    @Override
    public float getMovementProgress() {
        return 1f;
    }
}

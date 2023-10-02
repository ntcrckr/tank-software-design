package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.Action;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements Drawable, Controllable {
    private Coordinates coordinates;
    private Coordinates destinationCoordinates;
    private float movementProgress;
    private Direction direction;
    private final float movementSpeed;

    public Tank(Coordinates coordinates, Direction direction, float movementSpeed) {
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.movementProgress = 1f;
        this.direction = direction;
        this.movementSpeed = movementSpeed;
    }

    public boolean isMoving() {
        return !isEqual(movementProgress, 1f);
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void startMovement(Direction direction) {
        destinationCoordinates = direction.apply(coordinates);
        movementProgress = 0f;
        changeDirection(direction);
    }

    public Coordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f)) {
            coordinates = destinationCoordinates;
        }
    }

    public float getRotation() {
        return direction.getRotation();
    }

    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }

    @Override
    public void apply(Action action) {
        if (action == null) {
            return;
        }
        switch (action) {
            case MOVE_RIGHT:
                startMovement(Direction.RIGHT);
                break;
            case MOVE_UP:
                startMovement(Direction.UP);
                break;
            case MOVE_LEFT:
                startMovement(Direction.LEFT);
                break;
            case MOVE_DOWN:
                startMovement(Direction.DOWN);
                break;
            default:
                break;
        }
    }

    @Override
    public Coordinates tryToApply(Action action) {
        if (action == null) {
            return null;
        }
        if (isMoving()) {
            return null;
        }
        switch (action) {
            case MOVE_RIGHT:
                return Direction.RIGHT.apply(coordinates);
            case MOVE_UP:
                return Direction.UP.apply(coordinates);
            case MOVE_LEFT:
                return Direction.LEFT.apply(coordinates);
            case MOVE_DOWN:
                return Direction.DOWN.apply(coordinates);
            default:
                break;
        }
        return null;
    }
}

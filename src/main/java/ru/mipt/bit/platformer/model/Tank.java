package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.Action;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject, Controllable {
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
    public Controllable afterAction(Action action) {
        if (action == null) {
            return null;
        }
        if (isMoving()) {
            return null;
        }
        switch (action) {
            case MOVE_RIGHT:
                return new Tank(Direction.RIGHT.apply(coordinates), direction, movementSpeed);
            case MOVE_UP:
                return new Tank(Direction.UP.apply(coordinates), direction, movementSpeed);
            case MOVE_LEFT:
                return new Tank(Direction.LEFT.apply(coordinates), direction, movementSpeed);
            case MOVE_DOWN:
                return new Tank(Direction.DOWN.apply(coordinates), direction, movementSpeed);
            default:
                return new Tank(coordinates, direction, movementSpeed);
        }
    }
}

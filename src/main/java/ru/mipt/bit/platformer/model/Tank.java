package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.MoveAction;

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
    public void apply(MoveAction moveAction) {
        startMovement(moveAction.getDirection());
    }

    @Override
    public Controllable afterAction(MoveAction moveAction) {
        if (isMoving()) {
            return new Tank(coordinates, direction, movementSpeed);
        }
        return new Tank(moveAction.getDirection().apply(coordinates), direction, movementSpeed);
    }
}

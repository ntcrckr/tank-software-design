package ru.mipt.bit.platformer.model.impl;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.model.Movable;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class SimpleMovable implements Movable {
    private Coordinates coordinates;
    private Coordinates destinationCoordinates;
    private float movementProgress;
    private Direction direction;
    private final float movementSpeed;

    public SimpleMovable(Coordinates coordinates, Direction direction, float movementSpeed) {
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.movementProgress = 1f;
        this.direction = direction;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public Coordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void apply(Action action) {
        MoveAction moveAction = (MoveAction) action;
        if (moveAction == MoveAction.STOP) {
            stopMovement();
        } else if (!isMoving()) {
            startMovement(moveAction.getDirection());
        }
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public boolean isMoving() {
        return !isEqual(movementProgress, 1f);
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f)) {
            coordinates = destinationCoordinates;
        }
    }

    public void startMovement(Direction direction) {
        destinationCoordinates = direction.apply(coordinates);
        movementProgress = 0f;
        changeDirection(direction);
    }

    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }

    public void stopMovement() {
        destinationCoordinates = coordinates;
        movementProgress = 1f;
    }
}

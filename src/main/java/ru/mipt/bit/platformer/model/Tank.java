package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

import java.util.Objects;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject, Movable {
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

    @Override
    public boolean isMoving() {
        return !isEqual(movementProgress, 1f);
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void startMovement(Direction direction) {
        destinationCoordinates = direction.apply(coordinates);
        movementProgress = 0f;
        changeDirection(direction);
    }

    @Override
    public Coordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f)) {
            coordinates = destinationCoordinates;
        }
    }

    public float getRotation() {
        return direction.getRotation();
    }

    @Override
    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }

    @Override
    public void apply(Action action) {
        if (action instanceof MoveAction moveAction && !isMoving()) {
            startMovement(moveAction.getDirection());
        }
    }

    @Override
    public Movable afterApply(MoveAction moveAction) {
        if (isMoving()) {
            return this;
        }
        changeDirection(moveAction.getDirection());
        return new Tank(moveAction.getDirection().apply(coordinates), direction, movementSpeed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tank tank = (Tank) o;
        return Float.compare(movementProgress, tank.movementProgress) == 0 && Float.compare(movementSpeed, tank.movementSpeed) == 0 && Objects.equals(coordinates, tank.coordinates) && Objects.equals(destinationCoordinates, tank.destinationCoordinates) && direction == tank.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, destinationCoordinates, movementProgress, direction, movementSpeed);
    }
}

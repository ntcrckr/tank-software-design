package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

import java.util.List;
import java.util.Objects;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements GameObject, Movable {
    private final int uid;
    private List<Coordinates> coordinates;
    private List<Coordinates> destinationCoordinates;
    private float movementProgress;
    private Direction direction;
    private final float movementSpeed;

    public Tank(List<Coordinates> coordinates, Direction direction, float movementSpeed) {
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.movementProgress = 1f;
        this.direction = direction;
        this.movementSpeed = movementSpeed;
        this.uid = Objects.hash(coordinates, direction, movementSpeed);
    }

    @Override
    public boolean isMoving() {
        return !isEqual(movementProgress, 1f);
    }


    @Override
    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    @Override
    public void startMovement(Direction direction) {
        destinationCoordinates = direction.apply(coordinates);
        coordinates.addAll(destinationCoordinates);
        movementProgress = 0f;
        changeDirection(direction);
    }

    @Override
    public List<Coordinates> getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
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

    @Override
    public float getRotation() {
        return direction.getRotation();
    }

    @Override
    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tank tank = (Tank) o;
        return uid == tank.uid && Float.compare(movementProgress, tank.movementProgress) == 0 && Float.compare(movementSpeed, tank.movementSpeed) == 0 && Objects.equals(coordinates, tank.coordinates) && Objects.equals(destinationCoordinates, tank.destinationCoordinates) && direction == tank.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, coordinates, destinationCoordinates, movementProgress, direction, movementSpeed);
    }
}

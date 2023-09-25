package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank {
    private final Collidable collidable;
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private float movementProgress;
    private final float movementSpeed;

    public Tank(GridPoint2 coordinates, Direction direction, float movementSpeed) {
        this.collidable = new Collidable(coordinates);
        this.destinationCoordinates = coordinates;
        this.direction = direction;
        this.movementProgress = 1f;
        this.movementSpeed = movementSpeed;
    }

    public boolean isMoving() {
        return !isEqual(movementProgress, 1f);
    }

    public GridPoint2 getCoordinates() {
        return collidable.getCoordinates();
    }

    public boolean checkCollision(Direction direction, List<Collidable> others) {
        for (Collidable other:
             others) {
            if (collidable.goingToCollide(direction, other)) {
                return true;
            }
        }
        return false;
    }

    public void startMovement(Direction direction) {
        destinationCoordinates = direction.apply(collidable.getCoordinates());
        movementProgress = 0f;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f)) {
            // record that the player has reached his/her destination
            collidable.setCoordinates(destinationCoordinates.cpy());
        }
    }

    public float getRotation() {
        return direction.getRotation();
    }

    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }
}

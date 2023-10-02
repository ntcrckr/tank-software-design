package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements Drawable {
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

//    public boolean checkCollision(Direction direction, List<Collidable> others) {
//        for (Collidable other:
//             others) {
//            if (collidable.goingToCollide(direction, other)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public void startMovement(Direction direction) {
        destinationCoordinates = direction.apply(coordinates);
        movementProgress = 0f;
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
            // record that the player has reached his/her destination
            coordinates = destinationCoordinates;
        }
    }

    public float getRotation() {
        return direction.getRotation();
    }

    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }
}

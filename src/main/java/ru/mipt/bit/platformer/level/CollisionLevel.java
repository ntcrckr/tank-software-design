package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.ArrayList;
import java.util.List;

public class CollisionLevel implements LevelListener {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final List<Movable> movables = new ArrayList<>();
    private final int width, height;

    public CollisionLevel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onAdd(GameObject gameObject) {
        if (gameObject instanceof Movable movable){
            movables.add(movable);
        } else {
            gameObjects.add(gameObject);
        }
    }

    @Override
    public void onRemove(GameObject gameObject) {
        if (gameObject instanceof Movable movable) {
            movables.remove(movable);
        } else {
            gameObjects.remove(gameObject);
        }
    }

    public boolean isGoingToCollide(Movable movable) {
        Coordinates destinationCoordinates = movable.getDestinationCoordinates();
        if (!isWithinLevelBounds(destinationCoordinates)) {
            return true;
        }
        for (Movable otherMovable : movables) {
            if (movable == otherMovable) continue;
            Coordinates otherCoordinates = otherMovable.getCoordinates();
            Coordinates otherDestinationCoordinates = otherMovable.getDestinationCoordinates();
            if (destinationCoordinates.equals(otherCoordinates) ||
                    destinationCoordinates.equals(otherDestinationCoordinates)) {
                return true;
            }
        }
        for (GameObject gameObject : gameObjects) {
            Coordinates otherCoordinates = gameObject.getCoordinates();
            if (destinationCoordinates.equals(otherCoordinates)) {
                return true;
            }
        }
        return false;
    }

    private boolean isWithinLevelBounds(Coordinates coordinates) {
        return coordinates.getX() > 0 ||
                coordinates.getX() < width - 1 ||
                coordinates.getY() > 0 ||
                coordinates.getY() < height - 1;
    }
}

package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.*;

public class CollisionLevel {
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final Set<GameObject> gameObjects = new HashSet<>();

    public CollisionLevel(int startX, int endX, int startY, int endY) {
        this.minX = startX;
        this.maxX = endX;
        this.minY = startY;
        this.maxY = endY;
    }

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    private boolean isWithinBounds(Coordinates coordinates) {
        int x = coordinates.x();
        int y = coordinates.y();
        return (x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY);
    }

    private boolean isCoordinatesFree(Coordinates coordinates) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getCoordinates().contains(coordinates)) return false;
        }
        return true;
    }

    public boolean isNotGoingToCollide(Coordinates coordinates) {
        boolean isFree = isCoordinatesFree(coordinates);
        boolean isWithinBounds = isWithinBounds(coordinates);
        return isFree && isWithinBounds;
    }

    public boolean isNotGoingToCollide(List<Coordinates> coordinatesList) {
        for (Coordinates coordinates : coordinatesList) {
            if (!isNotGoingToCollide(coordinates)) return false;
        }
        return true;
    }
}

package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionLevel {
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final Map<Coordinates, GameObject> occupyCoordinatesLookup = new HashMap<>();
    private final Map<GameObject, List<Coordinates>> occupyGameObjectLookup = new HashMap<>();

    public CollisionLevel(int startX, int endX, int startY, int endY) {
        this.minX = startX;
        this.maxX = endX;
        this.minY = startY;
        this.maxY = endY;
    }

    private void put(GameObject gameObject, List<Coordinates> coordinatesList) {
        for (Coordinates coordinates : coordinatesList) {
            occupyCoordinatesLookup.put(coordinates, gameObject);
        }
        occupyGameObjectLookup.put(gameObject, coordinatesList);
    }

    public void add(GameObject gameObject) {
        put(gameObject, gameObject.getCoordinates());
    }

    public void remove(GameObject gameObject) {
        for (Coordinates coordinates : occupyGameObjectLookup.get(gameObject)) {
            occupyCoordinatesLookup.remove(coordinates);
        }
        occupyGameObjectLookup.remove(gameObject);
    }

    public void update(GameObject gameObject, List<Coordinates> coordinatesList) {
        remove(gameObject);
        put(gameObject, coordinatesList);
    }

    private boolean isWithinBounds(Coordinates coordinates) {
        int x = coordinates.x();
        int y = coordinates.y();
        return (x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY);
    }

    public boolean isNotGoingToCollide(Coordinates coordinates) {
        boolean isFree = occupyCoordinatesLookup.get(coordinates) == null;
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

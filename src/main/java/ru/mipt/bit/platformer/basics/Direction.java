package ru.mipt.bit.platformer.basics;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    RIGHT(new Coordinates(1, 0), 0f),
    UP(new Coordinates(0, 1), 90f),
    LEFT(new Coordinates(-1, 0), -180f),
    DOWN(new Coordinates(0, -1), -90f);

    private final Coordinates vector;
    private final float rotation;

    Direction(Coordinates vector, float rotation) {
        this.vector = vector;
        this.rotation = rotation;
    }

    public Coordinates apply(Coordinates coordinates) {
        return coordinates.add(vector);
    }

    public List<Coordinates> apply(List<Coordinates> coordinatesList) {
        List<Coordinates> newCoordinatesList = new ArrayList<>();
        for (Coordinates coordinates : coordinatesList) {
            newCoordinatesList.add(apply(coordinates));
        }
        return newCoordinatesList;
    }

    public float getRotation() {
        return rotation;
    }
}

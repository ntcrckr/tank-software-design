package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public class Collidable {
    private GridPoint2 coordinates;

    public Collidable(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    boolean goingToCollide(Direction direction, Collidable other) {
        return direction.apply(coordinates).equals(other.coordinates);
    }

    GridPoint2 getCoordinates() {
        return coordinates;
    }

    void setCoordinates(GridPoint2 newCoordinates) {
        coordinates = newCoordinates;
    }
}

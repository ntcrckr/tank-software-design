package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle {
//    private final GridPoint2 coordinates;
    private final Collidable collidable;

    public Obstacle(GridPoint2 coordinates) {
//        this.coordinates = coordinates;
        this.collidable = new Collidable(coordinates);
    }

    public GridPoint2 getCoordinates() {
        return collidable.getCoordinates();
    }
}

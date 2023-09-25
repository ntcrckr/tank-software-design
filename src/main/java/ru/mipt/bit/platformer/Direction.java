package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    RIGHT(new GridPoint2(1, 0), 0f),
    UP(new GridPoint2(0, 1), 90f),
    LEFT(new GridPoint2(-1, 0), -180f),
    DOWN(new GridPoint2(0, -1), -90f);

    private final GridPoint2 vector;
    private final float rotation;

    Direction(GridPoint2 vector, float rotation) {
        this.vector = vector;
        this.rotation = rotation;
    }

    GridPoint2 apply(GridPoint2 coordinates) {
        return coordinates.cpy().add(vector);
    }

    public float getRotation() {
        return rotation;
    }
}

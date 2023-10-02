package ru.mipt.bit.platformer.model;

public class Coordinates {
    private final Integer x;
    private final Integer y;

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Coordinates add(Coordinates vector) {
        return new Coordinates(x + vector.getX(), y + vector.getY());
    }
}

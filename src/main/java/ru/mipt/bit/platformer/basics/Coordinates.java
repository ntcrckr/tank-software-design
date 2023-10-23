package ru.mipt.bit.platformer.basics;

public record Coordinates(Integer x, Integer y) {

    public Coordinates add(Coordinates vector) {
        return new Coordinates(x + vector.x(), y + vector.y());
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}

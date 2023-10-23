package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.basics.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCoordinatesGenerator {
    private final Random random = new Random();
    private final List<Coordinates> freeCoordinates = new ArrayList<>();

    public RandomCoordinatesGenerator(int minX, int maxX, int minY, int maxY) {
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                freeCoordinates.add(new Coordinates(x, y));
            }
        }
    }

    public Coordinates getCoordinates() {
        if (freeCoordinates.isEmpty()) return null;
        Coordinates randomCoordinates = freeCoordinates.get(random.nextInt(freeCoordinates.size()));
        freeCoordinates.remove(randomCoordinates);
        return randomCoordinates;
    }
}

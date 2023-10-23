package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.basics.Coordinates;

public class Converter {
    public static GridPoint2 coordinatesToGridPoint2(Coordinates coordinates) {
        return new GridPoint2(coordinates.x(), coordinates.y());
    }
}

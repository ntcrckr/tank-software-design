package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

public class InputController {
    private final Map<Integer, Direction> inputMapping = new HashMap<>();

    public InputController() {}

    public void addMapping(Integer key, Direction direction) {
        inputMapping.put(key, direction);
    }

    public Direction getDirection() {
        for (Integer key :
                inputMapping.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return inputMapping.get(key);
            }
        }
        return null;
    }
}

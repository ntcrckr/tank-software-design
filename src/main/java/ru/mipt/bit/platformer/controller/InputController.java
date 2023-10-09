package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

public class InputController implements Controller {
    private final Map<Integer, Action> keyToActionMap = new HashMap<>();

    public InputController() {}

    public void addMapping(Integer key, Action action) {
        keyToActionMap.put(key, action);
    }

    public Action getAction() {
        for (Integer key :
                keyToActionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return keyToActionMap.get(key);
            }
        }
        return null;
    }
}

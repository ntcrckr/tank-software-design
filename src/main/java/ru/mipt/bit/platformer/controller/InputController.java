package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class InputController implements Controller {
    private final Map<Integer, Action> keyToActionMap = new HashMap<>();

    public InputController(InputType inputType) {
        if (inputType.equals(InputType.KEYBOARD)) {
            initKeyboard();
        }
    }

    public void addMapping(Integer key, Action action) {
        keyToActionMap.put(key, action);
    }

    private void initKeyboard() {
        addMapping(UP, Action.MOVE_UP);
        addMapping(W, Action.MOVE_UP);
        addMapping(LEFT, Action.MOVE_LEFT);
        addMapping(A, Action.MOVE_LEFT);
        addMapping(DOWN, Action.MOVE_DOWN);
        addMapping(S, Action.MOVE_DOWN);
        addMapping(RIGHT, Action.MOVE_RIGHT);
        addMapping(D, Action.MOVE_RIGHT);
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

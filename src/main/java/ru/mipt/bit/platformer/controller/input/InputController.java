package ru.mipt.bit.platformer.controller.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class InputController implements Controller {
    private final Map<Integer, MoveAction> keyToActionMap = new HashMap<>();

    public InputController() {}

    public void addMapping(Integer key, MoveAction moveAction) {
        keyToActionMap.put(key, moveAction);
    }

    public MoveAction getAction() {
        for (Integer key :
                keyToActionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return keyToActionMap.get(key);
            }
        }
        return null;
    }
}

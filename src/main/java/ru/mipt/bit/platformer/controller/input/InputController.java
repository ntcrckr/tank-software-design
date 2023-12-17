package ru.mipt.bit.platformer.controller.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class InputController implements Controller {
    private final Map<Integer, Action> keyToActionMap = new HashMap<>();

    public InputController() {}

    public void addMapping(Integer key, Action action) {
        keyToActionMap.put(key, action);
    }

    @Override
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

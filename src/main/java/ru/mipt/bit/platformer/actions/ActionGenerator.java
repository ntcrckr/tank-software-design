package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.HashMap;
import java.util.Map;

public class ActionGenerator {
    private final Map<GameObject, Controller> controllerTypeMap = new HashMap<>();

    public void add(GameObject gameObject, Controller controller) {
        controllerTypeMap.put(gameObject, controller);
    }

    public Map<GameObject, Action> generateActions() {
        Map<GameObject, Action> actionMap = new HashMap<>();
        for (Map.Entry<GameObject, Controller> entry : controllerTypeMap.entrySet()) {
            Action action = entry.getValue().getAction();
            actionMap.put(entry.getKey(), action);
        }
        return actionMap;
    }
}

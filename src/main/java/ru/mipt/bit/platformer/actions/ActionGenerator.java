package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionGenerator {
    private final Map<Movable, Controller> controllerTypeMap = new HashMap<>();

    public void add(Movable movable, Controller controller) {
        controllerTypeMap.put(movable, controller);
    }

    public Map<GameObject, Action> generateActions() {
        Map<GameObject, Action> actionMap = new HashMap<>();
        for (Map.Entry<Movable, Controller> entry : controllerTypeMap.entrySet()) {
            Action action = entry.getValue().getAction();
            actionMap.put(entry.getKey(), action);
        }
        return actionMap;
    }

    public Adapter getAdapter() {
        return new Adapter();
    }

    public class Adapter {
        public List<Movable> getEnemies(GameObject player) {
            return controllerTypeMap.keySet().stream()
                    .filter(go -> go != player)
                    .toList();
        }
    }
}

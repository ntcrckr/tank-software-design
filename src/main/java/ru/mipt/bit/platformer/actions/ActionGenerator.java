package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.model.Movable;

import java.util.HashMap;
import java.util.Map;

public class ActionGenerator {
    private final Map<Movable, Controller> controllerTypeMap = new HashMap<>();

    public void add(Movable movable, Controller controller) {
        controllerTypeMap.put(movable, controller);
    }

    public Map<Movable, MoveAction> generateActions() {
        Map<Movable, MoveAction> actionMap = new HashMap<>();
        for (Map.Entry<Movable, Controller> entry : controllerTypeMap.entrySet()) {
            MoveAction moveAction = entry.getValue().getAction();
            actionMap.put(entry.getKey(), moveAction);
        }
        return actionMap;
    }
}

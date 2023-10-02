package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.model.Controllable;

import java.util.HashMap;
import java.util.Map;

public class ActionGenerator {
    private final Map<Controllable, Controller> controllerTypeMap = new HashMap<>();

    public void add(Controllable controllable, ControllerType controllerType) {
        if (controllerType.equals(ControllerType.INPUT_CONTROLLER)) {
            controllerTypeMap.put(controllable, new InputController(InputType.KEYBOARD));
        }
    }

    public Map<Controllable, Action> generateActions() {
        Map<Controllable, Action> actionMap = new HashMap<>();
        for (Map.Entry<Controllable, Controller> entry : controllerTypeMap.entrySet()) {
            Action action = entry.getValue().getAction();
            actionMap.put(entry.getKey(), action);
        }
        return actionMap;
    }
}

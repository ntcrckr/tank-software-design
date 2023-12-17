package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.model.GameEntity;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionGenerator {
    private final Map<GameEntity, Controller> controllerTypeMap = new HashMap<>();

    public void add(GameEntity gameEntity, Controller controller) {
        controllerTypeMap.put(gameEntity, controller);
    }

    public Map<GameEntity, Action> generateActions() {
        Map<GameEntity, Action> actionMap = new HashMap<>();
        for (Map.Entry<GameEntity, Controller> entry : controllerTypeMap.entrySet()) {
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
                    .filter(ge -> ge instanceof Movable)
                    .map(ge -> (Movable) ge)
                    .filter(mo -> mo != player)
                    .toList();
        }
    }
}

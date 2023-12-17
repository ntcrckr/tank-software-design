package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.controller.ControllerProvider;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.model.GameEntity;
import ru.mipt.bit.platformer.util.GameEntityType;

import java.util.HashMap;
import java.util.Map;

public class ActionGenerator implements LevelListener {
    private final Map<GameEntity, Controller> controllerMap = new HashMap<>();
    private final Map<GameEntityType, ControllerProvider> controllerProviderMap;

    public ActionGenerator(Map<GameEntityType, ControllerProvider> controllerProviderMap) {
        this.controllerProviderMap = controllerProviderMap;
    }

    public Map<GameEntity, Action> generateActions() {
        Map<GameEntity, Action> actionMap = new HashMap<>();
        for (Map.Entry<GameEntity, Controller> entry : controllerMap.entrySet()) {
            Action action = entry.getValue().getAction();
            actionMap.put(entry.getKey(), action);
        }
        return actionMap;
    }

    @Override
    public void onAdd(GameEntity gameEntity) {
        switch (gameEntity.getGameObjectType()) {
            case PLAYER_TANK, ENEMY_TANK, GUI -> controllerMap.put(
                    gameEntity,
                    controllerProviderMap.get(gameEntity.getGameObjectType()).getController(gameEntity)
            );
        }
    }

    @Override
    public void onRemove(GameEntity gameEntity) {
        controllerMap.remove(gameEntity);
    }
}

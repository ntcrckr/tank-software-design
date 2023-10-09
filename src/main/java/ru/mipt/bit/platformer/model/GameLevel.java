package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.model.actions.MoveAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void applyActions(Map<Movable, MoveAction> actionMap) {
        for (Map.Entry<Movable, MoveAction> entry : actionMap.entrySet()) {
            Movable movable = entry.getKey();
            MoveAction action = entry.getValue();
            if (action == null) continue;
            Movable futureMovable = movable.afterAction(action);
            if (futureMovable.equals(movable)) {
                continue;
            }
            if (!goingToCollide(movable, futureMovable)) {
                movable.apply(action);
            }
        }
    }

    private boolean goingToCollide(Movable initialMovable, Movable futureMovable) {
        for (GameObject gameObject : gameObjects) {
            if (initialMovable.equals(gameObject)) continue;
            if (futureMovable.getCoordinates().equals(gameObject.getCoordinates())) {
                return true;
            }
        }
        return false;
    }

    public void updateState(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }
}

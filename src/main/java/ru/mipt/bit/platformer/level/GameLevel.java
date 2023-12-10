package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final CollisionLevel collisionLevel;
    private final List<LevelListener> levelListeners;

    public GameLevel(Coordinates size, List<LevelListener> levelListeners) {
        this.collisionLevel = new CollisionLevel(size.getX(), size.getY());
        levelListeners.add(collisionLevel);
        this.levelListeners = levelListeners;
    }

    public void add(GameObject gameObject) {
        levelListeners.forEach(ll -> ll.onAdd(gameObject));
        gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        levelListeners.forEach(ll -> ll.onRemove(gameObject));
        gameObjects.remove(gameObject);
    }

    public void applyActions(Map<GameObject, Action> actionMap) {
        for (Map.Entry<GameObject, Action> entry : actionMap.entrySet()) {
            GameObject gameObject = entry.getKey();
            Action action = entry.getValue();

            if (action == null) continue;
            if (gameObject instanceof Movable movable && action instanceof MoveAction moveAction){
                if (!collisionLevel.isGoingToCollide(movable, moveAction)) {
                    movable.apply(moveAction);
                }
            }
        }
    }

    public void updateState(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }
}

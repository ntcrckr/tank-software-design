package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.CreateAction;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
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
    private final Coordinates size;
    private final GameObject player;

    public GameLevel(Coordinates size, List<LevelListener> levelListeners, GameObject player) {
        this.size = size;
        this.collisionLevel = new CollisionLevel(size.getX(), size.getY());
        this.player = player;
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

            Action responseAction = gameObject.apply(action);
            if (responseAction instanceof CreateAction createAction) {
                add(createAction.getGameObject());
            }

            if (gameObject instanceof Movable movable) {
                if (collisionLevel.isGoingToCollide(movable)) {
                    gameObject.apply(MoveAction.STOP);
                }
            }
        }
    }

    public void updateState(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }

    public Coordinates getSize() {
        return size;
    }

    public GameObject getPlayer() {
        return player;
    }

    public Adapter getAdapter() {
        return new Adapter();
    }

    public class Adapter {
        public List<GameObject> getGameObjects() {
            return gameObjects;
        }
    }
}

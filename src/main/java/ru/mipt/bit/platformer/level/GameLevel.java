package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.BulletHitAction;
import ru.mipt.bit.platformer.actions.CreateAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Hittable;
import ru.mipt.bit.platformer.model.Movable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.mipt.bit.platformer.actions.MoveAction.STOP;

public class GameLevel {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final CollisionLevel collisionLevel;
    private final List<LevelListener> levelListeners;
    private final Coordinates size;
    private final Movable player;

    public GameLevel(Coordinates size, List<LevelListener> levelListeners, Movable player) {
        this.size = size;
        this.collisionLevel = new CollisionLevel(size.getX(), size.getY());
        this.player = player;
        levelListeners.add(collisionLevel);
        this.levelListeners = levelListeners;
    }

    public void addLevelListener(LevelListener levelListener) {
        levelListeners.add(levelListener);
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
        }
    }

    public void updateState(float deltaTime) {
        List<GameObject> gameObjectsToRemove = new ArrayList<>();

        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Movable movable) {
                GameObject collidedGameObject = collisionLevel.isGoingToCollide(movable);
                if (collidedGameObject != null) {
                    Action responseAction = gameObject.apply(STOP);
                    if (responseAction instanceof BulletHitAction) {
                        Bullet bullet = (Bullet) movable;
                        gameObjectsToRemove.add(bullet);
                        if (collidedGameObject instanceof Hittable hittable) {
                            hittable.takeDamage(bullet.getDamage());
                        }
                    }
                }
            }
            gameObject.updateState(deltaTime);
        }

        gameObjectsToRemove.forEach(this::remove);
    }

    public Coordinates getSize() {
        return size;
    }

    public Movable getPlayer() {
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

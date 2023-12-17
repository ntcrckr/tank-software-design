package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.BulletHitAction;
import ru.mipt.bit.platformer.actions.CreateAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.mipt.bit.platformer.actions.MoveAction.STOP;

public class GameLevel {
    private final List<GameEntity> gameEntities = new ArrayList<>();
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

    public void add(GameEntity gameEntity) {
        levelListeners.forEach(ll -> ll.onAdd(gameEntity));
        gameEntities.add(gameEntity);
    }

    public void remove(GameObject gameObject) {
        levelListeners.forEach(ll -> ll.onRemove(gameObject));
        gameEntities.remove(gameObject);
    }

    public void applyActions(Map<GameEntity, Action> actionMap) {
        for (Map.Entry<GameEntity, Action> entry : actionMap.entrySet()) {
            GameEntity gameObject = entry.getKey();
            Action action = entry.getValue();

            Action responseAction = gameObject.apply(action);
            if (responseAction instanceof CreateAction createAction) {
                add(createAction.getGameObject());
            }
        }
    }

    public void updateState(float deltaTime) {
        List<GameObject> gameObjectsToRemove = new ArrayList<>();

        for (GameEntity gameEntity : gameEntities) {
            if (gameEntity instanceof Movable movable) {
                GameObject collidedGameObject = collisionLevel.isGoingToCollide(movable);
                if (collidedGameObject != null) {
                    Action responseAction = gameEntity.apply(STOP);
                    if (responseAction instanceof BulletHitAction) {
                        Bullet bullet = (Bullet) movable;
                        gameObjectsToRemove.add(bullet);
                        if (collidedGameObject instanceof Hittable hittable) {
                            hittable.takeDamage(bullet.getDamage());
                        }
                    }
                }
            }
            gameEntity.updateState(deltaTime);
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
            return gameEntities.stream().filter(ge -> ge instanceof GameObject).map(ge -> (GameObject) ge).toList();
        }
    }
}

package ru.mipt.bit.platformer.level;

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

    public GameLevel(int startX, int endX, int startY, int endY) {
        this.collisionLevel = new CollisionLevel(startX, endX, startY, endY);
    }

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
        collisionLevel.add(gameObject);
    }

    public void applyActions(Map<Movable, MoveAction> actionMap) {
        for (Map.Entry<Movable, MoveAction> entry : actionMap.entrySet()) {
            Movable movable = entry.getKey();
            MoveAction action = entry.getValue();
            if (action == null) continue;

            Movable futureMovable = movable.afterAction(action);
            if (futureMovable.equals(movable)) continue;

            if (collisionLevel.isNotGoingToCollide(futureMovable.getCoordinates())) {
                movable.apply(action);
                List<Coordinates> totalCoordinates = movable.getCoordinates();
                totalCoordinates.addAll(futureMovable.getCoordinates());
                collisionLevel.update(movable, totalCoordinates);
            }
        }
    }

    public void updateState(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }
}

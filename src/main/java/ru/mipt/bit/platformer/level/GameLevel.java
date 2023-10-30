package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.actions.*;
import ru.mipt.bit.platformer.model.GameObject;

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

    public void applyActions(Map<GameObject, Action> actionMap) {
        for (Map.Entry<GameObject, Action> entry : actionMap.entrySet()) {
            GameObject gameObject = entry.getKey();
            Action action = entry.getValue();
            action.apply(gameObject);
        }
    }

    public void updateState(float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.updateState(deltaTime);
        }
    }
}

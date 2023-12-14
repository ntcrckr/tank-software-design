package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.ArrayList;
import java.util.List;

public class CollisionLevel implements LevelListener {
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final int width, height;

    public CollisionLevel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onAdd(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    @Override
    public void onRemove(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public boolean isGoingToCollide(GameObject gameObject) {
        if (!(gameObject instanceof Movable)) return false;
        Coordinates destinationCoordinates = gameObject.getDestinationCoordinates();
        for (GameObject otherGameObject : gameObjects) {
            if (gameObject == otherGameObject) continue;
            Coordinates otherCoordinates = otherGameObject.getCoordinates();
            Coordinates otherDestinationCoordinates = otherGameObject.getDestinationCoordinates();
            if (destinationCoordinates.equals(otherCoordinates) ||
                    destinationCoordinates.equals(otherDestinationCoordinates) ||
                    destinationCoordinates.getX() < 0 ||
                    destinationCoordinates.getX() > width - 1 ||
                    destinationCoordinates.getY() < 0 ||
                    destinationCoordinates.getY() > height - 1) {
                return true;
            }
        }
        return false;
    }
}

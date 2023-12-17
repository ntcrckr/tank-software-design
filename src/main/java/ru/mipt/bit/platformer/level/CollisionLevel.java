package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameEntity;
import ru.mipt.bit.platformer.model.GameLevelBoundary;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.util.ArrayList;
import java.util.List;

public class CollisionLevel implements LevelListener {
    private final List<Movable> movables = new ArrayList<>();
    private final List<GameObject> otherGameObjects = new ArrayList<>();
    private final int width, height;

    public CollisionLevel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onAdd(GameEntity gameEntity) {
        if (!(gameEntity instanceof GameObject gameObject)) return;
        if (gameObject instanceof Movable movable){
            movables.add(movable);
        } else {
            otherGameObjects.add(gameObject);
        }
    }

    @Override
    public void onRemove(GameEntity gameEntity) {
        if (gameEntity instanceof Movable movable) {
            movables.remove(movable);
        } else {
            otherGameObjects.remove(gameEntity);
        }
    }

    public GameObject isGoingToCollide(Movable movable) {
        Coordinates destinationCoordinates = movable.getDestinationCoordinates();
        if (!isWithinLevelBounds(destinationCoordinates)) {
            return new GameLevelBoundary();
        }
        for (Movable otherMovable : movables) {
            if (movable == otherMovable) continue;
            if (!otherMovable.blocking()) continue;
            Coordinates otherCoordinates = otherMovable.getCoordinates();
            Coordinates otherDestinationCoordinates = otherMovable.getDestinationCoordinates();
            if (destinationCoordinates.equals(otherCoordinates) ||
                    destinationCoordinates.equals(otherDestinationCoordinates)) {
                return otherMovable;
            }
        }
        for (GameObject gameObject : otherGameObjects) {
            Coordinates otherCoordinates = gameObject.getCoordinates();
            if (destinationCoordinates.equals(otherCoordinates)) {
                return gameObject;
            }
        }
        return null;
    }

    private boolean isWithinLevelBounds(Coordinates coordinates) {
        return coordinates.getX() > 0 ||
                coordinates.getX() < width - 1 ||
                coordinates.getY() > 0 ||
                coordinates.getY() < height - 1;
    }
}

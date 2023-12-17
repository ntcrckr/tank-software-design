package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.util.GameEntityType;

public class Obstacle implements GameObject {
    private final Coordinates coordinates;
    private final Direction direction;
    private final GameEntityType gameObjectType;

    public Obstacle(Coordinates coordinates, GameEntityType gameObjectType) {
        this.coordinates = coordinates;
        this.direction = Direction.RIGHT;
        this.gameObjectType = gameObjectType;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public GameEntityType getGameObjectType() {
        return gameObjectType;
    }

    @Override
    public void updateState(float deltaTime) {
        // does nothing for now
    }

    @Override
    public Action apply(Action action) {
        return null;
    }
}

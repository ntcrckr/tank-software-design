package ru.mipt.bit.platformer.model.tank;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.BulletHitAction;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.util.GameEntityType;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GameEntityType.BULLET;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class TankBullet implements Bullet {
    private Coordinates coordinates;
    private Coordinates destinationCoordinates;
    private float movementProgress = 0f;
    private final Direction direction;
    private final float movementSpeed;
    private final float damage;

    public TankBullet(Coordinates coordinates, Direction direction, float movementSpeed, float damage) {
        this.coordinates = coordinates;
        this.damage = damage;
        this.destinationCoordinates = direction.apply(coordinates);
        this.direction = direction;
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isEqual(movementProgress, 1f)) {
            coordinates = destinationCoordinates;
            destinationCoordinates = direction.apply(coordinates);
            movementProgress = 0f;
        }
    }

    @Override
    public Action apply(Action action) {
        if (action == MoveAction.STOP) {
            return new BulletHitAction();
        }
        return null;
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean blocking() {
        return false;
    }

    @Override
    public Coordinates getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public GameEntityType getGameObjectType() {
        return BULLET;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public boolean isMoving() {
        return true;
    }

    @Override
    public float getDamage() {
        return damage;
    }
}

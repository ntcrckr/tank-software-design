package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.model.tank.TankHittable;
import ru.mipt.bit.platformer.model.tank.TankMovable;
import ru.mipt.bit.platformer.model.tank.TankShooter;
import ru.mipt.bit.platformer.util.GameObjectType;

public class Tank implements Movable, Shooter, Hittable {
    private final Movable movable;
    private final Shooter shooter;
    private final Hittable hittable;
    private final GameObjectType gameObjectType;

    public Tank(
            Coordinates coordinates, Direction direction, float movementSpeed,
            Class<? extends Bullet> bulletClass, float reloadTime, float bulletSpeed, float bulletDamage,
            float health,
            GameObjectType gameObjectType
    ) {
        this.movable = new TankMovable(coordinates, direction, movementSpeed);
        this.shooter = new TankShooter(bulletClass, reloadTime, bulletSpeed, bulletDamage, this);
        this.hittable = new TankHittable(health);
        this.gameObjectType = gameObjectType;
    }

    @Override
    public Action apply(Action action) {
        if (action instanceof MoveAction moveAction) {
            return movable.apply(moveAction);
        } else if (action instanceof ShootAction shootAction && !isMoving()) {
            return shooter.apply(shootAction);
        } else {
            return null;
        }
    }

    @Override
    public void updateState(float deltaTime) {
        movable.updateState(deltaTime);
        shooter.updateState(deltaTime);
    }

    @Override
    public Coordinates getCoordinates() {
        return movable.getCoordinates();
    }

    @Override
    public Coordinates getDestinationCoordinates() {
        return movable.getDestinationCoordinates();
    }

    @Override
    public Direction getDirection() {
        return movable.getDirection();
    }

    @Override
    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }

    @Override
    public float getMovementProgress() {
        return movable.getMovementProgress();
    }

    @Override
    public boolean isMoving() {
        return movable.isMoving();
    }

    @Override
    public float getHealth() {
        return hittable.getHealth();
    }

    @Override
    public void takeDamage(float damage) {
        hittable.takeDamage(damage);
    }
}

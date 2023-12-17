package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.model.tank.TankHittable;
import ru.mipt.bit.platformer.model.tank.TankMovable;
import ru.mipt.bit.platformer.model.tank.TankShooter;
import ru.mipt.bit.platformer.model.tank.TankState;
import ru.mipt.bit.platformer.util.GameEntityType;

import static ru.mipt.bit.platformer.model.tank.TankState.*;

public class Tank implements Movable, Shooter, Hittable, HasStates {
    private final Movable movable;
    private final Shooter shooter;
    private final Hittable hittable;
    private final GameEntityType gameObjectType;
    private TankState state = OK;

    public Tank(
            Coordinates coordinates, Direction direction, float movementSpeed,
            Class<? extends Bullet> bulletClass, float reloadTime, float bulletSpeed, float bulletDamage,
            float health,
            GameEntityType gameObjectType
    ) {
        this.movable = new TankMovable(coordinates, direction, movementSpeed, this);
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
    public boolean blocking() {
        return true;
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
    public GameEntityType getGameObjectType() {
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
        checkState();
    }

    private void checkState() {
        float partHealthLeft = getHealth() / getMaxHealth();
        if (partHealthLeft > 0.7) {
            state = OK;
        } else if (partHealthLeft > 0.3) {
            state = MEDIUM_DAMAGE;
        } else {
            state = HEAVY_DAMAGE;
        }
    }

    @Override
    public float getMaxHealth() {
        return hittable.getMaxHealth();
    }

    public TankState getState() {
        return state;
    }
}

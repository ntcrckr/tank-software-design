package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

public class Tank implements Movable, Shooter {
    private final Movable movable;
    private final Shooter shooter;

    public Tank(Movable movable, Shooter shooter) {
        this.movable = movable;
        this.shooter = shooter;
    }

    @Override
    public Action apply(Action action) {
        if (action instanceof MoveAction moveAction) {
            return movable.apply(moveAction);
        } else if (action instanceof ShootAction shootAction) {
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
    public float getMovementProgress() {
        return movable.getMovementProgress();
    }

    @Override
    public boolean isMoving() {
        return movable.isMoving();
    }

    @Override
    public boolean didShoot() {
        return shooter.didShoot();
    }
}

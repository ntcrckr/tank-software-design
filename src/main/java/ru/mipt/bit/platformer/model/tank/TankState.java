package ru.mipt.bit.platformer.model.tank;

import ru.mipt.bit.platformer.model.GameObjectState;

public enum TankState implements GameObjectState {
    OK(1f, true),
    MEDIUM_DAMAGE(0.5f, true),
    HEAVY_DAMAGE(0.33f, false);

    private final float movementSpeedMultiplier;
    private final boolean canShoot;

    TankState(float movementSpeedMultiplier, boolean canShoot) {
        this.movementSpeedMultiplier = movementSpeedMultiplier;
        this.canShoot = canShoot;
    }

    public float getMovementSpeedMultiplier() {
        return movementSpeedMultiplier;
    }

    public boolean canShoot() {
        return canShoot;
    }
}

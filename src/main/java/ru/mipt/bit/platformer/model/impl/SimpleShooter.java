package ru.mipt.bit.platformer.model.impl;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.model.Shooter;

public class SimpleShooter implements Shooter {
    private static final float noReloadTime = 0f;
    private final float reloadTime;
    private float currentReloadTime = noReloadTime;
    private boolean didShoot = false;

    public SimpleShooter(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    @Override
    public void apply(Action action) {
        if (canShoot()) {
            currentReloadTime = reloadTime;
            didShoot = true;
            System.out.println("pew");
        }
    }

    @Override
    public void updateState(float deltaTime) {
        if (!canShoot()) {
            currentReloadTime = Math.max(currentReloadTime - deltaTime, noReloadTime);
        }
    }

    @Override
    public boolean didShoot() {
        if (didShoot) {
            didShoot = false;
            return true;
        } else {
            return false;
        }
    }

    private boolean canShoot() {
        return currentReloadTime == noReloadTime;
    }
}

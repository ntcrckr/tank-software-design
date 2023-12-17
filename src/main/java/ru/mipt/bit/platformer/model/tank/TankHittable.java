package ru.mipt.bit.platformer.model.tank;

import ru.mipt.bit.platformer.model.Hittable;

public class TankHittable implements Hittable {
    private float health;
    private final float maxHealth;

    public TankHittable(float health) {
        this.health = health;
        maxHealth = health;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void takeDamage(float damage) {
        health = Math.max(health - damage, 0);
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }
}

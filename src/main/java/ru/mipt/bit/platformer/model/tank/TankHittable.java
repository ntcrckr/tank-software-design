package ru.mipt.bit.platformer.model.tank;

import ru.mipt.bit.platformer.model.Hittable;

public class TankHittable implements Hittable {
    private float health;

    public TankHittable(float health) {
        this.health = health;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void takeDamage(float damage) {
        health = Math.max(health - damage, 0);
        System.out.println(health);
    }
}

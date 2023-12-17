package ru.mipt.bit.platformer.model;

public interface Hittable {
    float getHealth();

    void takeDamage(float damage);
}

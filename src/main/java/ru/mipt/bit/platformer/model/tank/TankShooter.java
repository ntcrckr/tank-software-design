package ru.mipt.bit.platformer.model.tank;

import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.CreateAction;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Shooter;

import java.lang.reflect.InvocationTargetException;

public class TankShooter implements Shooter {
    private static final float noReloadTime = 0f;
    private final float reloadTime;
    private float currentReloadTime = noReloadTime;
    private final Class<? extends Bullet> bulletClass;
    private final float bulletSpeed;
    private final float bulletDamage;
    private final GameObject parent;

    public TankShooter(Class<? extends Bullet> bulletClass, float reloadTime, float bulletSpeed, float bulletDamage, GameObject parent) {
        this.bulletClass = bulletClass;
        this.reloadTime = reloadTime;
        this.bulletSpeed = bulletSpeed;
        this.bulletDamage = bulletDamage;
        this.parent = parent;
    }

    @Override
    public Action apply(Action action) {
        if (canShoot()) {
            currentReloadTime = reloadTime;
            try {
                return new CreateAction(
                        bulletClass
                                .getConstructor(Coordinates.class, Direction.class, float.class, float.class)
                                .newInstance(parent.getCoordinates(), parent.getDirection(), bulletSpeed, bulletDamage)
                );
            } catch (InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public void updateState(float deltaTime) {
        if (!canShoot()) {
            currentReloadTime = Math.max(currentReloadTime - deltaTime, noReloadTime);
        }
    }

    private boolean canShoot() {
        return currentReloadTime == noReloadTime;
    }
}

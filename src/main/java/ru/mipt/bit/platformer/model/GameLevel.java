package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLevel {
    private final List<Tank> tanks = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();

    public void add(Tank tank) {
        tanks.add(tank);
    }

    public void add(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void applyActions(Map<Controllable, Action> actionMap) {
        for (Map.Entry<Controllable, Action> entry : actionMap.entrySet()) {
            Controllable controllable = entry.getKey();
            Action action = entry.getValue();
            Coordinates futureCoordinates = controllable.tryToApply(action);
            if (futureCoordinates == null) {
                continue;
            }
            if (!goingToCollide(controllable, futureCoordinates)) {
                controllable.apply(action);
            }
        }
    }

    private boolean goingToCollide(Controllable controllable, Coordinates coordinates) {
        System.out.println(coordinates);
        for (Obstacle obstacle : obstacles) {
            System.out.println(obstacle.getCoordinates());
            if (coordinates.equals(obstacle.getCoordinates())) {
                return true;
            }
        }
        for (Tank tank : tanks) {
            if (tank.equals(controllable)) {
                continue;
            }
            System.out.println(tank.getCoordinates());
            if (coordinates.equals(tank.getCoordinates())) {
                return true;
            }
        }
        return false;
    }

    public void updateState(float deltaTime) {
        for (Tank tank : tanks) {
            tank.updateState(deltaTime);
        }
    }
}

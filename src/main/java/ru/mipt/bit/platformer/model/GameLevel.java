package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLevel {
    private final List<Controllable> controllables = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();

    public void add(Controllable controllable) {
        controllables.add(controllable);
    }

    public void add(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void applyActions(Map<Controllable, Action> actionMap) {
        for (Map.Entry<Controllable, Action> entry : actionMap.entrySet()) {
            Controllable controllable = entry.getKey();
            Action action = entry.getValue();
            Controllable futureControllable = controllable.afterAction(action);
            if (futureControllable.equals(controllable)) {
                continue;
            }
            if (!goingToCollide(controllable, futureControllable)) {
                controllable.apply(action);
            }
        }
    }

    private boolean goingToCollide(Controllable initialControllable, Controllable futureControllable) {
        for (Obstacle obstacle : obstacles) {
            if (futureControllable.getCoordinates().equals(obstacle.getCoordinates())) {
                return true;
            }
        }
        for (Controllable controllable : controllables) {
            if (controllable.equals(initialControllable)) {
                continue;
            }
            if (futureControllable.getCoordinates().equals(controllable.getCoordinates())) {
                return true;
            }
        }
        return false;
    }

    public void updateState(float deltaTime) {
        for (Controllable controllable : controllables) {
            controllable.updateState(deltaTime);
        }
    }
}

package ru.mipt.bit.platformer.model;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    private final List<Tank> tanks = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();

    public void add(Tank tank) {
        tanks.add(tank);
    }

    public void add(Obstacle obstacle) {
        obstacles.add(obstacle);
    }
}

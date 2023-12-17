package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.model.GameEntity;

public interface ControllerProvider {
    Controller getController(GameEntity gameEntity);
}

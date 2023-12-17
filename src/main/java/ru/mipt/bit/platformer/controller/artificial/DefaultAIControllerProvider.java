package ru.mipt.bit.platformer.controller.artificial;

import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.controller.ControllerProvider;
import ru.mipt.bit.platformer.model.GameEntity;

public class DefaultAIControllerProvider implements ControllerProvider {
    @Override
    public Controller getController(GameEntity gameEntity) {
        return new DefaultAIController();
    }
}

package ru.mipt.bit.platformer.controller.input;

import ru.mipt.bit.platformer.actions.GUIToggleAction;
import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.controller.ControllerProvider;
import ru.mipt.bit.platformer.model.GameEntity;

import static com.badlogic.gdx.Input.Keys.L;

public class GUIControllerProvider implements ControllerProvider {
    @Override
    public Controller getController(GameEntity gameEntity) {
        InputController inputController = new InputController();
        inputController.addMapping(L, GUIToggleAction.HEALTH);
        return inputController;
    }
}

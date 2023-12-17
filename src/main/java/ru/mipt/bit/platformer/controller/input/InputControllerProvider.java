package ru.mipt.bit.platformer.controller.input;

import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.actions.GUIToggleAction;
import ru.mipt.bit.platformer.controller.Controller;

import static com.badlogic.gdx.Input.Keys.*;

public class InputControllerProvider {
    public static Controller getTankKeyboardDefault() {
        InputController inputController = new InputController();
        inputController.addMapping(UP, MoveAction.UP);
        inputController.addMapping(W, MoveAction.UP);
        inputController.addMapping(LEFT, MoveAction.LEFT);
        inputController.addMapping(A, MoveAction.LEFT);
        inputController.addMapping(DOWN, MoveAction.DOWN);
        inputController.addMapping(S, MoveAction.DOWN);
        inputController.addMapping(RIGHT, MoveAction.RIGHT);
        inputController.addMapping(D, MoveAction.RIGHT);
        inputController.addMapping(SPACE, new ShootAction());
        return inputController;
    }

    public static Controller getGUIKeyboardDefault() {
        InputController inputController = new InputController();
        inputController.addMapping(L, GUIToggleAction.HEALTH);
        return inputController;
    }
}

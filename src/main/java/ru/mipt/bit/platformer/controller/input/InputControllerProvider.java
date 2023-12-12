package ru.mipt.bit.platformer.controller.input;

import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ShootAction;
import ru.mipt.bit.platformer.controller.Controller;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class InputControllerProvider {
    public static Controller getKeyboardDefault() {
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
}

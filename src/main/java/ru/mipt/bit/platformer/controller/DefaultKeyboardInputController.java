package ru.mipt.bit.platformer.controller;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultKeyboardInputController extends InputController {
    public DefaultKeyboardInputController() {
        super();
        addMapping(UP, MoveAction.UP);
        addMapping(W, MoveAction.UP);
        addMapping(LEFT, MoveAction.LEFT);
        addMapping(A, MoveAction.LEFT);
        addMapping(DOWN, MoveAction.DOWN);
        addMapping(S, MoveAction.DOWN);
        addMapping(RIGHT, MoveAction.RIGHT);
        addMapping(D, MoveAction.RIGHT);
    }
}

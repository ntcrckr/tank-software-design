package ru.mipt.bit.platformer.controller;

import static com.badlogic.gdx.Input.Keys.*;

public class DefaultKeyboardInputController extends InputController {
    public DefaultKeyboardInputController() {
        super();
        addMapping(UP, Action.MOVE_UP);
        addMapping(W, Action.MOVE_UP);
        addMapping(LEFT, Action.MOVE_LEFT);
        addMapping(A, Action.MOVE_LEFT);
        addMapping(DOWN, Action.MOVE_DOWN);
        addMapping(S, Action.MOVE_DOWN);
        addMapping(RIGHT, Action.MOVE_RIGHT);
        addMapping(D, Action.MOVE_RIGHT);
    }
}

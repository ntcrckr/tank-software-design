package ru.mipt.bit.platformer.model;

import ru.mipt.bit.platformer.controller.Action;

public interface Controllable {
    void apply(Action action);

    Coordinates tryToApply(Action action);
}

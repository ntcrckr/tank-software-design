package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.model.actions.MoveAction;

public interface Controller {
    MoveAction getAction();
}

package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.actions.MoveAction;

public interface Controller {
    MoveAction getAction();
}

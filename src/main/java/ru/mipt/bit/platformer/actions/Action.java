package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.model.GameObject;

public interface Action {
    void apply(GameObject gameObject);
}

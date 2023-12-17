package ru.mipt.bit.platformer.actions;

import ru.mipt.bit.platformer.model.GameObject;

public class CreateAction implements Action {
    private final GameObject gameObject;

    public CreateAction(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}

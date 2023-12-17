package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;

public interface GameObjectInitMap {
    GameObject getGameObject(GameObjectType gameObjectType, Coordinates coordinates);
}

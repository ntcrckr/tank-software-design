package ru.mipt.bit.platformer.level.generator.provider;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;

public interface GameObjectProvider {
    GameObject provideObject(Coordinates coordinates);

    String provideTexturePath();
}

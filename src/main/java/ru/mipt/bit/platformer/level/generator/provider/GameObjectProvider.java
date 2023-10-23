package ru.mipt.bit.platformer.level.generator.provider;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;

import java.util.List;

public interface GameObjectProvider {
    GameObject provideObject(List<Coordinates> coordinates);

    String provideTexturePath();
}

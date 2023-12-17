package ru.mipt.bit.platformer.level.generator;

import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.util.GameObjectInitMap;

import java.util.List;

public interface LevelGenerator {
    LevelInfo generate(GameObjectInitMap gameObjectInitMap, List<LevelListener> levelListeners);
}

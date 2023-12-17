package ru.mipt.bit.platformer.level.generator;

import ru.mipt.bit.platformer.level.LevelListener;

import java.util.List;

public interface LevelGenerator {
    LevelInfo generate(List<LevelListener> levelListeners);
}

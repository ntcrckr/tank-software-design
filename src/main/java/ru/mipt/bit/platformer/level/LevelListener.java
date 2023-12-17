package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.model.GameEntity;

public interface LevelListener {
    void onAdd(GameEntity gameEntity);
    void onRemove(GameEntity gameEntity);
}

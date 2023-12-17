package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.model.GameObject;

public interface LevelListener {
    void onAdd(GameObject gameObject);
    void onRemove(GameObject gameObject);
}

package ru.mipt.bit.platformer.instructions;

import ru.mipt.bit.platformer.model.GameObject;

public interface Instruction {
    void apply(GameObject gameObject);
}

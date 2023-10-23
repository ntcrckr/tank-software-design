package ru.mipt.bit.platformer.level.generator.provider;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class TankProvider implements GameObjectProvider {

    private final String texturePath;

    public TankProvider(String texturePath) {
        this.texturePath = texturePath;
    }

    @Override
    public GameObject provideObject(List<Coordinates> coordinates) {
        return new Tank(coordinates, Direction.RIGHT, 0.4f);
    }

    @Override
    public String provideTexturePath() {
        return texturePath;
    }
}

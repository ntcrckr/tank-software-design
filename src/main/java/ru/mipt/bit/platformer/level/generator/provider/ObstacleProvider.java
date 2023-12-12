package ru.mipt.bit.platformer.level.generator.provider;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Obstacle;

public class ObstacleProvider implements GameObjectProvider {

    private final String texturePath;

    public ObstacleProvider(String texturePath) {
        this.texturePath = texturePath;
    }

    @Override
    public GameObject provideObject(Coordinates coordinates) {
        return new Obstacle(coordinates);
    }

    @Override
    public String provideTexturePath() {
        return texturePath;
    }
}

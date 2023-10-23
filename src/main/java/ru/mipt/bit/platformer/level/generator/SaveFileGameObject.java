package ru.mipt.bit.platformer.level.generator;

import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.controller.input.DefaultKeyboardInputController;
import ru.mipt.bit.platformer.level.generator.provider.GameObjectProvider;
import ru.mipt.bit.platformer.level.generator.provider.ObstacleProvider;
import ru.mipt.bit.platformer.level.generator.provider.TankProvider;
import ru.mipt.bit.platformer.model.GameObject;

import java.util.HashMap;
import java.util.Map;

public enum SaveFileGameObject {
    EMPTY('_', null, null),
    TREE('T', new ObstacleProvider("images/greenTree.png"), null),
    PLAYER('X', new TankProvider("images/tank_blue.png"), new DefaultKeyboardInputController());

    private final Character notation;
    private final GameObjectProvider gameObjectProvider;
    private final Controller controller;

    SaveFileGameObject(Character notation, GameObjectProvider gameObjectProvider, Controller controller) {
        this.notation = notation;
        this.gameObjectProvider = gameObjectProvider;
        this.controller = controller;
    }

    private static final Map<Character, SaveFileGameObject> notationMap;

    static {
        notationMap = new HashMap<>();
        for (SaveFileGameObject saveFileGameObject : SaveFileGameObject.values()) {
            notationMap.put(saveFileGameObject.notation, saveFileGameObject);
        }
    }

    public static SaveFileGameObject byNotation(Character notation) {
        SaveFileGameObject saveFileGameObject = notationMap.get(notation);
        if (saveFileGameObject == null) {
            throw new NullPointerException();
        }
        return saveFileGameObject;
    }

    public boolean isProvidable() {
        return gameObjectProvider != null;
    }

    public GameObject provideObject(Coordinates coordinates) {
        return gameObjectProvider.provideObject(coordinates);
    }

    public String provideTexturePath() {
        return gameObjectProvider.provideTexturePath();
    }

    public boolean isControllable() {
        return controller != null;
    }

    public Controller getController() {
        return controller;
    }
}

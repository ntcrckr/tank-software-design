package ru.mipt.bit.platformer.level.generator.impl;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.graphics.GUI;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.util.AssetMappings;
import ru.mipt.bit.platformer.util.ControllerMappings;
import ru.mipt.bit.platformer.util.GameObjectInitMap;

import java.util.List;

import static ru.mipt.bit.platformer.util.GameEntityType.*;

public class DefaultLevelGenerator implements LevelGenerator {
    @Override
    public LevelInfo generate(GameObjectInitMap gameObjectInitMap, List<LevelListener> levelListeners) {
        Movable playerTank = (Movable) gameObjectInitMap.getGameObject(PLAYER_TANK, new Coordinates(1, 1));

        GameLevel gameLevel = new GameLevel(new Coordinates(8, 10), levelListeners, playerTank);

        GUI gui = new GUI();

        GameGraphics gameGraphics = new GameGraphics(AssetMappings.graphicsPathMap, gui);
        gameGraphics.init();
        gameLevel.addLevelListener(gameGraphics);

        ActionGenerator actionGenerator = new ActionGenerator(new ControllerMappings(gameLevel).controllerProviderMap);
        gameLevel.addLevelListener(actionGenerator);

        gameLevel.add(playerTank);
        gameLevel.add(gui);

        GameObject tree = gameObjectInitMap.getGameObject(TREE, new Coordinates(1, 3));
        gameLevel.add(tree);

        Movable enemy = (Movable) gameObjectInitMap.getGameObject(ENEMY_TANK, new Coordinates(4, 4));
        gameLevel.add(enemy);

        gameGraphics.moveRectanglesAtTileCenters();
        return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
    }
}

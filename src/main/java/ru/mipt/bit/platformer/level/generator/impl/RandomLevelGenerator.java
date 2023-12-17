package ru.mipt.bit.platformer.level.generator.impl;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.controller.artificial.AIControllerAdapter;
import ru.mipt.bit.platformer.controller.input.InputControllerProvider;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.graphics.GUI;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.util.AssetMappings;
import ru.mipt.bit.platformer.util.GameObjectInitMap;
import ru.mipt.bit.platformer.util.RandomCoordinatesGenerator;

import java.util.List;

import static ru.mipt.bit.platformer.util.GameObjectType.*;


public class RandomLevelGenerator implements LevelGenerator {

    private final int width;
    private final int height;
    private final int treesAmount;
    private final int enemiesAmount;

    public RandomLevelGenerator(int width, int height, int treesAmount, int enemiesAmount) {
        this.width = width;
        this.height = height;
        this.treesAmount = treesAmount;
        this.enemiesAmount = enemiesAmount;
    }

    @Override
    public LevelInfo generate(GameObjectInitMap gameObjectInitMap, List<LevelListener> levelListeners) {
        RandomCoordinatesGenerator coordinatesGenerator = new RandomCoordinatesGenerator(0, width - 1, 0, height - 1);

        Movable playerTank = (Movable) gameObjectInitMap.getGameObject(PLAYER_TANK, coordinatesGenerator.getCoordinates());

        GameLevel gameLevel = new GameLevel(new Coordinates(width, height), levelListeners, playerTank);

        ActionGenerator actionGenerator = new ActionGenerator();

        GUI gui = new GUI();
        gameLevel.add(gui);
        actionGenerator.add(gui, InputControllerProvider.getGUIKeyboardDefault());

        AIControllerAdapter enemyController = new AIControllerAdapter(gameLevel, actionGenerator);

        GameGraphics gameGraphics = new GameGraphics(AssetMappings.graphicsPathMap, gui);
        gameGraphics.init();
        gameLevel.addLevelListener(gameGraphics);

        gameLevel.add(playerTank);
        actionGenerator.add(playerTank, InputControllerProvider.getTankKeyboardDefault());

        for (int i = 0; i < treesAmount; i++) {
            GameObject tree = gameObjectInitMap.getGameObject(TREE, coordinatesGenerator.getCoordinates());
            gameLevel.add(tree);
        }

        for (int i = 0; i < enemiesAmount; i++) {
            Movable tank = (Movable) gameObjectInitMap.getGameObject(ENEMY_TANK, coordinatesGenerator.getCoordinates());
            gameLevel.add(tank);
            actionGenerator.add(tank, enemyController.getController(tank));
        }

        gameGraphics.moveRectanglesAtTileCenters();

        return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
    }
}

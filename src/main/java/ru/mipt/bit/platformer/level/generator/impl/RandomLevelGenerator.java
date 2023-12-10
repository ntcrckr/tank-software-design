package ru.mipt.bit.platformer.level.generator.impl;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.controller.input.InputControllerProvider;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.RandomCoordinatesGenerator;

import java.util.List;


public class RandomLevelGenerator implements LevelGenerator {

    private final int width;
    private final int height;
    private final int treesAmount;

    public RandomLevelGenerator(int width, int height, int treesAmount) {
        this.width = width;
        this.height = height;
        this.treesAmount = treesAmount;
    }

    @Override
    public LevelInfo generate(List<LevelListener> levelListeners) {
        GameLevel gameLevel = new GameLevel(new Coordinates(width, height), levelListeners);
        GameGraphics gameGraphics = new GameGraphics();
        gameGraphics.init();
        ActionGenerator actionGenerator = new ActionGenerator();

        RandomCoordinatesGenerator coordinatesGenerator = new RandomCoordinatesGenerator(0, width-1, 0, height-1);
        Tank playerTank = new Tank(coordinatesGenerator.getCoordinates(), Direction.RIGHT, 0.4f);
        gameLevel.add(playerTank);
        gameGraphics.addGameObject(playerTank, "images/tank_blue.png");
        actionGenerator.add(playerTank, InputControllerProvider.getKeyboardDefault());

        for (int i = 0; i < treesAmount; i++) {
            Obstacle tree = new Obstacle(coordinatesGenerator.getCoordinates());
            gameLevel.add(tree);
            gameGraphics.addGameObject(tree, "images/greenTree.png");
        }

        gameGraphics.moveRectanglesAtTileCenters();

        return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
    }
}

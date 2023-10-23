package ru.mipt.bit.platformer.level.generator;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.controller.input.DefaultKeyboardInputController;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.util.RandomCoordinatesGenerator;

import java.util.List;


public class RandomLevelGenerator implements LevelGenerator {

    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;
    private final int treesAmount;

    public RandomLevelGenerator(int startX, int endX, int startY, int endY, int treesAmount) {
        this.minX = startX;
        this.maxX = endX;
        this.minY = startY;
        this.maxY = endY;
        this.treesAmount = treesAmount;
    }

    @Override
    public LevelInfo generate() {
        GameLevel gameLevel = new GameLevel(minX, maxX, minY, maxY);
        GameGraphics gameGraphics = new GameGraphics();
        gameGraphics.init();
        ActionGenerator actionGenerator = new ActionGenerator();

        RandomCoordinatesGenerator coordinatesGenerator = new RandomCoordinatesGenerator(minX, maxX, minY, maxY);
        Tank playerTank = new Tank(List.of(coordinatesGenerator.getCoordinates()), Direction.RIGHT, 0.4f);
        gameLevel.add(playerTank);
        gameGraphics.addGameObject(playerTank, "images/tank_blue.png");
        actionGenerator.add(playerTank, new DefaultKeyboardInputController());

        for (int i = 0; i < treesAmount; i++) {
            Obstacle tree = new Obstacle(List.of(coordinatesGenerator.getCoordinates()));
            gameLevel.add(tree);
            gameGraphics.addGameObject(tree, "images/greenTree.png");
        }

        gameGraphics.moveRectanglesAtTileCenters();

        return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
    }
}

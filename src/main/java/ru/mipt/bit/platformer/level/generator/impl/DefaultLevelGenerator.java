package ru.mipt.bit.platformer.level.generator.impl;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.controller.Controller;
import ru.mipt.bit.platformer.controller.artificial.AIControllerAdapter;
import ru.mipt.bit.platformer.controller.input.InputControllerProvider;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.util.List;

public class DefaultLevelGenerator implements LevelGenerator {
    @Override
    public LevelInfo generate(List<LevelListener> levelListeners) {
        Tank playerTank = new Tank(new Coordinates(1, 1), Direction.RIGHT, 0.4f);

        GameLevel gameLevel = new GameLevel(new Coordinates(8, 10),levelListeners, playerTank);
        GameGraphics gameGraphics = new GameGraphics();
        gameGraphics.init();
        ActionGenerator actionGenerator = new ActionGenerator();

        AIControllerAdapter enemyController = new AIControllerAdapter(gameLevel, actionGenerator);

        gameLevel.add(playerTank);
        gameGraphics.addGameObject(playerTank, "images/tank_blue.png");
        actionGenerator.add(playerTank, InputControllerProvider.getKeyboardDefault());

        Obstacle tree = new Obstacle(new Coordinates(1, 3));
        gameLevel.add(tree);
        gameGraphics.addGameObject(tree, "images/greenTree.png");
        gameGraphics.moveRectanglesAtTileCenters();

        Tank enemy = new Tank(new Coordinates(4, 4), Direction.DOWN, 0.6f);
        gameLevel.add(enemy);
        gameGraphics.addGameObject(enemy, "images/tank_red.png");
        actionGenerator.add(enemy, enemyController.getController(enemy));

        return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
    }
}

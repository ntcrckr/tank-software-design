package ru.mipt.bit.platformer.level.generator;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.controller.input.DefaultKeyboardInputController;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;

public class DefaultLevelGenerator implements LevelGenerator {
    @Override
    public LevelInfo generate() {
        GameLevel gameLevel = new GameLevel(0, 9, 0, 7);
        GameGraphics gameGraphics = new GameGraphics();
        gameGraphics.init();
        ActionGenerator actionGenerator = new ActionGenerator();

        Tank playerTank = new Tank(new ArrayList<>(List.of(new Coordinates(1, 1))), Direction.RIGHT, 0.4f);
        gameLevel.add(playerTank);
        gameGraphics.addGameObject(playerTank, "images/tank_blue.png");
        actionGenerator.add(playerTank, new DefaultKeyboardInputController());

        Obstacle tree = new Obstacle(new ArrayList<>(List.of(new Coordinates(1, 3))));
        gameLevel.add(tree);
        gameGraphics.addGameObject(tree, "images/greenTree.png");
        gameGraphics.moveRectanglesAtTileCenters();

        return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
    }
}

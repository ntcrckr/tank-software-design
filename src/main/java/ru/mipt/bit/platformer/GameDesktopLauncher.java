package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.controller.input.DefaultKeyboardInputController;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.level.generator.SaveFileLevelGenerator;
import ru.mipt.bit.platformer.model.*;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameDesktopLauncher implements ApplicationListener {

    private ActionGenerator actionGenerator = new ActionGenerator();
    private GameLevel gameLevel = new GameLevel();
    private GameGraphics gameGraphics = new GameGraphics();

    private static class RandomCoordinatesGenerator {
        private final Random random = new Random();
        private final List<Coordinates> freeCoordinates = new ArrayList<>();

        public RandomCoordinatesGenerator() {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    freeCoordinates.add(new Coordinates(i, j));
                }
            }
        }

        public Coordinates getCoordinates() {
            if (freeCoordinates.isEmpty()) return null;
            Coordinates randomCoordinates = freeCoordinates.get(random.nextInt(freeCoordinates.size()));
            freeCoordinates.remove(randomCoordinates);
            return randomCoordinates;
        }
    }

    @Override
    public void create() {
        File file = new File("src/main/resources/mode");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int mode = scanner.nextInt();
        switch (mode) {
            case 1:
                basicCreate();
                break;
            case 2:
                fileCreate();
                break;
            case 3:
                randomCreate();
                break;
            default:
                throw new RuntimeException();
        }
    }

    private void basicCreate() {
        gameGraphics.init();
        Tank playerTank = new Tank(new Coordinates(1, 1), Direction.RIGHT, 0.4f);
        gameLevel.add(playerTank);
        gameGraphics.addGameObject(playerTank, "images/tank_blue.png");
        actionGenerator.add(playerTank, new DefaultKeyboardInputController());
        Obstacle tree = new Obstacle(new Coordinates(1, 3));
        gameLevel.add(tree);
        gameGraphics.addGameObject(tree, "images/greenTree.png");
        gameGraphics.moveRectanglesAtTileCenters();
    }

    private void randomCreate() {
        gameGraphics.init();
        RandomCoordinatesGenerator coordinatesGenerator = new RandomCoordinatesGenerator();
        Tank playerTank = new Tank(coordinatesGenerator.getCoordinates(), Direction.RIGHT, 0.4f);
        gameLevel.add(playerTank);
        gameGraphics.addGameObject(playerTank, "images/tank_blue.png");
        actionGenerator.add(playerTank, new DefaultKeyboardInputController());
        Obstacle tree = new Obstacle(coordinatesGenerator.getCoordinates());
        gameLevel.add(tree);
        gameGraphics.addGameObject(tree, "images/greenTree.png");
        gameGraphics.moveRectanglesAtTileCenters();
    }

    private void fileCreate() {
        LevelGenerator levelGenerator = new SaveFileLevelGenerator("src/main/resources/level.txt");
        LevelInfo levelInfo = levelGenerator.generate();
        gameLevel = levelInfo.getGameLevel();
        gameGraphics = levelInfo.getGameGraphics();
        actionGenerator = levelInfo.getActionGenerator();
    }

    @Override
    public void render() {
        gameGraphics.clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        Map<Movable, MoveAction> actionMap = actionGenerator.generateActions();

        gameLevel.applyActions(actionMap);

        gameLevel.updateState(deltaTime);

        gameGraphics.draw();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        gameGraphics.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}

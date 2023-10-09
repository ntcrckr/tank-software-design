package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.model.actions.MoveAction;
import ru.mipt.bit.platformer.model.actions.ActionGenerator;
import ru.mipt.bit.platformer.controller.DefaultKeyboardInputController;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.model.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class GameDesktopLauncher implements ApplicationListener {

    private static final String mode = "file";
    private ActionGenerator actionGenerator = new ActionGenerator();
    private final GameLevel gameLevel = new GameLevel();
    private final GameGraphics gameGraphics = new GameGraphics();

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
        switch (mode) {
            case "basic":
                basicCreate();
                break;
            case "random":
                randomCreate();
                break;
            case "file":
                fileCreate();
                break;
            default:
                break;
        }
    }

    private void basicCreate() {
        gameGraphics.init();
        Tank playerTank = new Tank(new Coordinates(1, 1), Direction.RIGHT, 0.4f);
        gameLevel.add(playerTank);
        gameGraphics.add(playerTank, "images/tank_blue.png");
        actionGenerator.add(playerTank, new DefaultKeyboardInputController());
        Obstacle tree = new Obstacle(new Coordinates(1, 3));
        gameLevel.add(tree);
        gameGraphics.add(tree, "images/greenTree.png");
        gameGraphics.moveRectanglesAtTileCenters();
    }

    private void randomCreate() {
        gameGraphics.init();
        RandomCoordinatesGenerator coordinatesGenerator = new RandomCoordinatesGenerator();
        Tank playerTank = new Tank(coordinatesGenerator.getCoordinates(), Direction.RIGHT, 0.4f);
        gameLevel.add(playerTank);
        gameGraphics.add(playerTank, "images/tank_blue.png");
        actionGenerator.add(playerTank, new DefaultKeyboardInputController());
        Obstacle tree = new Obstacle(coordinatesGenerator.getCoordinates());
        gameLevel.add(tree);
        gameGraphics.add(tree, "images/greenTree.png");
        gameGraphics.moveRectanglesAtTileCenters();
    }

    private void fileCreate() {
        gameGraphics.init();
        int y = 6;
        try {
            File file = new File("src/main/resources/level.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    switch (c) {
                        case 'T':
                            Obstacle tree = new Obstacle(new Coordinates(x, y));
                            gameLevel.add(tree);
                            gameGraphics.add(tree, "images/greenTree.png");
                            break;
                        case 'X':
                            Tank playerTank = new Tank(new Coordinates(x, y), Direction.RIGHT, 0.4f);
                            gameLevel.add(playerTank);
                            gameGraphics.add(playerTank, "images/tank_blue.png");
                            actionGenerator.add(playerTank, new DefaultKeyboardInputController());
                            break;
                        case '_':
                        default:
                            break;
                    }
                }
                y -= 1;
            }
            myReader.close();
            gameGraphics.moveRectanglesAtTileCenters();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
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

package ru.mipt.bit.platformer.level.generator.impl;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.controller.artificial.AIController;
import ru.mipt.bit.platformer.controller.input.InputControllerProvider;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


public class SaveFileLevelGenerator implements LevelGenerator {
    private final File saveFile;

    public SaveFileLevelGenerator(String saveFileName) {
        this.saveFile = new File(saveFileName);
    }

    @Override
    public LevelInfo generate(List<LevelListener> levelListeners) {
        Coordinates levelSize = getLevelWidthHeight();
        System.out.println(levelSize);
        return getLevelInfo(levelListeners, levelSize);
    }

    private Coordinates getLevelWidthHeight() {
        try(
                Scanner scanner = new Scanner(this.saveFile);
                Stream<String> fileStream = Files.lines(saveFile.toPath())
        ) {
            int width = 0;
            int height = (int) fileStream.count() - 1;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    width = Math.max(width, x);
                }
            }
            return new Coordinates(width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LevelInfo getLevelInfo(List<LevelListener> levelListeners, Coordinates levelSize) {
        GameLevel gameLevel = new GameLevel(levelSize, levelListeners);
        GameGraphics gameGraphics = new GameGraphics();
        gameGraphics.init();
        ActionGenerator actionGenerator = new ActionGenerator();

        try(Scanner scanner = new Scanner(this.saveFile)) {
            int y = levelSize.getY();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int x = 0; x < line.length(); x++) {
                    char notation = line.charAt(x);
                    handleNotation(notation, new Coordinates(x, y), gameLevel, gameGraphics, actionGenerator);
                }
                y -= 1;
            }
            gameGraphics.moveRectanglesAtTileCenters();
            return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleNotation(char c, Coordinates coordinates, GameLevel gameLevel, GameGraphics gameGraphics, ActionGenerator actionGenerator) {
        switch (c) {
            case '_' -> {
            }
            case 'T' -> {
                Obstacle tree = new Obstacle(coordinates);
                gameLevel.add(tree);
                gameGraphics.addGameObject(tree, "images/greenTree.png");
            }
            case 'X' -> {
                Tank player = new Tank(coordinates, Direction.RIGHT, 0.4f);
                gameLevel.add(player);
                gameGraphics.addGameObject(player, "images/tank_blue.png");
                actionGenerator.add(player, InputControllerProvider.getKeyboardDefault());
            }
            case 'E' -> {
                Tank enemy = new Tank(coordinates, Direction.DOWN, 0.6f);
                gameLevel.add(enemy);
                gameGraphics.addGameObject(enemy, "images/tank_red.png");
                actionGenerator.add(enemy, new AIController());
            }
        }
    }
}

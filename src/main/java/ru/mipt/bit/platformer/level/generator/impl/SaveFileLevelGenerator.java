package ru.mipt.bit.platformer.level.generator.impl;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.basics.Direction;
import ru.mipt.bit.platformer.controller.artificial.AIControllerAdapter;
import ru.mipt.bit.platformer.controller.input.InputControllerProvider;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.impl.SimpleMovable;
import ru.mipt.bit.platformer.model.impl.SimpleShooter;

import java.io.File;
import java.io.FileNotFoundException;
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
        Scanner scanner;
        try {
            scanner = new Scanner(this.saveFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Stream<String> fileStream;
        try {
            fileStream = Files.lines(saveFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int height = (int) fileStream.count() - 1;
        int width = 0;
        int y = height;

        GameObject player = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == 'X') {
                    player = new Tank(
                            new SimpleMovable(new Coordinates(x, y), Direction.RIGHT, 0.4f),
                            new SimpleShooter(1f)
                    );
                }
                width = Math.max(width, x);
            }
            y -= 1;
        }
        scanner.close();

        GameLevel gameLevel = new GameLevel(new Coordinates(width, height), levelListeners, player);

        GameGraphics gameGraphics = new GameGraphics();
        gameGraphics.init();
        ActionGenerator actionGenerator = new ActionGenerator();

        AIControllerAdapter enemyController = new AIControllerAdapter(gameLevel, actionGenerator);

        try {
            scanner = new Scanner(this.saveFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            fileStream = Files.lines(saveFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        y = (int) fileStream.count() - 1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                switch (c) {
                    case '_' -> {
                    }
                    case 'T' -> {
                        Obstacle tree = new Obstacle(new Coordinates(x, y));
                        gameLevel.add(tree);
                        gameGraphics.addGameObject(tree, "images/greenTree.png");
                    }
                    case 'X' -> {
                        Tank player_ = new Tank(
                                new SimpleMovable(new Coordinates(x, y), Direction.RIGHT, 0.4f),
                                new SimpleShooter(1f)
                        );
                        gameLevel.add(player_);
                        gameGraphics.addGameObject(player_, "images/tank_blue.png");
                        actionGenerator.add(player_, InputControllerProvider.getKeyboardDefault());
                    }
                    case 'E' -> {
                        Tank enemy = new Tank(
                                new SimpleMovable(new Coordinates(x, y), Direction.DOWN, 0.6f),
                                new SimpleShooter(1f)
                        );
                        gameLevel.add(enemy);
                        gameGraphics.addGameObject(enemy, "images/tank_red.png");
                        actionGenerator.add(enemy, enemyController.getController(enemy));
                    }
                }
            }
            y -= 1;
        }
        scanner.close();
        gameGraphics.moveRectanglesAtTileCenters();

        return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
    }
}

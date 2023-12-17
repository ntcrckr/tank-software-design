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
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.util.AssetMappings;
import ru.mipt.bit.platformer.util.GameObjectInitMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static ru.mipt.bit.platformer.util.GameObjectType.*;


public class SaveFileLevelGenerator implements LevelGenerator {
    private final File saveFile;

    public SaveFileLevelGenerator(String saveFileName) {
        this.saveFile = new File(saveFileName);
    }

    @Override
    public LevelInfo generate(GameObjectInitMap gameObjectInitMap, List<LevelListener> levelListeners) {
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

        Movable player = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == 'X') {
                    player = (Movable) gameObjectInitMap.getGameObject(PLAYER_TANK, new Coordinates(x, y));
                }
                width = Math.max(width, x);
            }
            y -= 1;
        }
        scanner.close();

        GameLevel gameLevel = new GameLevel(new Coordinates(width, height), levelListeners, player);

        GameGraphics gameGraphics = new GameGraphics(AssetMappings.graphicsPathMap);
        gameGraphics.init();
        gameLevel.addLevelListener(gameGraphics);

        ActionGenerator actionGenerator = new ActionGenerator();

        gameLevel.add(player);
        actionGenerator.add(player, InputControllerProvider.getKeyboardDefault());

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
                        GameObject tree = gameObjectInitMap.getGameObject(TREE, new Coordinates(x, y));
                        gameLevel.add(tree);
                    }
                    case 'X' -> {}
                    case 'E' -> {
                        Movable enemy = (Movable) gameObjectInitMap.getGameObject(ENEMY_TANK, new Coordinates(x, y));
                        gameLevel.add(enemy);
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

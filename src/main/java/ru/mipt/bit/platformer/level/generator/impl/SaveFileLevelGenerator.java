package ru.mipt.bit.platformer.level.generator.impl;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.graphics.GUI;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.util.AssetMappings;
import ru.mipt.bit.platformer.util.ControllerMappings;
import ru.mipt.bit.platformer.util.GameObjectInitMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static ru.mipt.bit.platformer.util.GameEntityType.*;


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

        GUI gui = new GUI();

        GameGraphics gameGraphics = new GameGraphics(AssetMappings.graphicsPathMap, gui);
        gameGraphics.init();
        gameLevel.addLevelListener(gameGraphics);

        ActionGenerator actionGenerator = new ActionGenerator(new ControllerMappings(gameLevel).controllerProviderMap);
        gameLevel.addLevelListener(actionGenerator);

        gameLevel.add(gui);
        gameLevel.add(player);

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
                    case '_', 'X' -> {
                    }
                    case 'T' -> {
                        GameObject tree = gameObjectInitMap.getGameObject(TREE, new Coordinates(x, y));
                        gameLevel.add(tree);
                    }
                    case 'E' -> {
                        Movable enemy = (Movable) gameObjectInitMap.getGameObject(ENEMY_TANK, new Coordinates(x, y));
                        gameLevel.add(enemy);
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

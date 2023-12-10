package ru.mipt.bit.platformer.level.generator.impl;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.LevelGenerator;
import ru.mipt.bit.platformer.level.generator.LevelInfo;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
        List<GameObject> gameObjects = new ArrayList<>();
        GameGraphics gameGraphics = new GameGraphics();
        gameGraphics.init();
        ActionGenerator actionGenerator = new ActionGenerator();

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

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                System.out.printf("%d %d: %c\n", x, y, c);
                SaveFileGameObject saveFileGameObject = SaveFileGameObject.byNotation(c);

                if (saveFileGameObject.isProvidable()) {
                    Coordinates coordinates = new Coordinates(x, y);
                    GameObject gameObject = saveFileGameObject.provideObject(coordinates);
                    String texturePath = saveFileGameObject.provideTexturePath();

                    gameObjects.add(gameObject);
                    gameGraphics.addGameObject(gameObject, texturePath);

                    if (saveFileGameObject.isControllable()) {
                        actionGenerator.add((Movable) gameObject, saveFileGameObject.getController());
                    }
                }
                width = Math.max(width, x);
            }
            y -= 1;
        }
        scanner.close();
        gameGraphics.moveRectanglesAtTileCenters();

        GameLevel gameLevel = new GameLevel(new Coordinates(width, height), levelListeners);
        return new LevelInfo(gameLevel, gameGraphics, actionGenerator);
    }
}

package ru.mipt.bit.platformer.level.generator;

import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.basics.Coordinates;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;


public class SaveFileLevelGenerator implements LevelGenerator {
    private final File saveFile;

    public SaveFileLevelGenerator(String saveFileName) {
        this.saveFile = new File(saveFileName);
    }

    @Override
    public LevelInfo generate() {
        GameLevel gameLevel = new GameLevel();
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
        int y = (int) fileStream.count() - 1;

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

                    gameLevel.add(gameObject);
                    gameGraphics.addGameObject(gameObject, texturePath);

                    if (saveFileGameObject.isControllable()) {
                        actionGenerator.add((Movable) gameObject, saveFileGameObject.getController());
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

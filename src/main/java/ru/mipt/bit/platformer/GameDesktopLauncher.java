package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.generator.*;
import ru.mipt.bit.platformer.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameDesktopLauncher implements ApplicationListener {

    private ActionGenerator actionGenerator;
    private GameLevel gameLevel;
    private GameGraphics gameGraphics;

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
        scanner.close();
        LevelGenerator levelGenerator = switch (mode) {
            case 1 -> new DefaultLevelGenerator();
            case 2 -> new RandomLevelGenerator(10, 8, 10);
            case 3 -> new SaveFileLevelGenerator("src/main/resources/level.txt");
            default -> throw new RuntimeException();
        };
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

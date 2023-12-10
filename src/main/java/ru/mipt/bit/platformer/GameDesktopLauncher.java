package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.actions.Action;
import ru.mipt.bit.platformer.actions.MoveAction;
import ru.mipt.bit.platformer.actions.ActionGenerator;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.CollisionLevel;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.level.generator.*;
import ru.mipt.bit.platformer.level.generator.impl.DefaultLevelGenerator;
import ru.mipt.bit.platformer.level.generator.impl.RandomLevelGenerator;
import ru.mipt.bit.platformer.level.generator.impl.SaveFileLevelGenerator;
import ru.mipt.bit.platformer.model.*;
import ru.mipt.bit.platformer.util.GameMode;

import java.util.*;

public class GameDesktopLauncher implements ApplicationListener {

    private ActionGenerator actionGenerator;
    private GameLevel gameLevel;
    private GameGraphics gameGraphics;

    @Override
    public void create() {
        LevelGenerator levelGenerator = switch (GameMode.getGameMode()) {
            case DEFAULT -> new DefaultLevelGenerator();
            case RANDOM -> new RandomLevelGenerator(10, 8, 10);
            case FROM_FILE -> new SaveFileLevelGenerator("src/main/resources/level.txt");
        };
        List<LevelListener> levelListeners = new ArrayList<>();
        LevelInfo levelInfo = levelGenerator.generate(levelListeners);
        gameLevel = levelInfo.getGameLevel();
        gameGraphics = levelInfo.getGameGraphics();
        actionGenerator = levelInfo.getActionGenerator();
    }

    @Override
    public void render() {
        gameGraphics.clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        Map<GameObject, Action> actionMap = actionGenerator.generateActions();

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

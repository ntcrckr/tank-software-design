package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.controller.Action;
import ru.mipt.bit.platformer.controller.ActionGenerator;
import ru.mipt.bit.platformer.controller.ControllerType;
import ru.mipt.bit.platformer.view.GameGraphics;
import ru.mipt.bit.platformer.model.*;

import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private ActionGenerator actionGenerator;
    private final GameLevel gameLevel = new GameLevel();
    private final GameGraphics gameGraphics = new GameGraphics();

    @Override
    public void create() {
        gameGraphics.init();
        Tank playerTank = new Tank(new Coordinates(1, 1), Direction.RIGHT, 0.4f);
        gameLevel.add(playerTank);
        gameGraphics.add(playerTank, "images/tank_blue.png");
        Obstacle tree = new Obstacle(new Coordinates(1, 3));
        gameLevel.add(tree);
        gameGraphics.add(tree, "images/greenTree.png");

        gameGraphics.moveRectanglesAtTileCenters();

        actionGenerator = new ActionGenerator();
        actionGenerator.add(playerTank, ControllerType.INPUT_CONTROLLER);
    }

    @Override
    public void render() {
        clearScreen();

        float deltaTime = Gdx.graphics.getDeltaTime();

        Map<Controllable, Action> actionMap = actionGenerator.generateActions();

        gameLevel.applyActions(actionMap);

        gameLevel.updateState(deltaTime);

        gameGraphics.draw();
    }

    private static void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
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

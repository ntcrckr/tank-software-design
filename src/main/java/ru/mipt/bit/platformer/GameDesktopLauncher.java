package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private GameObjectGraphics playerTankGraphics;
    private final List<GameObjectGraphics> obstacleGraphics = new ArrayList<>();
    private final List<GameObjectGraphics> enemyTankGraphics = new ArrayList<>();

    private Tank playerTank;
    private final List<Tank> enemyTanks = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
    private InputController inputController;

    @Override
    public void create() {
        playerTank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, 0.4f);
        obstacles.add(new Obstacle(new GridPoint2(1, 3)));
        inputController = new InputController();
        initInputController();

        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        playerTankGraphics = new GameObjectGraphics(new Texture("images/tank_blue.png"));

        obstacleGraphics.add(new GameObjectGraphics(new Texture("images/greenTree.png")));
        for (int i = 0; i < obstacles.size(); i++) {
            moveRectangleAtTileCenter(groundLayer, obstacleGraphics.get(i).getRectangle(), obstacles.get(i).getCoordinates());
        }
    }

    private void initInputController() {
        inputController.addMapping(UP, Direction.UP);
        inputController.addMapping(W, Direction.UP);
        inputController.addMapping(LEFT, Direction.LEFT);
        inputController.addMapping(A, Direction.LEFT);
        inputController.addMapping(DOWN, Direction.DOWN);
        inputController.addMapping(S, Direction.DOWN);
        inputController.addMapping(RIGHT, Direction.RIGHT);
        inputController.addMapping(D, Direction.RIGHT);
    }

    @Override
    public void render() {
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        Direction direction = inputController.getDirection();

        if (direction != null) {
            tryToMove(playerTank, direction);
        }
        playerTank.updateState(deltaTime);

        for (Tank enemyTank :
                enemyTanks) {
            Direction enemyDirection = determineEnemyDirection(enemyTank);
            if (enemyDirection != null) {
                tryToMove(enemyTank, enemyDirection);
            }
            enemyTank.updateState(deltaTime);
        }

        drawTankGraphics();

        renderGame();
    }

    private Direction determineEnemyDirection(Tank enemyTank) {
        // random for now, in future will determine enemy's direction according to some algorithm, maybe should be inside EnemyTank class
        return Direction.UP;
    }

    private void tryToMove(Tank tank, Direction direction) {
        if (!tank.isMoving()) {
            List<Collidable> obstacleCollidables = obstacles.stream().map(Obstacle::getCollidable).collect(Collectors.toList());
            if (!tank.checkCollision(direction, obstacleCollidables)) {
                tank.startMovement(direction);
            }
            tank.changeDirection(direction);
        }
    }

    private void drawTankGraphics() {
        // calculate interpolated player screen coordinates
        tileMovement.moveRectangleBetweenTileCenters(playerTankGraphics.getRectangle(), playerTank.getCoordinates(), playerTank.getDestinationCoordinates(), playerTank.getMovementProgress());

        for (Tank enemyTank :
                enemyTanks) {
            Rectangle enemyRectangle = getEnemyRectangle();
            tileMovement.moveRectangleBetweenTileCenters(enemyRectangle, enemyTank.getCoordinates(), enemyTank.getDestinationCoordinates(), enemyTank.getMovementProgress());
        }
    }

    private Rectangle getEnemyRectangle() {
        // temp Rectangle because enemies are not yet implemented
        return new Rectangle();
    }

    private void renderGame() {
        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(batch, playerTankGraphics.getTextureRegion(), playerTankGraphics.getRectangle(), playerTank.getRotation());

        // also render enemy tanks graphics, but there are none for now

        // render tree obstacles
        for (GameObjectGraphics obstacleGraphic:
                obstacleGraphics) {
            drawTextureRegionUnscaled(batch, obstacleGraphic.getTextureRegion(), obstacleGraphic.getRectangle(), 0f);
        }

        // submit all drawing requests
        batch.end();
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
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        for (GameObjectGraphics obstacleGraphic :
                obstacleGraphics) {
            obstacleGraphic.getTexture().dispose();
        }
        // same for enemies, but there are none now
        playerTankGraphics.getTexture().dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}

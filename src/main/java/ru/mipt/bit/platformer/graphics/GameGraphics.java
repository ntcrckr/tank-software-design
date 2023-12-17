package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.model.GameEntity;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.util.Converter;
import ru.mipt.bit.platformer.util.GameEntityType;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.actions.GUIToggleAction.HEALTH;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameGraphics implements LevelListener {
    private final List<Graphics> movableGraphics = new ArrayList<>();
    private final List<Graphics> otherGraphics = new ArrayList<>();
    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;
    private final Map<GameEntityType, String> graphicsPathMap;
    private final GUI gui;

    public GameGraphics(Map<GameEntityType, String> graphicsPathMap, GUI gui) {
        this.graphicsPathMap = graphicsPathMap;
        this.gui = gui;
    }

    @Override
    public void onAdd(GameEntity gameEntity) {
        if (!(gameEntity instanceof GameObject gameObject)) return;
        GameEntityType gameObjectType = gameObject.getGameObjectType();
        String graphicsPath = graphicsPathMap.get(gameObjectType);
        switch (gameObjectType) {
            case PLAYER_TANK, ENEMY_TANK -> {
                HealthBar graphics = new HealthBar(new GameObjectGraphics(batch, graphicsPath, (Movable) gameEntity));
                movableGraphics.add(graphics);
                gui.add(HEALTH, graphics);
            }
            case BULLET -> movableGraphics.add(
                    new GameObjectGraphics(batch, graphicsPath, (Movable) gameEntity)
            );
            case TREE -> otherGraphics.add(new GameObjectGraphics(batch, graphicsPath, gameObject));
        }
    }

    @Override
    public void onRemove(GameEntity gameEntity) {
        if (!(gameEntity instanceof GameObject gameObject)) return;
        GameEntityType gameObjectType = gameObject.getGameObjectType();
        switch (gameObjectType) {
            case PLAYER_TANK, ENEMY_TANK -> {
                Graphics graphics = movableGraphics.stream()
                        .filter(gog -> gog.getDrawable() == gameObject)
                        .findFirst().orElseThrow();
                movableGraphics.remove(graphics);
                if (graphics instanceof ToggleableGraphics toggleableGraphics) {
                    gui.remove(HEALTH, toggleableGraphics);
                }
            }
            case BULLET -> {
                Graphics graphics = movableGraphics.stream()
                        .filter(gog -> gog.getDrawable() == gameObject)
                        .findFirst().orElseThrow();
                movableGraphics.remove(graphics);
            }
            case TREE -> {
                Graphics graphics = otherGraphics.stream()
                        .filter(gog -> gog.getDrawable() == gameObject)
                        .findFirst().orElseThrow();
                otherGraphics.remove(graphics);
            }
        }
    }

    public void init() {
        batch = new SpriteBatch();
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.linear);
    }

    public void moveRectanglesAtTileCenters() {
        Stream.concat(otherGraphics.stream(), movableGraphics.stream()).forEach(
                gameObjectGraphics -> moveRectangleAtTileCenter(
                        groundLayer,
                        gameObjectGraphics.getRectangle(),
                        Converter.coordinatesToGridPoint2(gameObjectGraphics.getDrawable().getCoordinates())
                )
        );
    }

    public void draw() {
        for (Graphics movableGraphic : movableGraphics) {
            Movable drawable = (Movable) movableGraphic.getDrawable();
            tileMovement.moveRectangleBetweenTileCenters(
                    movableGraphic.getRectangle(),
                    Converter.coordinatesToGridPoint2(drawable.getCoordinates()),
                    Converter.coordinatesToGridPoint2(drawable.getDestinationCoordinates()),
                    drawable.getMovementProgress()
            );
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        Stream.concat(otherGraphics.stream(), movableGraphics.stream()).forEach(Graphics::draw);

        // submit all drawing requests
        batch.end();
    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public void dispose() {
        for (Graphics graphic : otherGraphics) {
            graphic.getTexture().dispose();
        }
        level.dispose();
        batch.dispose();
    }
}

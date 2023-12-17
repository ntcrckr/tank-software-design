package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.level.LevelListener;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.util.Converter;
import ru.mipt.bit.platformer.util.GameObjectType;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameGraphics implements LevelListener {
    private final List<GameObjectGraphics<Movable>> movableGraphics = new ArrayList<>();
    private final List<GameObjectGraphics<GameObject>> otherGraphics = new ArrayList<>();
    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;
    private final Map<GameObjectType, String> graphicsPathMap;

    public GameGraphics(Map<GameObjectType, String> graphicsPathMap) {
        this.graphicsPathMap = graphicsPathMap;
    }

    @Override
    public void onAdd(GameObject gameObject) {
        GameObjectType gameObjectType = gameObject.getGameObjectType();
        String graphicsPath = graphicsPathMap.get(gameObjectType);
        switch (gameObjectType) {
            case PLAYER_TANK, ENEMY_TANK, BULLET -> movableGraphics.add(new GameObjectGraphics<>(new Texture(graphicsPath), (Movable) gameObject));
            case TREE -> otherGraphics.add(new GameObjectGraphics<>(new Texture(graphicsPath), gameObject));
        }
    }

    @Override
    public void onRemove(GameObject gameObject) {
        GameObjectType gameObjectType = gameObject.getGameObjectType();
        switch (gameObjectType) {
            case PLAYER_TANK, ENEMY_TANK, BULLET -> {
                GameObjectGraphics<Movable> gameObjectGraphics = movableGraphics.stream()
                        .filter(gog -> gog.getDrawable() == gameObject)
                        .findFirst().orElseThrow();
                movableGraphics.remove(gameObjectGraphics);
            }
            case TREE -> {
                GameObjectGraphics<GameObject> gameObjectGraphics = otherGraphics.stream()
                        .filter(gog -> gog.getDrawable() == gameObject)
                        .findFirst().orElseThrow();
                otherGraphics.remove(gameObjectGraphics);
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
        for (GameObjectGraphics<Movable> movableGraphic : movableGraphics) {
            tileMovement.moveRectangleBetweenTileCenters(
                    movableGraphic.getRectangle(),
                    Converter.coordinatesToGridPoint2(movableGraphic.getDrawable().getCoordinates()),
                    Converter.coordinatesToGridPoint2(movableGraphic.getDrawable().getDestinationCoordinates()),
                    movableGraphic.getDrawable().getMovementProgress()
            );
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        Stream.concat(otherGraphics.stream(), movableGraphics.stream()).forEach(
                gameObjectGraphics -> drawTextureRegionUnscaled(
                        batch,
                        gameObjectGraphics.getTextureRegion(),
                        gameObjectGraphics.getRectangle(),
                        gameObjectGraphics.getDrawable().getDirection().getRotation()
                )
        );

        // submit all drawing requests
        batch.end();
    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    public void dispose() {
        for (GameObjectGraphics<GameObject> graphic : otherGraphics) {
            graphic.getTexture().dispose();
        }
        level.dispose();
        batch.dispose();
    }
}

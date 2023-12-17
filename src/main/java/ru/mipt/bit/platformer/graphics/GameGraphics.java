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
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Movable;
import ru.mipt.bit.platformer.util.Converter;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameGraphics {
    private final List<GameObjectGraphics<GameObject>> graphics = new ArrayList<>();
    private final List<GameObjectGraphics<Movable>> movableGraphics = new ArrayList<>();
    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;

    public GameGraphics() {
    }

    public void init() {
        batch = new SpriteBatch();
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void addGameObject(GameObject gameObject, String texturePath) {
        if (gameObject instanceof Movable) {
            movableGraphics.add(new GameObjectGraphics<>(new Texture(texturePath), (Movable) gameObject));
        } else {
            graphics.add(new GameObjectGraphics<>(new Texture(texturePath), gameObject));
        }
    }

    public void moveRectanglesAtTileCenters() {
        Stream.concat(graphics.stream(), movableGraphics.stream()).forEach(
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

        Stream.concat(graphics.stream(), movableGraphics.stream()).forEach(
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
        for (GameObjectGraphics<GameObject> graphic : graphics) {
            graphic.getTexture().dispose();
        }
        level.dispose();
        batch.dispose();
    }
}

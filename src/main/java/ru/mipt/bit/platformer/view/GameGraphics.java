package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.model.Drawable;
import ru.mipt.bit.platformer.util.Converter;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameGraphics {
    private final List<GameObjectGraphics> graphics = new ArrayList<>();
    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;

    public void init() {
        batch = new SpriteBatch();
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void add(Drawable drawable, String texturePath) {
        graphics.add(new GameObjectGraphics(new Texture(texturePath), drawable));
    }

    public void moveRectanglesAtTileCenters() {
        for (GameObjectGraphics graphic : graphics) {
            moveRectangleAtTileCenter(
                    groundLayer,
                    graphic.getRectangle(),
                    Converter.coordinatesToGridPoint2(graphic.getDrawable().getCoordinates())
            );
        }
    }

    public void draw() {
        for (GameObjectGraphics graphic : graphics) {
            tileMovement.moveRectangleBetweenTileCenters(
                    graphic.getRectangle(),
                    Converter.coordinatesToGridPoint2(graphic.getDrawable().getCoordinates()),
                    Converter.coordinatesToGridPoint2(graphic.getDrawable().getDestinationCoordinates()),
                    graphic.getDrawable().getMovementProgress()
            );
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        for (GameObjectGraphics graphic : graphics) {
            drawTextureRegionUnscaled(
                    batch,
                    graphic.getTextureRegion(),
                    graphic.getRectangle(),
                    graphic.getDrawable().getRotation()
            );
        }

        // submit all drawing requests
        batch.end();
    }

    public void dispose() {
        for (GameObjectGraphics graphic : graphics) {
            graphic.getTexture().dispose();
        }
        level.dispose();
        batch.dispose();
    }
}

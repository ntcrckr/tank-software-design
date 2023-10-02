package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.Texture;
import ru.mipt.bit.platformer.model.Drawable;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

import java.util.ArrayList;
import java.util.List;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class GameGraphics {
    private final List<GameObjectGraphics> graphics = new ArrayList<>();

    public GameGraphics() {
        
    }

    public void add(Drawable drawable, String texturePath) {
        graphics.add(new GameObjectGraphics(new Texture(texturePath), drawable));
    }

    public void moveRectaglesAtTileCenters() {
        for (GameObjectGraphics graphic : graphics) {
            moveRectangleAtTileCenter(groundLayer, graphic.getRectangle(), graphic.getDrawable().getCoordinates());
        }
    }
}

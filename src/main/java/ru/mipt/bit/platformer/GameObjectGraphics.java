package ru.mipt.bit.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class GameObjectGraphics {
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;

    public GameObjectGraphics(Texture texture) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.textureRegion);
    }

    public Texture getTexture() {
        return texture;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}

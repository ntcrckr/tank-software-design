package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class GameObjectGraphics <T extends GameObject> {
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final T gameObject;

    public GameObjectGraphics(Texture texture, T gameObject) {
        this.texture = texture;
        this.gameObject = gameObject;
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

    public T getDrawable() {
        return gameObject;
    }
}

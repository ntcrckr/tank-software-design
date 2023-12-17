package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.GameObjectState;
import ru.mipt.bit.platformer.model.HasStates;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GameObjectGraphics implements Graphics {
    private final Batch batch;
    private Texture texture;
    private TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final GameObject gameObject;
    private GameObjectState savedGameObjectState = null;

    public GameObjectGraphics(Batch batch, String texturePath, GameObject gameObject) {
        this.batch = batch;
        this.texture = new Texture(texturePath);
        this.gameObject = gameObject;
        this.textureRegion = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.textureRegion);
    }

    @Override
    public void draw() {
        checkState();
        drawTextureRegionUnscaled(
                batch,
                textureRegion,
                rectangle,
                gameObject.getDirection().getRotation()
        );
    }

    private void checkState() {
        if (gameObject instanceof HasStates hasStates) {
            GameObjectState gameObjectState = hasStates.getState();
            if (savedGameObjectState == gameObjectState) return;
            String texturePath = TankStateTextureMap.getTexturePath(gameObject.getGameObjectType(), gameObjectState);
            texture.dispose();
            texture = new Texture(texturePath);
            textureRegion = new TextureRegion(texture);
            savedGameObjectState = gameObjectState;
        }
    }

    public Batch getBatch() {
        return batch;
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public GameObject getDrawable() {
        return gameObject;
    }
}

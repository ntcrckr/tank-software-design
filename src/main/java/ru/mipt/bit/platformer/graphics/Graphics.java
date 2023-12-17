package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;

public interface Graphics {
    Batch getBatch();

    Texture getTexture();

    TextureRegion getTextureRegion();

    Rectangle getRectangle();

    GameObject getDrawable();
}

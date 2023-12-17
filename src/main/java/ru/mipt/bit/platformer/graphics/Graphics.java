package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;

public interface Graphics {
    void draw();

    Batch getBatch();

    Texture getTexture();

    Rectangle getRectangle();

    GameObject getDrawable();
}

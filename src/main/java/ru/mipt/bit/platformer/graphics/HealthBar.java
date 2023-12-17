package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.Hittable;
import ru.mipt.bit.platformer.util.GdxGameUtils;

import static com.badlogic.gdx.graphics.Color.GREEN;
import static com.badlogic.gdx.graphics.Color.RED;

public class HealthBar implements Graphics, ToggleableGraphics {
    private final GameObjectGraphics gameObjectGraphics;
    private boolean visible = true;

    public HealthBar(GameObjectGraphics gameObjectGraphics) {
        this.gameObjectGraphics = gameObjectGraphics;
    }

    private void drawHealthBar(Batch batch, Rectangle rectangle, float health, float maxHealth) {
        TextureRegion healthBgBar = createBar(maxHealth, RED);
        TextureRegion healthLeftBar = createBar(health, GREEN);
        Rectangle hpRectangle = new Rectangle(rectangle);
        hpRectangle.y += 90;
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthBgBar, hpRectangle, 0);
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthLeftBar, hpRectangle, 0);
    }

    private static TextureRegion createBar(float health, Color color) {
        int width = Math.round(90 * health / 100);
        Pixmap pixmap = new Pixmap(width, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, 20);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegion(texture);
    }

    @Override
    public void draw() {
        if (visible) {
            drawHealthBar(
                    gameObjectGraphics.getBatch(),
                    gameObjectGraphics.getRectangle(),
                    ((Hittable) gameObjectGraphics.getDrawable()).getHealth(),
                    ((Hittable) gameObjectGraphics.getDrawable()).getMaxHealth()
            );
        }
        gameObjectGraphics.draw();
    }

    @Override
    public Batch getBatch() {
        return gameObjectGraphics.getBatch();
    }

    @Override
    public Texture getTexture() {
        return gameObjectGraphics.getTexture();
    }

    @Override
    public Rectangle getRectangle() {
        return gameObjectGraphics.getRectangle();
    }

    @Override
    public GameObject getDrawable() {
        return gameObjectGraphics.getDrawable();
    }

    @Override
    public void toggle() {
        visible = !visible;
    }
}

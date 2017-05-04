package xyz.tdt4240.breakin.game.sprites;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Interface for renderable objects
 */
public interface Renderable {

    void render(SpriteBatch sb);

    void dispose();

}

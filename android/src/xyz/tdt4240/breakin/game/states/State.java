package xyz.tdt4240.breakin.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.orhanobut.logger.Logger;

import xyz.tdt4240.breakin.application.BreakInApplication;
import xyz.tdt4240.breakin.application.ScreenHandler;

/**
 * Superior abstract class for all states
 */
public abstract class State {

    protected GameStateManager gsm;

    public static final int SCREEN_WIDTH;

    public static final int SCREEN_HEIGHT;

    static {
        SCREEN_WIDTH = ScreenHandler.getWidth();
        SCREEN_HEIGHT = ScreenHandler.getHeight();
    }

    public State(GameStateManager gsm){
        this.gsm = gsm;
    }

    //Deal with user input
    protected abstract void handleInput();

    //Update dt = delta time
    //Physics stuff and calculations should be done here
    public abstract void update(float dt);

    //Rendering of objects should be done here
    public abstract void render(SpriteBatch sb);

    //Dispose of all textures / sprites to prevent memory leaks
    public abstract void dispose();

}

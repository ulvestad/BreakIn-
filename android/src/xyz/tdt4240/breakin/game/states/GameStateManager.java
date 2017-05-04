package xyz.tdt4240.breakin.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Manages different GameStates
 */
public class GameStateManager {

    private ConcurrentLinkedQueue<State> states;

    public GameStateManager(){
        states = new ConcurrentLinkedQueue<>();
    }

    public void add(State state){
        states.add(state);
    }

    public void remove(){
        states.remove().dispose();
    }

    public void set(State state){
        State oldState = states.peek();
        add(state);
        oldState.dispose();
        states.remove(oldState);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

    public void removeAll(){

        while(!states.isEmpty())
            remove();

    }

}
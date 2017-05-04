package xyz.tdt4240.breakin.game.sprites.bricks;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.orhanobut.logger.Logger;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.SoundHandler;
import xyz.tdt4240.breakin.game.movement_patterns.CircularMovementPattern;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.sprites.Renderable;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Superclass for all variations of bricks
 */
public abstract class Brick implements Renderable {

    protected Vector2 position;

    protected GameState gameState;

    private float angle;

    protected Texture brickTexture;

    protected Polygon brickBounds;

    protected int brickHealth;

    protected int brickScore;

    protected int brickWidth;

    protected int brickHeight;

    protected boolean isRemoved = false;

    private Brick(){}

    public Brick(GameState gameState){
        this.gameState = gameState;
        brickHealth = Constants.DEFAULT_BRICK_HEALTH;
        brickScore = Constants.BRICK_SCORE_DEFAULT;
        brickWidth = Constants.BRICK_WIDTH;
        brickHeight = Constants.BRICK_HEIGHT;

        position = new Vector2(Constants.DEFAULT_BRICK_X, Constants.DEFAULT_BRICK_Y);
        brickBounds = new Polygon();
        brickBounds.setPosition(position.x,position.y);
        brickBounds.setVertices(Constants.DEFAULT_BRICK_VERTICES);
    }

    public Polygon getBrickBounds(){
        return brickBounds;
    }

    /**
     * Sets position for brick
     * @param x
     * @param y
     */
    public void setPosition(float x, float y){
        position.set(x, y);
        brickBounds.setPosition(position.x,position.y);
    }
    public void setRotation(float angle){
        this.angle = angle;
        brickBounds.setRotation(this.angle);
    }
    @Override
    public void render(SpriteBatch sb) {
        if (!isRemoved) {
            sb.draw(new TextureRegion(brickTexture), position.x, position.y, 0, 0, brickWidth, brickHeight, 1f, 1f, angle);
        }
    }

    public int getHealth(){
        return brickHealth;
    }


    public int getScore(){
        return brickScore;
    }


    public void hit(Ball ball){
        brickHealth--;
    }

    public Vector2 getPosition() {return position;}


    public void onBreak(Ball ball){
        SoundHandler.getInstance().playBrickBreakSound();
    }

    public void remove(){
        isRemoved = true;
        dispose();
    }

    public boolean isRemoved(){
        return isRemoved;
    }



    @Override
    public void dispose(){
        brickTexture.dispose();
    }


}

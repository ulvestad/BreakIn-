package xyz.tdt4240.breakin.game.sprites;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Timer;
import java.util.TimerTask;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.ScreenHandler;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Describes the complete state of a paddle
 */
public class Paddle implements Renderable, Updateable {

    private Vector2 position;

    private Texture normalPaddleTexture;

    private Texture widePaddleTexture;

    private TextureRegion paddleTextureRegion;

    private GameState gameState;

    private Polygon paddleBounds;

    private volatile boolean isSizeChanged;

    private Timer timer;

    private int playerId;

    public Paddle(GameState gameState, int playerId) {

        this.gameState = gameState;
        this.playerId = playerId;

        if(playerId == Constants.PLAYER_1){
            normalPaddleTexture = new Texture(Constants.TEXTURE_PADDLE_BLUE);
            widePaddleTexture = new Texture(Constants.TEXTURE_PADDLE_BLUE_WIDE);
        }else{
            normalPaddleTexture = new Texture(Constants.TEXTURE_PADDLE_RED);
            widePaddleTexture = new Texture(Constants.TEXTURE_PADDLE_RED_WIDE);
        }

        paddleTextureRegion = new TextureRegion(normalPaddleTexture);
        paddleTextureRegion.flip(false, playerId == Constants.PLAYER_2);

        position = new Vector2();
        paddleBounds = new Polygon(Constants.PADDLE_VERTICES);

        placeAtStartPosition();

    }

    public void placeAtStartPosition(){

        float startX;
        float startY;

        startX = (ScreenHandler.getWidth() / 2) - (Constants.PADDLE_WIDTH / 2);

        if(playerId == Constants.PLAYER_1)
            startY = Constants.PADDLE_VERTICAL_OFFSET;
        else
            startY = ScreenHandler.getHeight() - Constants.PADDLE_VERTICAL_OFFSET * 2;

        position.set(startX, startY);
        paddleBounds.setPosition(startX, startY);
    }


    public void changeWidthFor(int width, long milliseconds){

        if(!isSizeChanged){
            isSizeChanged = true;

            paddleTextureRegion.setTexture(widePaddleTexture);

            float[] largePaddleVerticies = new float[]{
                    0,0,
                    width,0,
                    width,Constants.PADDLE_HEIGHT,
                    0,Constants.PADDLE_HEIGHT
            };

            paddleBounds.setVertices(largePaddleVerticies);

            timer = new Timer();
            timer.schedule(new ResetSizeTask(), milliseconds);
        }

    }

    public void setXPos(float x){
        position.x = x;
        paddleBounds.setPosition(position.x,position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Polygon getPaddleBounds(){
        return paddleBounds;
    }

    public int getPlayerId(){
        return playerId;
    }

    @Override
    public void render(SpriteBatch sb) {

        if(isSizeChanged)
            sb.draw(paddleTextureRegion, position.x, position.y, Constants.BIG_PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
        else
            sb.draw(paddleTextureRegion, position.x, position.y, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);

    }

    @Override
    public void update(float dt) {
        paddleBounds.setPosition(position.x,position.y);
    }

    @Override
    public void dispose() {
        normalPaddleTexture.dispose();
        widePaddleTexture.dispose();
    }

    private class ResetSizeTask extends TimerTask {
        public void run(){

            isSizeChanged = false;

            paddleTextureRegion.setTexture(normalPaddleTexture);
            paddleBounds.setVertices(Constants.PADDLE_VERTICES);

        }
    }


}

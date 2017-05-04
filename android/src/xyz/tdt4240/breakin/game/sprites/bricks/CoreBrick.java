package xyz.tdt4240.breakin.game.sprites.bricks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.SoundHandler;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.states.GameState;
import xyz.tdt4240.breakin.game.states.State;

/**
 * Middle brick that ends the game when hit and all bricks are cleared
 */
public class CoreBrick extends Brick {

    public CoreBrick(GameState gameState) {
        super(gameState);

        brickScore = Constants.BRICK_SCORE_CORE;
        brickTexture = new Texture(Constants.TEXTURE_BRICK_CORE1);

        brickWidth = Constants.CORE_BRICK_WIDTH;
        brickHeight = Constants.CORE_BRICK_HEIGHT;

        brickBounds.setVertices(new float[]{
                0,0,
                brickWidth, 0,
                brickWidth, brickHeight,
                0, brickHeight,
        });
    }

    @Override
    public void hit(Ball ball){

        if(gameState.getRemainingBricks() == 1)
            brickHealth--;

    }

    @Override
    public void onBreak(Ball ball){}


}

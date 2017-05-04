package xyz.tdt4240.breakin.game.sprites.bricks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.movement_patterns.CircularMovementPattern;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Makes the players paddle bigger when hit
 */
public class BiggerPaddleBrick extends Brick {

    public BiggerPaddleBrick(GameState gameState){
        super(gameState);

        brickScore = Constants.BRICK_SCORE_BIGGER_PADDLE;
        brickTexture = new Texture(Constants.TEXTURE_BRICK_BIGGER_PADDLE);

    }

    @Override
    public void hit(Ball ball) {
        brickHealth--;
    }

    @Override
    public void onBreak(Ball ball) {
        super.onBreak(ball);
        gameState.changePaddleWidth(ball.getPlayerId(), Constants.BIG_PADDLE_WIDTH, Constants.BIG_PADDLE_TIME);
    }


}

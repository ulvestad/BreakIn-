package xyz.tdt4240.breakin.game.sprites.bricks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Timer;
import java.util.TimerTask;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.movement_patterns.CircularMovementPattern;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Increases ball velocity when hit
 */
public class SpeedBrick extends Brick {

    //Should not really be static since the speed can be applied to different players and different balls
    private static volatile boolean isSpeedChanged;

    private Timer timer;

    private Ball ball;

    public SpeedBrick(GameState gameState){
        super(gameState);

        brickScore = Constants.BRICK_SCORE_SPEED;
        brickTexture = new Texture(Constants.TEXTURE_BRICK_SPEED);

    }

    @Override
    public void onBreak(Ball ball) {
        super.onBreak(ball);

        if(!isSpeedChanged){
            isSpeedChanged = true;

            this.ball = ball;

            Vector2 velocity = ball.getVelocity();
            velocity.scl(2f);
            ball.setVelocity(velocity);

            timer = new Timer();
            timer.schedule(new ResetSpeedTask(), Constants.SPEED_BRICK_TIME);
        }

    }

    private class ResetSpeedTask extends TimerTask {
        public void run(){

            isSpeedChanged = false;

            Vector2 velocity = ball.getVelocity();
            velocity.scl(0.5f);
            ball.setVelocity(velocity);

        }
    }


}

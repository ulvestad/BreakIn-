package xyz.tdt4240.breakin.game.sprites.bricks;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.SoundHandler;
import xyz.tdt4240.breakin.game.movement_patterns.CircularMovementPattern;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Requires multiple hits to break
 */
public class HardBrick extends Brick {

    public HardBrick(GameState gameState){
        super(gameState);

        brickHealth = Constants.HARD_BRICK_HEALTH;
        brickScore = Constants.BRICK_SCORE_HARD;
        brickTexture = new Texture(Constants.TEXTURE_BRICK_HARD);

    }

    @Override
    public void hit(Ball ball) {
        super.hit(ball);
        //TODO change texture
        SoundHandler.getInstance().playHardBrickHitSound();
    }


}

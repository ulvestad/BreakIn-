package xyz.tdt4240.breakin.game.sprites.bricks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.movement_patterns.CircularMovementPattern;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Standard brick, no special effects
 */
public class NormalBrick extends Brick {

    public NormalBrick(GameState gameState) {
        super(gameState);

        brickScore = Constants.BRICK_SCORE_NORMAL;
        brickTexture = new Texture(Constants.TEXTURE_BRICK_NORMAL);

    }


}

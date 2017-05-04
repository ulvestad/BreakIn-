package xyz.tdt4240.breakin.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xyz.tdt4240.breakin.activities.GameActivity;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.SoundHandler;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.sprites.bricks.Brick;
import xyz.tdt4240.breakin.game.sprites.bricks.BrickFactory;
import xyz.tdt4240.breakin.game.movement_patterns.MovementPattern;
import xyz.tdt4240.breakin.game.movement_patterns.MovementPatternFactory;
import xyz.tdt4240.breakin.models.Level;

/**
 * Abstract superclass for all GameStates
 */
public abstract class GameState extends State {

    protected Texture background;

    protected List<Brick> bricks;

    protected MovementPattern movementPattern;

    protected Level lvl;

    protected String gameMode;

    protected GameActivity gameActivity;

    protected int bricksLeft;

    public GameState(GameActivity gameActivity, GameStateManager gsm, String gameMode, long lvlId) {
        super(gsm);

        this.gameActivity = gameActivity;
        this.gameMode = gameMode;

        //Load sound files now, so it wont slow down the game later
        SoundHandler.init();

        lvl = Level.findById(Level.class, lvlId);

        bricks = BrickFactory.createBricks(lvl.getBricks(), this);

        bricksLeft = bricks.size();

        movementPattern = MovementPatternFactory.createPattern(lvl.movementPattern, bricks);

        movementPattern.initializePositions();

        background = new Texture(Constants.TEXTURE_BACKGROUND);

    }

    //Could also create a helper class for this function
    public boolean doesBoundsOverlap(Polygon p1, Polygon p2){
        return Intersector.overlapConvexPolygons(p1, p2);
    }

    public int getRemainingBricks(){
        return bricksLeft;
    }

    public boolean hasBricksLeft(){
        return bricksLeft > 0;
    }

    public abstract void removeBrick(Brick brick, Ball ball);

    public abstract void changePaddleWidth(int playerId, int width, long milliseconds);

    public abstract void applyScorePenalty(int playerId, float penalty);

    public abstract void endGame();

}

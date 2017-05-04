package xyz.tdt4240.breakin.game.states;

import android.content.Context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.orhanobut.logger.Logger;

import java.util.Random;

import xyz.tdt4240.breakin.activities.GameActivity;
import xyz.tdt4240.breakin.application.BreakInApplication;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.ScreenHandler;
import xyz.tdt4240.breakin.game.SoundHandler;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.sprites.Paddle;
import xyz.tdt4240.breakin.game.sprites.ScoreField;
import xyz.tdt4240.breakin.game.sprites.bricks.Brick;

/**
 * Handles singleplayer games
 */
public class SingleplayerGameState extends GameState {

    private Paddle paddle;

    private Ball ball;

    private ScoreField scoreField;

    private int playerScore;

    private boolean isBallMoving;

    public SingleplayerGameState(GameActivity gameActivity, GameStateManager gsm, long lvlId) {
        super(gameActivity, gsm, Constants.GAME_MODE_NORMAL, lvlId);

        paddle = new Paddle(this, Constants.PLAYER_1);

        ball = new Ball(this, Constants.PLAYER_1);

        scoreField = new ScoreField(Constants.GAME_MODE_NORMAL, Constants.PLAYER_1);

        SoundHandler.getInstance().playGameMusic();

    }

    @Override
    public void removeBrick(Brick brick, Ball ball) {
        brick.dispose();

        playerScore += brick.getScore();
        scoreField.setScore(playerScore);
        brick.remove();
        bricksLeft--;

        if(!hasBricksLeft())
            endGame();

    }

    @Override
    public void changePaddleWidth(int playerId, int width, long milliseconds) {
        paddle.changeWidthFor(width, milliseconds);
    }

    @Override
    public void applyScorePenalty(int playerId, float penalty) {

        //No need to check the playerId, since there is only one player

        playerScore -= penalty * playerScore;
        scoreField.setScore(playerScore);

    }

    @Override
    protected void handleInput() {

        //Teleports paddle to position touched
        if(Gdx.input.isTouched()) {

            if(!isBallMoving){
                isBallMoving = true;
                ball.setVelocity(ball.createRandomizedStartVelocity());
            }

            paddle.setXPos(Gdx.input.getX() - (Constants.PADDLE_WIDTH / 2));
        }

    }

    @Override
    public void update(float dt) {

        handleInput();

        ball.update(dt);
        paddle.update(dt);
        movementPattern.update(dt);

        for(Brick b : bricks){

            if(!b.isRemoved() && doesBoundsOverlap(b.getBrickBounds(), ball.getBallBounds())){
                ball.onBrickHit(b);
                break;
            }

        }

        if(doesBoundsOverlap(paddle.getPaddleBounds(), ball.getBallBounds()))
            ball.onPaddleHit(paddle);

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();

        sb.draw(background, 0, 0, ScreenHandler.getWidth(), ScreenHandler.getHeight());

        scoreField.render(sb);

        paddle.render(sb);

        ball.render(sb);

        for(Brick b : bricks)
            b.render(sb);

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        ball.dispose();
        paddle.dispose();
        scoreField.dispose();
    }

    @Override
    public void endGame(){

        SoundHandler.getInstance().stopGameMusic();

        boolean didPlayerWin = hasBricksLeft();

        if(didPlayerWin)
            SoundHandler.getInstance().playVictorySound();

        gsm.set(new GameOverState(gameActivity, gsm, lvl.getId(), playerScore, Constants.GAME_MODE_NORMAL));

    }
}

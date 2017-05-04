package xyz.tdt4240.breakin.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
 * Gamestate for offline multiplayer matches
 */
public class MultiplayerGameState extends GameState {

    private ScoreField scoreField1;
    private ScoreField scoreField2;

    private Paddle paddle1;
    private Paddle paddle2;

    private Ball ball1;
    private Ball ball2;

    private int player1Score;
    private int player2Score;

    private boolean isBallMoving;

    public MultiplayerGameState(GameActivity gameActivity, GameStateManager gsm, long lvlId, String gameMode) {
        super(gameActivity, gsm, gameMode, lvlId);

        scoreField1 = new ScoreField(gameMode, Constants.PLAYER_1);
        scoreField2 = new ScoreField(gameMode, Constants.PLAYER_2);

        paddle1 = new Paddle(this, Constants.PLAYER_1);
        paddle2 = new Paddle(this, Constants.PLAYER_2);

        ball1 = new Ball(this, Constants.PLAYER_1);
        ball2 = new Ball(this, Constants.PLAYER_2);

        SoundHandler.getInstance().playGameMusic();
    }

    @Override
    public void applyScorePenalty(int playerId, float penalty) {

        if(playerId == Constants.PLAYER_1){
            player1Score -= penalty * player1Score;
            scoreField1.setScore(player1Score);
        }else{
            player2Score -= penalty * player2Score;
            scoreField2.setScore(player2Score);
        }

    }

    @Override
    protected void handleInput() {

        //Teleports paddle to position touched
        if(Gdx.input.isTouched()){

            if(!isBallMoving){
                isBallMoving = true;
                ball1.setVelocity(ball1.createRandomizedStartVelocity());
                ball2.setVelocity(ball2.createRandomizedStartVelocity());
            }

            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            //Find paddle closest to position touched (only y-axis)
            float paddle1Distance = Math.abs(paddle1.getPosition().y - y);
            float paddle2Distance = Math.abs(paddle2.getPosition().y - y);

            if(paddle1Distance < paddle2Distance)
                paddle2.setXPos(x - (Constants.PADDLE_WIDTH / 2));
            else
                paddle1.setXPos(x - (Constants.PADDLE_WIDTH / 2));


        }

    }

    @Override
    public void update(float dt) {

        handleInput();

        ball1.update(dt);
        ball2.update(dt);
        movementPattern.update(dt);

        for(Brick b : bricks){

            if(!b.isRemoved() && doesBoundsOverlap(b.getBrickBounds(), ball1.getBallBounds())) {
                ball1.onBrickHit(b);
                break; //Should not be able to hit more than one brick in a frame
            }

        }

        for(Brick b : bricks){

            if(!b.isRemoved() && doesBoundsOverlap(b.getBrickBounds(), ball2.getBallBounds())) {
                ball2.onBrickHit(b);
                break; //Should not be able to hit more than one brick in a frame
            }

        }

        if(doesBoundsOverlap(paddle1.getPaddleBounds(), ball1.getBallBounds()))
            ball1.onPaddleHit(paddle1);
        if(doesBoundsOverlap(paddle2.getPaddleBounds(), ball1.getBallBounds()))
            ball1.onPaddleHit(paddle2);
        if(doesBoundsOverlap(paddle1.getPaddleBounds(), ball2.getBallBounds()))
            ball2.onPaddleHit(paddle1);
        if(doesBoundsOverlap(paddle2.getPaddleBounds(), ball2.getBallBounds()))
            ball2.onPaddleHit(paddle2);

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();

        sb.draw(background, 0, 0, ScreenHandler.getWidth(), ScreenHandler.getHeight());

        scoreField1.render(sb);
        scoreField2.render(sb);

        paddle1.render(sb);
        paddle2.render(sb);

        ball1.render(sb);
        ball2.render(sb);

        for(Brick b : bricks)
            b.render(sb);

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        ball1.dispose();
        ball2.dispose();
        paddle1.dispose();
        paddle2.dispose();
        scoreField1.dispose();
        scoreField2.dispose();
    }

    @Override
    public void removeBrick(Brick brick, Ball ball) {

        if(ball.getPlayerId() == Constants.PLAYER_1){
            player1Score += brick.getScore();
            scoreField1.setScore(player1Score);
        }else{
            player2Score += brick.getScore();
            scoreField2.setScore(player2Score);
        }

        brick.remove();
        bricksLeft--;

        if(!hasBricksLeft())
            endGame();

    }

    @Override
    public void changePaddleWidth(int playerId, int width, long milliseconds) {

        if(playerId == Constants.PLAYER_1)
            paddle1.changeWidthFor(width, milliseconds);
        else
            paddle2.changeWidthFor(width, milliseconds);

    }

    @Override
    public void endGame(){

        boolean gameOver = hasBricksLeft();

        if(gameOver)
            SoundHandler.getInstance().playVictorySound();

        if(gameMode.equals(Constants.GAME_MODE_COOPERATIVE))
            gsm.set(new GameOverState(gameActivity, gsm, lvl.getId(), player1Score + player2Score, Constants.GAME_MODE_COOPERATIVE));
        else
            gsm.set(new GameOverState(gameActivity, gsm, player1Score, player2Score));

    }

}

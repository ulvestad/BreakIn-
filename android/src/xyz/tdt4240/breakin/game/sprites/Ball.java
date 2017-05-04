package xyz.tdt4240.breakin.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.orhanobut.logger.Logger;

import xyz.tdt4240.breakin.application.BreakInApplication;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.ScreenHandler;
import xyz.tdt4240.breakin.game.physics.Bounce;
import xyz.tdt4240.breakin.game.sprites.bricks.Brick;
import xyz.tdt4240.breakin.game.sprites.bricks.CoreBrick;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Describes the complete state of a ball
 */
public class Ball implements Renderable, Updateable {

    private Vector2 position;

    private Vector2 velocity;

    private Texture ballTexture;

    private GameState gameState;

    private Polygon ballBounds;

    private int playerId;

    public Ball(GameState gameState, int playerId) {

        this.gameState = gameState;
        this.playerId = playerId;

        position = new Vector2();
        velocity = new Vector2();

        ballBounds = new Polygon(Constants.BALL_VERTICES);

        if(playerId == Constants.PLAYER_1)
            ballTexture = new Texture(Constants.TEXTURE_BALL_BLUE);
        else
            ballTexture = new Texture(Constants.TEXTURE_BALL_RED);

        placeAtStartPosition();
    }


    public void placeAtStartPosition(){

        float startX;
        float startY;

        startX = (ScreenHandler.getWidth() / 2) - (ballTexture.getWidth() / 2);

        if(playerId == Constants.PLAYER_1)
            startY = Constants.BALL_VERTICAL_OFFSET + Constants.PADDLE_VERTICAL_OFFSET;
        else
            startY = ScreenHandler.getHeight() - Constants.BALL_VERTICAL_OFFSET - (Constants.PADDLE_VERTICAL_OFFSET * 2);

        position.set(startX,startY);
        ballBounds.setPosition(startX,startY);
    }

    public Polygon getBallBounds(){
        return ballBounds;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Returns ball position
     * @return
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets ball position
     * @param position
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Handles event when ball hits brick
     * @param brick
     */
    public void onBrickHit(Brick brick){

        Logger.d("Ball collided with " + brick.getClass().getSimpleName());

        brick.hit(this);

        if(brick.getHealth() == 0) {
            brick.onBreak(this);
            gameState.removeBrick(brick, this);
        }
        Vector2 prevVelocity = new Vector2(velocity);
        prevVelocity.scl(1.5f);
        Logger.d("PrebounceVelocity: " +velocity);
        velocity = Bounce.reflect(this, brick.getBrickBounds());
        position.sub(prevVelocity);
        ballBounds.setPosition(position.x,position.y);




        Logger.d("Velocity "+velocity);
    }

    /**
     * Handles event when ball hits paddle
     * @param paddle
     */
    public void onPaddleHit(Paddle paddle){

        Logger.d("Ball collided with paddle");
        Logger.d("PrebounceVelocity: " +velocity);
        Vector2 prevVelocity = new Vector2(velocity);

        velocity = Bounce.reflect(this,paddle.getPaddleBounds());

        prevVelocity.sub(position);
        ballBounds.setPosition(position.x,position.y);
        if(paddle.getPlayerId()==1){
            position.set(position.x,paddle.getPosition().y + Constants.BALL_HEIGHT);
        } else {
            position.set(position.x,paddle.getPosition().y - Constants.PADDLE_HEIGHT);
        }
        Logger.d("Velocity "+velocity);
    }

    /**
     * Returns player id 1 = p1, 2 = p2
     * @return
     */
    public int getPlayerId(){
        return playerId;
    }

    /**
     * Sets ball velocity
     * @param velocity
     */
    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }

    /**
     * Draws Ball on screen
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {

        sb.draw(ballTexture, position.x, position.y, Constants.BALL_WIDTH, Constants.BALL_HEIGHT);

    }

    /**
     * Removes ball from screen
     */
    @Override
    public void dispose() {
        ballTexture.dispose();
    }

    /**
     * Updates the state of the ball
     * @param dt
     */
    @Override
    public void update(float dt) {
        //Logger.d("Current velocity" +velocity);
        position.add(velocity);
        ballBounds.setPosition(position.x,position.y);

        if(position.x + Constants.BALL_WIDTH >= ScreenHandler.getWidth()){
            //Ball hits right edge
            velocity.x *= -1;
            position.x = ScreenHandler.getWidth() - Constants.BALL_WIDTH;
        }else if(position.x <= 0){
            //Ball hits left edge
            velocity.x *= -1;
            position.x = 0;
        }else if(position.y + Constants.BALL_HEIGHT >= ScreenHandler.getHeight()){
            //Ball hits top edge

            if(playerId == Constants.PLAYER_2){
                velocity.set(0,0);
                placeAtStartPosition();
                setDelayedVelocity(createRandomizedStartVelocity(), Constants.DELAY_AFTER_BALL_MISS);
                gameState.applyScorePenalty(playerId, Constants.BALL_MISS_PENALTY);
            }else{
                velocity.y *= -1;
                position.y = ScreenHandler.getHeight() - Constants.BALL_HEIGHT;
            }

        }else if(position.y <= 0){
            //Ball hits bottom edge

            if(playerId == Constants.PLAYER_1) {
                velocity.set(0,0);
                placeAtStartPosition();
                setDelayedVelocity(createRandomizedStartVelocity(), Constants.DELAY_AFTER_BALL_MISS);
                gameState.applyScorePenalty(playerId, Constants.BALL_MISS_PENALTY);
            }else{
                velocity.y *= -1;
                position.y = 0;
            }

        }

    }

    /**
     * Randomizes start velocity
     * @return Velocity as Vector2
     */
    public  Vector2 createRandomizedStartVelocity(){

        double angle = (15*Math.PI/180 + Math.random() * (60*Math.PI/180))*((int)(Math.random()*3+1));
        double velX = Constants.BALL_VELOCITY * Math.cos(angle);
        double velY = Constants.BALL_VELOCITY * Math.sin(angle);


        if(playerId == Constants.PLAYER_1){

            if(velX < 0)
                velX *= -1;

            if(velY < 0)
                velY *= -1;

        }else{

            if(velX > 0)
                velX *= -1;

            if(velY > 0)
                velY *= -1;

        }

        return new Vector2((float) velX, (float) velY);
    }

    /**
     * Makes sure that the ball stays stationary for a time
     * @param _velocity
     * @param delay
     */
    private void setDelayedVelocity(final Vector2 _velocity, final long delay){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    return;
                }

                velocity = _velocity;
            }
        }).start();

    }


}

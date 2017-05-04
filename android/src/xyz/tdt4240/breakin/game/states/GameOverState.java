package xyz.tdt4240.breakin.game.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.orhanobut.logger.Logger;

import xyz.tdt4240.breakin.activities.GameActivity;
import xyz.tdt4240.breakin.application.BreakInApplication;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.DbHelper;
import xyz.tdt4240.breakin.application.ScreenHandler;
import xyz.tdt4240.breakin.game.sprites.bricks.Brick;
import xyz.tdt4240.breakin.models.Level;
import xyz.tdt4240.breakin.models.LevelRating;

/**
 * Describes the state when the game is over
 */
public class GameOverState extends State {

    private Texture background;

    private GameActivity gameActivity;

    private BitmapFont gameOverText;

    private BitmapFont scoreText;

    private String gameOverStr;
    private String scoreStr;

    private float gameOverTextWidth;
    private float scoreTextWidth;

    private long gameOverStarted;

    //For competitive mode
    public GameOverState(GameActivity gameActivity, GameStateManager gsm, int player1Score, int player2Score) {
        super(gsm);
        this.gameActivity = gameActivity;
        Logger.d("GAME OVER");

        background = new Texture(Constants.TEXTURE_BACKGROUND);

        gameOverText = new BitmapFont();
        gameOverText.getData().setScale(5, 5);

        scoreText = new BitmapFont();
        scoreText.getData().setScale(4, 4);

        if(player1Score > player2Score)
            gameOverStr = "Player 1 Won !";
        else if(player2Score > player1Score)
            gameOverStr = "Player 2 Won !";
        else
            gameOverStr = "Draw !";

        scoreStr = "Player 1: " + player1Score + "\nPlayer 2: " + player2Score;

        scoreTextWidth = new GlyphLayout(scoreText, scoreStr).width;
        gameOverTextWidth = new GlyphLayout(gameOverText, gameOverStr).width;

        gameOverStarted = System.currentTimeMillis();

    }

    public GameOverState(GameActivity gameActivity, GameStateManager gsm, long lvlId, int scoreAchieved, String gameMode) {
        super(gsm);
        this.gameActivity = gameActivity;
        Logger.d("GAME OVER");

        DbHelper.saveScore(lvlId, scoreAchieved, gameMode);

        background = new Texture(Constants.TEXTURE_BACKGROUND);

        gameOverText = new BitmapFont();
        gameOverText.getData().setScale(5, 5);

        scoreText = new BitmapFont();
        scoreText.getData().setScale(4, 4);

        gameOverStr = "You Won!";

        scoreStr = "Score: " + scoreAchieved;

        scoreTextWidth = new GlyphLayout(scoreText, scoreStr).width;
        gameOverTextWidth = new GlyphLayout(gameOverText, gameOverStr).width;

        gameOverStarted = System.currentTimeMillis();

    }

    @Override
    protected void handleInput() {

        if(Gdx.input.isTouched()){

            //Don't close the activity before at least Constants.MIN_GAME_OVER_TIME has passed
            if(System.currentTimeMillis() - gameOverStarted < Constants.MIN_GAME_OVER_TIME)
                return;

            gameActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameActivity.finish();
                }
            });

        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();

        sb.draw(background, 0, 0, ScreenHandler.getWidth(), ScreenHandler.getHeight());

        gameOverText.draw(sb, gameOverStr, (SCREEN_WIDTH / 2) - (gameOverTextWidth / 2), (SCREEN_HEIGHT / 2) + 100);

        scoreText.draw(sb, scoreStr, SCREEN_WIDTH / 2 - (scoreTextWidth / 2), (SCREEN_HEIGHT / 2));

        sb.end();

    }

    @Override
    public void dispose() {
        gameOverText.dispose();
        scoreText.dispose();
    }

}

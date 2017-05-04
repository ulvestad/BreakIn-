package xyz.tdt4240.breakin.game.sprites;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.ScreenHandler;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Keeps track of players score
 */
public class ScoreField implements Renderable {

    private BitmapFont font;

    private int score;

    private int x;

    private int y;

    public ScoreField(String gameMode, int playerId) {
        font = new BitmapFont();

        if(playerId == Constants.PLAYER_1)
            font.getData().setScale(Constants.SCORE_FIELD_FONT_SCALE, Constants.SCORE_FIELD_FONT_SCALE);
        else
            font.getData().setScale(-Constants.SCORE_FIELD_FONT_SCALE, -Constants.SCORE_FIELD_FONT_SCALE);


        GlyphLayout glyphLayout = new GlyphLayout(font, "");

        if(playerId == Constants.PLAYER_1){

            if(gameMode.equals(Constants.GAME_MODE_NORMAL)){
                x = Constants.SCORE_FIELD_HORIZONTAL_OFFSET;
                y = ScreenHandler.getHeight() - Constants.SCORE_FIELD_VERTICAL_OFFSET - (int)glyphLayout.height;
            }else{
                x = Constants.SCORE_FIELD_HORIZONTAL_OFFSET;
                y = Constants.SCORE_FIELD_VERTICAL_OFFSET + (int)glyphLayout.height;
            }

        }else{
            x = ScreenHandler.getWidth() - Constants.SCORE_FIELD_HORIZONTAL_OFFSET;
            y = ScreenHandler.getHeight() - Constants.SCORE_FIELD_VERTICAL_OFFSET + (int)glyphLayout.height;
        }

    }

    public void setScore(int score){
        this.score = score;
    }

    @Override
    public void render(SpriteBatch sb) {
        font.draw(sb, "Score: " + score, x, y );
    }

    @Override
    public void dispose() {
        font.dispose();
    }

}
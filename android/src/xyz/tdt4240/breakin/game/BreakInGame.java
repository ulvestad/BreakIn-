package xyz.tdt4240.breakin.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import xyz.tdt4240.breakin.activities.GameActivity;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.states.GameStateManager;
import xyz.tdt4240.breakin.game.states.MultiplayerGameState;
import xyz.tdt4240.breakin.game.states.SingleplayerGameState;

/**
 * Starts up a BreakIn game.
 * This class should only be used by GameActivity.
 */
public class BreakInGame extends ApplicationAdapter {

	private SpriteBatch batch;

	private GameStateManager gsm;

	private String gameMode;

	private long lvlId;

    private GameActivity gameActivity;


	public BreakInGame(GameActivity gameActivity, String gameMode, long lvlId){
        this.gameActivity = gameActivity;
		this.gameMode = gameMode;
		this.lvlId = lvlId;
	}

	public void cleanUp(){
		gsm.removeAll();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
        gsm = new GameStateManager();

        Gdx.gl.glClearColor(1, 0, 0, 1);

		if(gameMode.equals(Constants.GAME_MODE_NORMAL))
			gsm.add(new SingleplayerGameState(gameActivity, gsm, lvlId));
		else
			gsm.add(new MultiplayerGameState(gameActivity, gsm, lvlId, gameMode));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}

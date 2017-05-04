package xyz.tdt4240.breakin.activities;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.orhanobut.logger.Logger;

import xyz.tdt4240.breakin.application.Tags;
import xyz.tdt4240.breakin.game.BreakInGame;
import xyz.tdt4240.breakin.game.states.GameStateManager;

/**
 * Controller class for the game activity.
 * Starts up LibGDX.
 */
public class GameActivity extends AndroidApplication {

    private BreakInGame breakInGame;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String gameMode = getIntent().getStringExtra(Tags.GAME_MODE);
        long lvlId = getIntent().getLongExtra(Tags.LEVEL_ID, -1);

        breakInGame = new BreakInGame(this, gameMode, lvlId);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(breakInGame, config);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        breakInGame.cleanUp();
    }

}


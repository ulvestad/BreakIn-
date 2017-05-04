package xyz.tdt4240.breakin.fragments;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import xyz.tdt4240.breakin.R;
import xyz.tdt4240.breakin.activities.GameActivity;
import xyz.tdt4240.breakin.activities.MainActivity;
import xyz.tdt4240.breakin.adapters.LevelAdapter;
import xyz.tdt4240.breakin.adapters.LevelAdapterNoRating;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.DbHelper;
import xyz.tdt4240.breakin.application.Tags;
import xyz.tdt4240.breakin.models.Level;

/**
 * Fragment for starting a multiplayer game
 */
public class MultiplayerFragment extends Fragment {

    private GridView levelGrid;

    private MainActivity parentActivity;

    private View rootView;

    private RadioGroup gameModeGroup;

    private RadioButton competitiveBtn;

    private RadioButton cooperativeBtn;

    private List<Level> levels;

    private String currentGameMode = Constants.DEFAULT_MULTIPLAYER_GAME_MODE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_multiplayer, container, false);

        gameModeGroup = (RadioGroup) rootView.findViewById(R.id.gameModeGroup);

        competitiveBtn = (RadioButton) rootView.findViewById(R.id.competitiveBtn);

        cooperativeBtn = (RadioButton) rootView.findViewById(R.id.cooperativeBtn);

        levelGrid = (GridView) rootView.findViewById(R.id.levelGrid);

        updateLevelGrid(currentGameMode);

        if(currentGameMode.equals(Constants.GAME_MODE_COMPETITIVE))
            competitiveBtn.setChecked(true);
        else
            cooperativeBtn.setChecked(true);

        levelGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Level selectedLvl = levels.get(position);

                Intent intent = new Intent(parentActivity, GameActivity.class);

                intent.putExtra(Tags.GAME_MODE, currentGameMode);
                intent.putExtra(Tags.LEVEL_ID, selectedLvl.getId());

                startActivity(intent);


            }
        });

        gameModeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                currentGameMode = (checkedId == competitiveBtn.getId())
                        ? Constants.GAME_MODE_COMPETITIVE : Constants.GAME_MODE_COOPERATIVE;

                updateLevelGrid(currentGameMode);

            }
        });

        return rootView;
    }

    public void updateLevelGrid(String gameMode){

        levels = DbHelper.getMultiplayerLevels();

        BaseAdapter levelAdapter;

        if(gameMode.equals(Constants.GAME_MODE_COMPETITIVE))
            levelAdapter = new LevelAdapterNoRating(getContext(), levels);
        else
            levelAdapter = new LevelAdapter(getContext(), levels, gameMode);

        levelGrid.setAdapter(levelAdapter);

    }

    public void setParentActivity(MainActivity parentActivity){
        this.parentActivity = parentActivity;
    }

}

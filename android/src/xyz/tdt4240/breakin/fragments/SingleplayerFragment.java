package xyz.tdt4240.breakin.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.orhanobut.logger.Logger;

import java.util.List;


import xyz.tdt4240.breakin.R;
import xyz.tdt4240.breakin.activities.GameActivity;
import xyz.tdt4240.breakin.activities.MainActivity;
import xyz.tdt4240.breakin.adapters.LevelAdapter;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.DbHelper;
import xyz.tdt4240.breakin.application.Tags;
import xyz.tdt4240.breakin.models.Level;

/**
 * Fragment for starting a singleplayer game
 */
public class SingleplayerFragment extends Fragment {

    private GridView levelGrid;

    private LevelAdapter levelAdapter;

    private MainActivity parentActivity;

    private View rootView;

    private List<Level> levels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_singleplayer, container, false);

        levelGrid = (GridView) rootView.findViewById(R.id.levelGrid);

        levels = DbHelper.getSingleplayerLevels();

        levelAdapter = new LevelAdapter(getContext(), levels, Constants.GAME_MODE_NORMAL);

        levelGrid.setAdapter(levelAdapter);

        levelGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(parentActivity, GameActivity.class);

                intent.putExtra(Tags.GAME_MODE, Constants.GAME_MODE_NORMAL);
                intent.putExtra(Tags.LEVEL_ID, levels.get(i).getId());

                Logger.d("Sending id: " + levels.get(i).getId());

                startActivity(intent);

            }
        });

        return rootView;
    }

    public void setParentActivity(MainActivity parentActivity){
        this.parentActivity = parentActivity;
    }

}

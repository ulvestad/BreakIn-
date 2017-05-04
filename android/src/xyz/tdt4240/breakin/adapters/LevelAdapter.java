package xyz.tdt4240.breakin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.tdt4240.breakin.R;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.DbHelper;
import xyz.tdt4240.breakin.models.Level;
import xyz.tdt4240.breakin.models.LevelRating;

/**
 * Adapter for level grid items with rating bar
 */
public class LevelAdapter extends BaseAdapter {

    private Context context;

    private List<Level> levels;

    private String gameMode;

    public LevelAdapter(Context context, List<Level> levels, String gameMode) {
        this.context = context;
        this.levels = levels;
        this.gameMode = gameMode;
    }

    public int getCount() {
        return levels.size();
    }

    public Object getItem(int position) {
        return levels.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.entry_level, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.levelTv = (TextView) convertView.findViewById(R.id.levelTv);
            viewHolder.ratingAchievedBar = (RatingBar) convertView.findViewById(R.id.ratingAchievedBar);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Level lvl = levels.get(position);

        viewHolder.levelTv.setText(String.valueOf(lvl.lvlNr));

        if(gameMode.equals(Constants.GAME_MODE_COMPETITIVE)){
            viewHolder.ratingAchievedBar.setVisibility(View.INVISIBLE);
        }else{

            LevelRating levelRating = DbHelper.getLevelRating(lvl.getId(), gameMode);

            if(levelRating == null)
                viewHolder.ratingAchievedBar.setRating(0);
            else
                viewHolder.ratingAchievedBar.setRating(levelRating.ratingAchieved);
        }

        //Set the color of the rating stars
        LayerDrawable stars = (LayerDrawable) viewHolder.ratingAchievedBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(context, R.color.ratingBarColor), PorterDuff.Mode.SRC_ATOP);

        return convertView;
    }

    private static class ViewHolder {
        TextView levelTv;
        RatingBar ratingAchievedBar;
    }
}
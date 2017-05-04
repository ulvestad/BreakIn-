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
 * Adapter for level grid items without rating bar
 */
public class LevelAdapterNoRating extends BaseAdapter {

    private Context context;

    private List<Level> levels;

    public LevelAdapterNoRating(Context context, List<Level> levels) {
        this.context = context;
        this.levels = levels;
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
            convertView = inflater.inflate(R.layout.entry_level_no_rating, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.levelTv = (TextView) convertView.findViewById(R.id.levelTv);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Level lvl = levels.get(position);

        viewHolder.levelTv.setText(String.valueOf(lvl.lvlNr));

        return convertView;
    }

    private static class ViewHolder {
        TextView levelTv;
    }
}
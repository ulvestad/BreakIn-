package xyz.tdt4240.breakin.models;

import com.orm.SugarRecord;

/**
 * Data model level rating.
 * There can be several ratings depending on the game mode for each level.
 */
public class LevelRating extends SugarRecord {

    public long lvlId;

    public int ratingAchieved;

    public String gameMode;

    public LevelRating(){}

}

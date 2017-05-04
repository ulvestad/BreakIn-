package xyz.tdt4240.breakin.models;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Data model for game levels
 */
public class Level extends SugarRecord {

    @Ignore
    public int lvlNr;

    public int lvlType;

    @Ignore
    private List<String> bricks;

    @Ignore
    private int[] scoreNeededForRating;

    //Movement pattern name
    public String movementPattern;

    //Workaround for dealing with relationships in ORM and keeping a normalized database
    private String bricksJsonStr;
    private String scoreNeededJsonStr;

    @Ignore
    private static Gson gson = new Gson();


    public void setBricks(List<String> bricks){
        this.bricks = bricks;
    }

    public void setScoreNeededForRating(int[] scoreNeededForRating){
        this.scoreNeededForRating = scoreNeededForRating;
    }

    public List<String> getBricks(){

        if(bricks == null)
            bricks = gson.fromJson(bricksJsonStr, new TypeToken<List<String>>(){}.getType());

        return bricks;

    }

    public int[] getScoreNeededForRating(){

        if(scoreNeededForRating == null)
            scoreNeededForRating = gson.fromJson(scoreNeededJsonStr, int[].class);

        return scoreNeededForRating;
    }


    @Override
    public long save(){
        bricksJsonStr = gson.toJson(getBricks());
        scoreNeededJsonStr = gson.toJson(getScoreNeededForRating());
        return super.save();
    }


}

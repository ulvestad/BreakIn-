package xyz.tdt4240.breakin.application;

import com.orhanobut.logger.Logger;

import java.util.List;

import xyz.tdt4240.breakin.models.Level;
import xyz.tdt4240.breakin.models.LevelRating;

/**
 * Wrapper class for managing the local database
 */
public class DbHelper {

    public static List<Level> getSingleplayerLevels(){

        List<Level> levels = Level.find(Level.class, "lvl_type=? or lvl_type=?",
                String.valueOf(Constants.LEVEL_TYPE_SINGLEPLAYER), String.valueOf(Constants.LEVEL_TYPE_BOTH));

        for(int i = 0; i < levels.size(); i++)
            levels.get(i).lvlNr = i + 1;

        return levels;
    }

    public static List<Level> getMultiplayerLevels(){

        List<Level> levels = Level.find(Level.class, "lvl_type=? or lvl_type=?",
                String.valueOf(Constants.LEVEL_TYPE_MULTIPLAYER), String.valueOf(Constants.LEVEL_TYPE_BOTH));

        for(int i = 0; i < levels.size(); i++)
            levels.get(i).lvlNr = i + 1;

        return levels;
    }

    public static LevelRating getLevelRating(long lvlId, String gameMode){

        List<LevelRating> levelRatings = LevelRating.find(LevelRating.class, "lvl_id=? and game_mode=?", String.valueOf(lvlId), gameMode);

        if(levelRatings.isEmpty())
            return null;
        else
            return levelRatings.get(0);

    }

    public static void saveScore(long lvlId, int scoreAchieved, String gameMode){

        Logger.d("Saving score => lvlId: " + lvlId + " score: " + scoreAchieved + " gameMode: " + gameMode);

        if(gameMode.equals(Constants.GAME_MODE_COMPETITIVE))
            return;

        Level lvl = Level.findById(Level.class, lvlId);

        if(lvl != null){

            int[] scoreNeeded = lvl.getScoreNeededForRating();

            for(int i = scoreNeeded.length - 1; i >= 0; i--){

                if(scoreAchieved >= scoreNeeded[i]){

                    int ratingAchieved = i + 1;
                    LevelRating levelRating = DbHelper.getLevelRating(lvlId, gameMode);

                    if(levelRating != null && levelRating.ratingAchieved < ratingAchieved){
                        levelRating.ratingAchieved = ratingAchieved;
                        levelRating.save();
                    }else{
                        levelRating = new LevelRating();
                        levelRating.lvlId = lvlId;
                        levelRating.gameMode = gameMode;
                        levelRating.ratingAchieved = ratingAchieved;
                        levelRating.save();
                    }

                    break;
                }
            }

        }

    }


}

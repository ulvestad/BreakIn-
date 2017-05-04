package xyz.tdt4240.breakin.application;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import xyz.tdt4240.breakin.models.Level;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Helper class for seeding the database
 */
public class SeedHelper {

    public static void seedLevels(){

        Logger.d("Seeding levels");
        int[] scoreNeededForRating = new int[Constants.RATING_LEVELS];
        scoreNeededForRating[0] = 1000;
        scoreNeededForRating[1] = 2000;
        scoreNeededForRating[2] = 3000;

        ArrayList<String> bricks = new ArrayList<>();
        bricks.add(Constants.BRICK_TYPE_CORE);
        int numBricks =0;
        for(Integer x: Constants.BRICKS_PER_LEVEL){
            numBricks += x;
        }
        ArrayList<String> bricks2;
        ArrayList<String> bricks3;

        //48 bricks in bricks, bricks2, bricks3
        for(int i = 0; i < numBricks; i++) bricks.add(Constants.BRICK_TYPE_NORMAL);
        bricks2 = new ArrayList<String>(bricks);
        bricks3 = new ArrayList<String>(bricks);

        randomPlacer(bricks);
        randomPlacer(bricks2);
        randomPlacer(bricks3);

        Level lvl1 = new Level();
        lvl1.lvlType = Constants.LEVEL_TYPE_BOTH;
        lvl1.movementPattern = Constants.MOVEMENT_PATTERN_CIRCULAR;
        lvl1.setScoreNeededForRating(scoreNeededForRating);
        lvl1.setBricks(bricks);
        lvl1.save();

        Level lvl2 = new Level();
        lvl2.lvlType = Constants.LEVEL_TYPE_BOTH;
        lvl2.movementPattern = Constants.MOVEMENT_PATTERN_CIRCULAR;
        lvl2.setScoreNeededForRating(scoreNeededForRating);
        lvl2.setBricks(bricks2);
        lvl2.save();

        Level lvl3 = new Level();
        lvl3.lvlType = Constants.LEVEL_TYPE_SINGLEPLAYER;
        lvl3.movementPattern = Constants.MOVEMENT_PATTERN_CIRCULAR;
        lvl3.setScoreNeededForRating(scoreNeededForRating);
        lvl3.setBricks(bricks3);
        lvl3.save();

    }

    //Creates i random numbers between 1-48 and adds
    //special bricks to these positions in the brickArray
    public static void randomPlacer(ArrayList<String> brickSet){
        ArrayList<String> brickType = new ArrayList<String>();
        brickType.add(0, Constants.BRICK_TYPE_HARD);
        brickType.add(1, Constants.BRICK_TYPE_SLOW);
        brickType.add(2, Constants.BRICK_TYPE_SPEED);
        brickType.add(3, Constants.BRICK_TYPE_BIGGER_PADDLE);

        //List for randomizing what type of special brick to insert
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        for (int i=0; i<4; i++) {
            list2.add(i);
        }
        Collections.shuffle(list2);

        //Randomizer for inserting special bricks
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<48; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        int x = 0;
        for (int i=0; i<12; i++) {
            System.out.println(list.get(i));
            brickSet.set(list.get(i), brickType.get(list2.get(x)));
            x += 1;
            if (x >= 4){
                x = 0;
            }
        }
        //------------------------------------------------------------
    }

}
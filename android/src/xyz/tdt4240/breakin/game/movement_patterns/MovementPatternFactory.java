package xyz.tdt4240.breakin.game.movement_patterns;


import java.util.ArrayList;
import java.util.List;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.sprites.bricks.Brick;

public final class MovementPatternFactory {

    private MovementPatternFactory(){}

    /**
     *
     * @param patternName name of the movementpattern eg. CircularMovmentPattern
     * @param bricks bricks list containing brick objects
     * @return new MovmentPatter object or Exception
     */
    public static MovementPattern createPattern(String patternName, List<Brick> bricks){

        switch (patternName){
            case Constants.MOVEMENT_PATTERN_CIRCULAR:
                return new CircularMovementPattern(bricks);
            default:
                throw new IllegalArgumentException("Undefined movement pattern " + patternName);
        }

    }


}

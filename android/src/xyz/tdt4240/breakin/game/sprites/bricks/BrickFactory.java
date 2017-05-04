package xyz.tdt4240.breakin.game.sprites.bricks;


import java.util.ArrayList;
import java.util.List;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.states.GameState;

/**
 * Creates new bricks on demand
 */
public final class BrickFactory {

    private BrickFactory(){}

    public static Brick createBrick(String brickName, GameState gameState){

        switch (brickName){
            case Constants.BRICK_TYPE_HARD:
                return new HardBrick(gameState);
            case Constants.BRICK_TYPE_SLOW:
                return new SlowBrick(gameState);
            case Constants.BRICK_TYPE_SPEED:
                return new SpeedBrick(gameState);
            case Constants.BRICK_TYPE_NORMAL:
                return new NormalBrick(gameState);
            case Constants.BRICK_TYPE_BIGGER_PADDLE:
                return new BiggerPaddleBrick(gameState);
            case Constants.BRICK_TYPE_CORE:
                return new CoreBrick(gameState);
            default:
                throw new IllegalArgumentException("Undefined brick: " + brickName);
        }

    }

    public static List<Brick> createBricks(List<String> brickNames, GameState gameState){

        List<Brick> bricks = new ArrayList<>(brickNames.size());

        for(String brickName : brickNames)
            bricks.add(createBrick(brickName, gameState));

        return bricks;

    }

}

package xyz.tdt4240.breakin.game.movement_patterns;


import java.util.ArrayList;
import java.util.List;

import xyz.tdt4240.breakin.game.sprites.Updateable;
import xyz.tdt4240.breakin.game.sprites.bricks.Brick;

public abstract class MovementPattern implements Updateable {

    protected List<Brick> bricks;

    protected boolean isMoving;

    /**
     *
     * @param bricks bricks list containing brick objects
     */
    public MovementPattern(List<Brick> bricks){
        this.bricks = bricks;
        isMoving = true;
    }

    //Set the starting position for the bricks
    public abstract void initializePositions();

    //Updates the movement
    //Implementations of this function should not update if isMoving = false
    public abstract void update(float dt);

    //Start moving the bricks
    public void start(){
        isMoving = true;
    }

    //Stop moving the bricks
    public void stop(){
        isMoving = false;
    }

}

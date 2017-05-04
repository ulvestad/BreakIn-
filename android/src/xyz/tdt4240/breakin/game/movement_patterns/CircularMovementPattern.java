package xyz.tdt4240.breakin.game.movement_patterns;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import xyz.tdt4240.breakin.application.BreakInApplication;
import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.application.ScreenHandler;
import xyz.tdt4240.breakin.application.SeedHelper;
import xyz.tdt4240.breakin.game.sprites.bricks.Brick;
import xyz.tdt4240.breakin.game.states.SingleplayerGameState;
import xyz.tdt4240.breakin.game.states.State;

public class CircularMovementPattern extends MovementPattern {

    /**
     *
     * @param bricks list containing brick objects
     */

    public CircularMovementPattern(List<Brick> bricks) {
        super(bricks);
    }

    //declaration of variables
    private int r = (int)(State.SCREEN_WIDTH-(State.SCREEN_WIDTH*0.57));
    private int a = State.SCREEN_HEIGHT / 2 - (Constants.PADDLE_WIDTH + 50);
    private int b = State.SCREEN_HEIGHT  / 2- (Constants.PADDLE_HEIGHT - 60);

    public static float angle;

    private int []circlelayers = Constants.BRICKS_PER_LEVEL;
    private int num_layers = circlelayers.length;
    private double anlge_increment = 0;
    private List<List<Integer>> currentBricks = new ArrayList<List<Integer>>();
    private boolean isGamePaused = false;


    /**
     * method to initilize postions of the bricks in a circular pattern in different layers
     */
    @Override
    public void initializePositions() {
        //init pos of core brick in the middle
        bricks.get(0).setPosition(State.SCREEN_WIDTH/2-(Constants.CORE_BRICK_WIDTH/2),State.SCREEN_HEIGHT/2-(Constants.CORE_BRICK_HEIGHT/2));
        //init pos for all other bricks in a circular movementpattern
        int counter = 0;
        for (int i = 0; i < num_layers; i++) {
            currentBricks.add(new ArrayList<Integer>());
            double slice = 2 * Math.PI / circlelayers[i];
            for(int j = 0; j<circlelayers[i]; j++){
                currentBricks.get(i).add(counter);
                int x;
                if(i == 0){
                    x = 0;
                }else{
                    x = (r/(num_layers+1))*i;
                }
                double angle = slice * j;
                int newX = (int) (a + (r-x) * Math.cos(angle));
                int newY = (int) (b + (r-x) * Math.sin(angle));
                bricks.get(counter+1).setPosition(newX, newY);
                counter +=1;
            }
        }
    }

    /**
     *
     * @param dt delta time
     * method that updated the postions of the bricks, giving the animation of the bricks rotating around the center
     */
    @Override
    public void update(float dt) {
        if(isGamePaused){
            //game is paused, NO update of positions
            return;
        }
        int counter = 0;
        double [] default_layer_speed = {0.0001, 0.002, 0.000001};
        for (int i = 0; i < num_layers; i++) {
            double slice = 2 * Math.PI / circlelayers[i];
            for(int j = 0; j<currentBricks.get(i).size(); j++){
                int x = determineLayer(i);
                if(bricks.get(counter+1).isRemoved()){
                    counter +=1;
                    anlge_increment += default_layer_speed[i];
                    continue;
                }
                int direction = 1;
                if(i == 1){
                    direction = -1;
                }
                double angle = slice * (j+anlge_increment*direction);

                int newX = (int) (a + (r-x) * Math.cos(angle));
                int newY = (int) (b + (r-x) * Math.sin(angle));
                bricks.get(counter+1).setPosition(newX, newY); //offest by 1 to exclude core brick

                this.angle = (float) (360-(Math.toDegrees(Math.atan2((newX - (State.SCREEN_WIDTH/2)), (newY - (State.SCREEN_HEIGHT/2))) )));

                //Sets a custom rotation relative to the center for each brick
                bricks.get(counter+1).setRotation(this.angle);

                counter +=1;
                anlge_increment += default_layer_speed[i];
            }
        }
    }
    //Weird angle thing
    //this.angle = (float) ((Math.toDegrees(Math.atan2((newX - State.SCREEN_WIDTH/2), (newY - State.SCREEN_HEIGHT/2)) )));

    /**
     * start rotation
     */
    @Override
    public void start() {
        this.isGamePaused = false;
    }

    /**
     * stop rotation
     */
    @Override
    public void stop() {
        this.isGamePaused = true;
    }

    /**
     *
     * @param i the number brick in the bricks list
     * @return the current layer of a brick
     */
    public int determineLayer(int i){
        int x;
        if(i == 0){
            x = 0;
        }else{
            x = (r/(num_layers+2))*i;
        }
        return x;
    }


}
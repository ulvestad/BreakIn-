package xyz.tdt4240.breakin.game.physics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.orhanobut.logger.Logger;


import java.util.ArrayList;

import xyz.tdt4240.breakin.application.Constants;
import xyz.tdt4240.breakin.game.sprites.Ball;
import xyz.tdt4240.breakin.game.sprites.bricks.Brick;
import xyz.tdt4240.breakin.game.sprites.bricks.CoreBrick;

/**
 * Contains methods used in ball collisions
 */
public final class Bounce {


    /**
     * Returns the new ball velocity after ball bounces on bounceObject
     * @param ball
     * @param bounceObject
     * @return
     */
    public static Vector2 reflect(Ball ball, Polygon bounceObject){
        //Gets ball velocity and position for later use
        Vector2 ballVelocity = ball.getVelocity();

        /*
        Chooses reflective surface based on surface hit
         */
        Vector2 bounceSurface = determineSurface(ball, bounceObject);


        /*
        ballVelocity = incoming trajectory
        ballVelocity(new) = outgoing trajectory
        normal = Normal of the bounce wall
        DOT = dotproduct
        ballVelocity(new)= ballVelocity-2*normal*(normal DOT ballVelocity)
        */

        Vector2 normal = bounceSurface.rotate90(1).nor();
        Vector2 subproduct = normal.scl(normal.dot(ballVelocity)).scl(2);
        Logger.d("Subproduct"+subproduct.toString());
        Logger.d("presub BallVel " +ballVelocity);
        ballVelocity.sub(subproduct);
        Logger.d("ball velocity "+ballVelocity);
        return ballVelocity;
    }

    /**
     * Determines which surface the ball bounces off of
     * @param ball
     * @param bounceObject
     * @return
     */
    public static Vector2 determineSurface(Ball ball, Polygon bounceObject){
        Vector2 surface;
        Vector2 ballVelocity = new Vector2(ball.getVelocity());
        //check which way the ball is going
        String direction = "";
        Logger.d("X-velocity: " +ballVelocity.x + " -- x > 0 = " + (ballVelocity.x>0));
        Logger.d("Y-velocity: " +ballVelocity.y + " -- y > 0 = " + (ballVelocity.y>0));

        direction = ballDirection(ballVelocity);
        /*
        Setting bounce axes
        */
        Vector2 shortside = new Vector2(0,1).rotate(bounceObject.getRotation());
        Vector2 longside = new Vector2(1,0).rotate(bounceObject.getRotation());
        Vector2 cornerStraightBack = new Vector2(ballVelocity.rotate90(1).nor());

        /*
        Testing ball vector towards bounce object vertices to determine which face is hit
        returns as decimal value between 0-15
        */
        float[] ballVertices = ball.getBallBounds().getTransformedVertices();
        Vector2 ballCentrePos = new Vector2(ball.getPosition().x + Constants.BALL_WIDTH/2,ball.getPosition().y- Constants.BALL_HEIGHT/2);
        Vector2 ballTopLeft = new Vector2(ballVertices[0],ballVertices[1]);
        Vector2 ballTopRight = new Vector2(ballVertices[2],ballVertices[3]);
        Vector2 ballBotRight = new Vector2(ballVertices[4],ballVertices[5]);
        Vector2 ballBotLeft = new Vector2(ballVertices[6],ballVertices[7]);

        // Checking if ball vertices + velocity are withing the bounce object
//        boolean[] collides = vertexCollisions(ball.getBallBounds().getTransformedVertices(), ballVelocity, bounceObject);
        String collision = "";
        switch (direction){
            case "up-right":
                //going up-right
                Logger.d("upright");
                collision = vertexCollisions(ballTopLeft, ballTopRight, ballBotRight, ballVelocity, bounceObject);
                break;
            case "up-left":
                //going up-left
                Logger.d("upleft");
                collision = vertexCollisions(ballBotLeft, ballTopLeft, ballTopRight, ballVelocity, bounceObject);
                break;
            case "down-right":
                //going down-right
                Logger.d("downright");
                collision = vertexCollisions(ballTopRight, ballBotRight, ballBotLeft, ballVelocity, bounceObject);
                break;
            case "down-left":
                //going down-left
                Logger.d("downleft");
                collision = vertexCollisions(ballBotRight, ballBotLeft, ballTopLeft, ballVelocity, bounceObject);
                break;
            case "up":
                //going up
                Logger.d("up");
                collision = vertexCollisions(ballTopRight , ballCentrePos , ballBotRight , ballVelocity, bounceObject);
                break;
            case "down":
                //going down
                Logger.d("down");
                collision = vertexCollisions(ballBotRight , ballCentrePos , ballBotLeft , ballVelocity, bounceObject);
                break;
            case "right":
                //going right
                Logger.d("right");
                collision = vertexCollisions(ballTopRight, ballCentrePos, ballBotRight, ballVelocity, bounceObject);
                break;
            case "left":
                //going left
                Logger.d("left");
                collision = vertexCollisions(ballBotLeft, ballCentrePos, ballTopLeft, ballVelocity, bounceObject);
                break;
            default:
                //ball stands still
                break;
        }
        switch (collision){
            case "shortside":
                Logger.d("collided with " + collision);
                surface = shortside;
                Logger.d("surface " + surface);
                break;
            case "longside":
                Logger.d("collided with " + collision);
                surface = longside;
                Logger.d("surface " + surface);
                break;
            default:
                Logger.d("collided with " + collision);
                surface = cornerStraightBack;
                Logger.d("surface " + surface);
                break;
        }

        return surface;
    }

    /**
     * Checks Which side of the brick the ball collides with
     * @param rightVertex
     * @param forwardVertex
     * @param leftVertex
     * @param velocity
     * @param bounceObject
     * @return
     */
    public static String vertexCollisions(Vector2 rightVertex, Vector2 forwardVertex, Vector2 leftVertex, Vector2 velocity, Polygon bounceObject){
        String side = "";

        Vector2 rightNext = new Vector2(rightVertex.x + velocity.x, rightVertex.y + velocity.y);
        Vector2 forwardNext = new Vector2(forwardVertex.x + velocity.x, forwardVertex.y + velocity.y);
        Vector2 leftNext = new Vector2(leftVertex.x + velocity.x, leftVertex.y + velocity.y);

        Vector2 rightmiddlepoint = new Vector2((forwardVertex.x+rightVertex.x)/2,(forwardVertex.y+rightVertex.y)/2);
        Vector2 leftmiddlepoint = new Vector2((forwardVertex.x+rightVertex.x)/2,(forwardVertex.y+rightVertex.y)/2);

        Vector2 rmpNext = new Vector2(rightmiddlepoint.x + velocity.x, rightmiddlepoint.y + velocity.y);
        Vector2 lmpNext = new Vector2(leftmiddlepoint.x + velocity.x, leftmiddlepoint.y + velocity.y);

        ArrayList<Vector2> boundVertices = new ArrayList<Vector2>();
        for (int i = 0; i< bounceObject.getTransformedVertices().length/2;i++) {
            boundVertices.add(new Vector2(bounceObject.getTransformedVertices()[(int) (i*2)],bounceObject.getTransformedVertices()[(int) (i*2+1)]));
        }
        //ForwardPointRelation fpr
        String pointRelation = boundVectorRelation(forwardVertex,forwardNext,boundVertices);

        if ( !pointRelation.isEmpty() ){
            side = pointRelation;
        }else{


                String rightPointRelation = boundVectorRelation(rightVertex, rightNext, boundVertices);
                String leftPointRelation = boundVectorRelation(leftVertex, leftNext, boundVertices);
                if (rightPointRelation.isEmpty() && leftPointRelation.isEmpty()) {
                    return "";
                } else if (!rightPointRelation.isEmpty()) {
                    side = rightPointRelation;
                } else if (!leftPointRelation.isEmpty()) {
                    side = leftPointRelation;
                }
        }
        return side;
    }

    public static String boundVectorRelation(Vector2 lineStart, Vector2 lineEnd, ArrayList<Vector2> bounds){
        ArrayList<Integer> vertices = new ArrayList<>(bounds.size());
        for( int i = 0; i<bounds.size(); i++){
            vertices.add(Intersector.pointLineSide(lineStart,lineEnd,bounds.get(i)));
        }
//        Logger.d("BoundVectorRelation"+vertices.toString());
        if ( (vertices.get(0)==1 && vertices.get(1)==-1) || (vertices.get(2)==1 && vertices.get(3)==-1) ) {
            return "longside";
        }else if( (vertices.get(1)==1 && vertices.get(2)==-1) || (vertices.get(3)==1 && vertices.get(0)==-1) ){
            return "shortside";
        }
        return "";
    }

    public static Vector2 avoidCoreSticking(Ball ball,Brick brick){
        Vector2 position = new Vector2(ball.getPosition());
        String direction = ballDirection(ball.getVelocity());

        switch (direction){
            case "up-right":
                //going up-right
                position.set(brick.getPosition().x-Constants.BALL_WIDTH-1, brick.getPosition().y-Constants.CORE_BRICK_HEIGHT-1);
                break;
            case "up-left":
                //going up-left
                position.set(brick.getPosition().x+Constants.CORE_BRICK_WIDTH+1, brick.getPosition().y-Constants.CORE_BRICK_HEIGHT-1);
                break;
            case "down-right":
                //going down-right
                position.set(brick.getPosition().x-Constants.BALL_WIDTH-1, brick.getPosition().y+Constants.BALL_HEIGHT+1);
                break;
            case "down-left":
                //going down-left
                position.set(brick.getPosition().x+Constants.CORE_BRICK_WIDTH+1, brick.getPosition().y+Constants.BALL_HEIGHT+1);
                break;
            case "up":
                //going up
                position.set(position.x, brick.getPosition().y-Constants.CORE_BRICK_HEIGHT);
                break;
            case "down":
                //going down
                position.set(position.x, brick.getPosition().y+Constants.CORE_BRICK_HEIGHT);
                break;
            case "right":
                //going right
                position.set(brick.getPosition().x-Constants.CORE_BRICK_WIDTH, position.y);
                break;
            case "left":
                //going left
                position.set(brick.getPosition().x+Constants.CORE_BRICK_WIDTH, position.y);
                break;
            default:
                //ball stands still
                break;
        }



        return position;
    }

//    Polygon ballBounds = ball.getBallBounds();
//        Array<Vector2> polygon = new Array<>();
//        float[] transformedBound = ballBounds.getTransformedVertices();
//        for(int i = 0; i<transformedBound.length; i+=2){
//            Vector2 point = new Vector2(transformedBound[i], transformedBound[i+1]);
//            polygon.add(point);
//        }
//        for(){
//          Intersector.isPointInPolygon(polygon,ball.getBallBounds()
//        }

    /**
     * Returns which direction the ball is going
     * @param ballVelocity
     * @return
     */
    public static String ballDirection(Vector2 ballVelocity){
        String direction = "";
        if(ballVelocity.x > 0 && ballVelocity.y > 0){
            direction = "up-right";
        }else if( ballVelocity.x > 0 && ballVelocity.y < 0 ){
            direction = "down-right";
        }else if( ballVelocity.x < 0 && ballVelocity.y > 0 ){
            direction = "up-left";
        }else if( ballVelocity.x < 0 && ballVelocity.y < 0){
            direction = "down-left";
        }else if( ballVelocity.x == 0 && ballVelocity.y > 0 ){
            direction = "down";
        }else if( ballVelocity.x == 0  && ballVelocity.y < 0 ){
            direction = "up";
        }else if( ballVelocity.x > 0 && ballVelocity.y == 0 ){
            direction = "right";
        }else if( ballVelocity.x < 0 && ballVelocity.y == 0 ){
            direction = "left";
        }
        return direction;
    }
}

package xyz.tdt4240.breakin.application;


/**
 * Global constants used by different parts of the application
 */
public final class Constants {

    public final static int ONE_SECOND = 1000;

    //Maximum amount of "stars" the user can obtain pr level
    public final static int RATING_LEVELS = 3;

    //How long the user minimum has to wait before exiting the game over state
    public final static long MIN_GAME_OVER_TIME = ONE_SECOND;

    //Paths to different textures
    public final static String TEXTURE_BACKGROUND = "textures/bg.png";
    public final static String TEXTURE_PADDLE_BLUE = "textures/paddle_blue.png";
    public final static String TEXTURE_PADDLE_BLUE_WIDE = "textures/paddle_blue_wide.png";
    public final static String TEXTURE_PADDLE_RED = "textures/paddle_red.png";
    public final static String TEXTURE_PADDLE_RED_WIDE = "textures/paddle_red_wide.png";
    public final static String TEXTURE_BRICK_CORE = "textures/brick_core.png";
    public final static String TEXTURE_BRICK_CORE1 = "textures/brick_core1.png";
    public final static String TEXTURE_BRICK_CORE2 = "textures/brick_core2.png";
    public final static String TEXTURE_BRICK_CORE3 = "textures/brick_core3.png";
    public final static String TEXTURE_BRICK_CORE4 = "textures/brick_core4.png";
    public final static String TEXTURE_BRICK_CORE5 = "textures/brick_core5.png";
    public final static String TEXTURE_BRICK_CORE6 = "textures/brick_core6.png";
    public final static String TEXTURE_BRICK_NORMAL = "textures/brick_normal.png";
    public final static String TEXTURE_BRICK_SPEED = "textures/brick_speed.png";
    public final static String TEXTURE_BRICK_SLOW = "textures/brick_slow.png";
    public final static String TEXTURE_BRICK_BIGGER_PADDLE = "textures/brick_bigger_paddle.png";
    public final static String TEXTURE_BRICK_HARD = "textures/brick_hard.png";
    public final static String TEXTURE_BALL_BLUE = "textures/ball_blue.png";
    public final static String TEXTURE_BALL_RED = "textures/ball_red.png";

    //Paths to different sound files
    public final static String SOUND_WALL_HIT = "sounds/wall_hit.wav";
    public final static String SOUND_BRICK_BREAK = "sounds/brick_break.wav";
    public final static String SOUND_HARD_BRICK_HIT = "sounds/fat_hit.wav";
    public final static String SOUND_PADDLE_HIT = "sounds/ping_pong_hit.wav";
    public final static String SOUND_CORE_BRICK_HIT = "sounds/glass_break_long.wav";
    public final static String SOUND_VICTORY = "sounds/tadaa.wav";
    public final static String SOUND_GAME_MUSIC = "sounds/game_music.mp3";

    //Game modes supported
    public final static String GAME_MODE_COOPERATIVE = "game_mode_cooperative"; //Multiplayer cooperative
    public final static String GAME_MODE_COMPETITIVE = "game_mode_competitive"; //Multiplayer competitive
    public final static String GAME_MODE_NORMAL = "game_mode_normal"; //Singleplayer
    public final static String DEFAULT_MULTIPLAYER_GAME_MODE = GAME_MODE_COMPETITIVE;

    public final static int LEVEL_TYPE_SINGLEPLAYER = 1;
    public final static int LEVEL_TYPE_MULTIPLAYER = 2;
    public final static int LEVEL_TYPE_BOTH = 3;


    //Different movement patterns that a level can implement
    public final static String MOVEMENT_PATTERN_CIRCULAR = "movement_pattern_circular";


    //Brick types
    public final static String BRICK_TYPE_NORMAL = "brick_type_normal";
    public final static String BRICK_TYPE_HARD = "brick_type_hard";
    public final static String BRICK_TYPE_SLOW = "brick_type_slow";
    public final static String BRICK_TYPE_SPEED = "brick_type_speed";
    public final static String BRICK_TYPE_BIGGER_PADDLE = "brick_type_bigger_paddle";
    public final static String BRICK_TYPE_CORE = "brick_type_core";

    //
    public final static  int[] BRICKS_PER_LEVEL = {22, 18, 12,};

    //Brick scores
    public final static int BRICK_SCORE_DEFAULT = 100;
    public final static int BRICK_SCORE_NORMAL = 100;
    public final static int BRICK_SCORE_HARD = 300;
    public final static int BRICK_SCORE_SLOW = 150;
    public final static int BRICK_SCORE_SPEED = 150;
    public final static int BRICK_SCORE_BIGGER_PADDLE = 50;
    public final static int BRICK_SCORE_CORE = 500;

    //Player IDs
    public final static int PLAYER_1 = 1;
    public final static int PLAYER_2 = 2;

    //How many hits the bricks can take before breaking
    public final static int DEFAULT_BRICK_HEALTH = 1;
    public final static int HARD_BRICK_HEALTH = 3;

    //Default brick position
    public final static int DEFAULT_BRICK_X = 0;
    public final static int DEFAULT_BRICK_Y = 0;

    //Brick width / height
    public final static int BRICK_WIDTH = ScreenHandler.percentToPixWidth(0.105);
    public final static int BRICK_HEIGHT = ScreenHandler.percentToPixHeight(0.015);

    public final static int CORE_BRICK_WIDTH = ScreenHandler.percentToPixWidth(0.148);
    public final static int CORE_BRICK_HEIGHT = ScreenHandler.percentToPixHeight(0.089);


    public final static int SCORE_FIELD_FONT_SCALE = 4;

    public final static int SCORE_FIELD_HORIZONTAL_OFFSET = ScreenHandler.percentToPixWidth(0.046);
    public final static int SCORE_FIELD_VERTICAL_OFFSET = ScreenHandler.percentToPixHeight(0.028);


    //Paddle width / height
    public final static int PADDLE_WIDTH = ScreenHandler.percentToPixWidth(0.28);
    public final static int PADDLE_HEIGHT = ScreenHandler.percentToPixHeight(0.027);

    public final static int PADDLE_VERTICAL_OFFSET = PADDLE_HEIGHT;

    //Paddle width after triggering BiggerPaddleBrick
    public final static int BIG_PADDLE_WIDTH = ScreenHandler.percentToPixWidth(0.46);

    //How long the "big paddle" will last
    public final static long BIG_PADDLE_TIME = 5 * ONE_SECOND;

    //How long slow/speed effect will last
    public final static int SLOW_BRICK_TIME = 3 * ONE_SECOND;
    public final static int SPEED_BRICK_TIME = 3 * ONE_SECOND;

    //Ball width / height
    public final static int BALL_WIDTH = ScreenHandler.percentToPixWidth(0.037);
    public final static int BALL_HEIGHT = ScreenHandler.percentToPixHeight(0.022);

    public final static int BALL_VERTICAL_OFFSET = ScreenHandler.percentToPixHeight(0.04);

    //How long the player has to wait before the ball will start moving again
    //after the ball passed the paddle
    public final static long DELAY_AFTER_BALL_MISS = 500;

    //How many percents of the player score will be removed when missing a ball
    public final static float BALL_MISS_PENALTY = 0.33f;

    //Ball velocity
    public final static int BALL_VELOCITY = ScreenHandler.percentToPixHeight(0.01);

    //Vertices
    public final static float[] PADDLE_VERTICES = new float[]{
            0,0,
            PADDLE_WIDTH,0,
            PADDLE_WIDTH,PADDLE_HEIGHT,
            0,PADDLE_HEIGHT
    };


    public final static float[] DEFAULT_BRICK_VERTICES = new float[]{
            0,0,
            Constants.BRICK_WIDTH, 0,
            Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT,
            0, Constants.BRICK_HEIGHT,
    };



    public final static float[] BALL_VERTICES = new float[]{
            0,0,
            BALL_WIDTH,0,
            BALL_WIDTH,BALL_HEIGHT,
            0,BALL_HEIGHT
    };


    private Constants(){}

}

package xyz.tdt4240.breakin.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import xyz.tdt4240.breakin.application.Constants;

/**
 * Singleton for loading and playing audio.
 */
public final class SoundHandler {

    private Sound brickBreak;
    private Sound wallHit;
    private Sound hardBrickHit;
    private Sound paddleHit;
    private Sound coreBrickHit;
    private Sound victory;
    private Sound gameMusic;

    private static SoundHandler instance;

    private SoundHandler(){
        brickBreak = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_BRICK_BREAK));
        wallHit = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_WALL_HIT));
        hardBrickHit = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_HARD_BRICK_HIT));
        paddleHit = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_PADDLE_HIT));
        coreBrickHit = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_CORE_BRICK_HIT));
        victory = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_VICTORY));
        gameMusic = Gdx.audio.newSound(Gdx.files.internal(Constants.SOUND_GAME_MUSIC));
    }

    public static SoundHandler getInstance(){

        if(instance == null)
            instance = new SoundHandler();

        return instance;
    }

    public static void init(){
        if(instance == null)
            instance = new SoundHandler();
    }

    public void playBrickBreakSound(){
        brickBreak.play();
    }

    public void playWallHitSound(){
        wallHit.play();
    }

    public void playHardBrickHitSound(){
        hardBrickHit.play();
    }

    public void playPaddleHitSound(){
        paddleHit.play();
    }

    public void playCoreBrickHitSound(){
        coreBrickHit.play();
    }

    public void playVictorySound(){
        victory.play();
    }

    public void playGameMusic(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                long timeStarted = System.currentTimeMillis();

                while ((System.currentTimeMillis() - timeStarted) >= 1000 || gameMusic.loop(10) <= 0){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        return;
                    }
                }

            }
        }).start();
    }

    public void stopGameMusic(){
        gameMusic.stop();
    }

}

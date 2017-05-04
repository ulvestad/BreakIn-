package xyz.tdt4240.breakin.application;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Deals with screen dimensions and scaling
 */
public final class ScreenHandler {

    private static int SCREEN_WIDTH;

    private static int SCREEN_HEIGHT;

    private static boolean isInitialized;

    public static void init(Context context){

        if(isInitialized)
            return;

        isInitialized = true;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH = size.x;
        SCREEN_HEIGHT = size.y;

    }

    public static int getWidth(){
        return SCREEN_WIDTH;
    }

    public static int getHeight(){
        return SCREEN_HEIGHT;
    }

    //Convert percentage to pixels relative to the screen width
    public static int percentToPixWidth(double percent){
        return (int)(percent * SCREEN_WIDTH);
    }

    //Convert percentage to pixels relative to the screen height
    public static int percentToPixHeight(double percent){
        return (int)(percent * SCREEN_HEIGHT);
    }

}

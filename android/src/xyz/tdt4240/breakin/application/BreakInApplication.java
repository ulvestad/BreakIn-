package xyz.tdt4240.breakin.application;


import com.orhanobut.logger.Logger;
import com.orm.SugarApp;
import com.orm.SugarContext;

import xyz.tdt4240.breakin.models.Level;

/**
 * Initializes modules that are used by the rest of the application
 */
public class BreakInApplication extends SugarApp {

    @Override
    public void onCreate(){
        super.onCreate();

        Logger.d("Starting application");

        SugarContext.init(this);

        PreferenceHandler.init(this);

        ScreenHandler.init(this);

        //This will only run the first time the app is started
        if(PreferenceHandler.getObject(Tags.FIRST_START, Boolean.class) == null){
            PreferenceHandler.setObject(Tags.FIRST_START, false);
            SeedHelper.seedLevels();
        }

    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        SugarContext.terminate();
    }

}
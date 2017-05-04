package xyz.tdt4240.breakin.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Wrapper class for managing stored preferences
 */
public final class PreferenceHandler {
    private PreferenceHandler(){}

    private final static String EMPTY_JSON = "[]";

    private static Gson gson;

    public static SharedPreferences preferences;

    public static SharedPreferences.Editor editor;

    private static boolean isInitialized;

    public static void init(Context context){

        if(isInitialized)
            return;

        isInitialized = true;

        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        editor = preferences.edit();

        gson = new Gson();

    }

    public static void setObject(String tag, Object obj){
        editor.putString(tag, gson.toJson(obj)).apply();
    }

    public static <T> T getObject(String tag, Class<T> objectClass){

        String jsonStr = preferences.getString(tag, EMPTY_JSON);

        if(jsonStr.equals(EMPTY_JSON))
            return null;
        else
            return gson.fromJson(jsonStr, objectClass);

    }

    public static <T> T getObject(String tag, Class<T> objectClass, Object defaultValue){

        String jsonStr = preferences.getString(tag, EMPTY_JSON);

        if(jsonStr.equals(EMPTY_JSON))
            return (T) defaultValue;
        else
            return (T) gson.fromJson(jsonStr, objectClass);

    }

}
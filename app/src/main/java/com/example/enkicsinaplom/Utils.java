package com.example.enkicsinaplom;

import android.app.Activity;
import android.content.Intent;

import com.example.enkicsinaplom.R;

public class Utils
{
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WHITE = 1;
    public final static int THEME_RED = 2;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, boolean select)
    {
        if (select)
            sTheme = THEME_DEFAULT;
        else
            sTheme = THEME_RED;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.Default);
                break;
            case THEME_RED:
                activity.setTheme(R.style.MyTheme);
                break;
        }
    }
}

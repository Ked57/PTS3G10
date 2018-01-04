package ked.pts3g10.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Clement on 04/01/2018.
 */

public class Preferences {

    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_PASS= "pass";
    static final String PREF__USER_VERSION = "version";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }
    public static void setUserPass(Context ctx, String userPass)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PASS, userPass);
        editor.commit();
    }

    public static String getUserPass(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_PASS, "");
    }
    public static void setVersion(Context ctx, int version)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF__USER_VERSION, version);
        editor.commit();
    }

    public static int getVersion(Context ctx)
    {
        return getSharedPreferences(ctx).getInt(PREF__USER_VERSION, 0);
    }
}

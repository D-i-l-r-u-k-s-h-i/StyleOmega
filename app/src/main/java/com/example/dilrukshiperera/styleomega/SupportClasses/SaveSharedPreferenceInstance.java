package com.example.dilrukshiperera.styleomega.SupportClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreferenceInstance {

    static final String SP_USER="email";

    //pass the instance of context (Activity or fragment) which uses it
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    //set value, user-email at the creation of the sharedpreference
    public static void setUserEmail(Context ctx, String uEmail)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SP_USER, uEmail);
        editor.commit();
    }
    //retrieve values passed at the creation of shared preference
    public static String getUserEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(SP_USER, "");
    }
    //clear shared preference for logout
    public static void clearUser(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
}

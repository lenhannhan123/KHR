package fpt.aptech.khrmobile.Config;

import android.content.Context;
import android.content.SharedPreferences;

import fpt.aptech.khrmobile.MainActivity;

public class ConfigData {

    public static String IP ="192.168.1.64";
    public static final String profilePreferences = "profilepref";
    public static final String Mail = "mailKey";

    public String userId(Context context){
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(profilePreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
       return sharedPreferences.getString(Mail,null);
    }




}

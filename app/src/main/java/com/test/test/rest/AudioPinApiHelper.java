package com.test.test.rest;

import android.content.Context;

import com.test.test.ui.R;

/**
 * Created by shahed on 28/08/2016.
 */
public class AudioPinApiHelper {
    //private static String BASE_URL = "https://api.knurld.io/";
    private static String BASE_URL = "https://b739ce.poc.knuedge.com/";
    private static String APP_MODEL_BASE_URL = BASE_URL + "/app-models/";
    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }
    public static String getBaseUrl(){
        return BASE_URL;
    }
    public static String getClientId(){
        return mContext.getString(R.string.client_id);
    }
    public static String getClientSecret(){
        return  mContext.getString(R.string.client_secret);
    }
    public static String getAppModelUrl(){
        return APP_MODEL_BASE_URL + mContext.getString(R.string.app_model);
    }

}

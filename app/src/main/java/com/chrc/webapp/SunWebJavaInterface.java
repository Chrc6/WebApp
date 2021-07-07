package com.chrc.webapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author : chrc
 * date   : 2021/7/4  1:10 PM
 * desc   :
 */
public class SunWebJavaInterface {

    public static final String JAVA_KEY = "sunshine";

    private FirebaseAnalytics mFirebaseAnalytics;

    public SunWebJavaInterface(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    /**
     *
     * @param key   事件key
     * @param data  事件value
     */
    @JavascriptInterface
    public void firebaseLogEvent(String key, String data) {
        Log.d("main===","firebaseLogEvent key="+key+" data="+data);
        Bundle bundle = new Bundle();
        SunShineJsAnalyData sunShineJsAnalyData = null;
        try {
            sunShineJsAnalyData = new Gson().fromJson(data, SunShineJsAnalyData.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (sunShineJsAnalyData != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(sunShineJsAnalyData.getId()));
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, sunShineJsAnalyData.getName());
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, sunShineJsAnalyData.getContentType());
        } else {
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, data);
        }
        mFirebaseAnalytics.logEvent(key, bundle);
    }

    public void firebaseLogEvent(String key, Bundle bundle) {
        mFirebaseAnalytics.logEvent(key, bundle);
    }
}

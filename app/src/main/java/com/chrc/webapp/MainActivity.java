package com.chrc.webapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.just.agentweb.AgentWeb;

public class MainActivity extends BaseWebActivity {
    private static final String URL = "url";
    private int i = 0;

    public static void start(Context context, String url) {
        Intent starter = new Intent(context,MainActivity.class);
        starter.putExtra(URL, url);
        context.startActivity(starter);
    }

    @Override
    protected void addFirstView(LinearLayout mLinearLayout) {
        super.addFirstView(mLinearLayout);
//        Log.d("main===","addFirstView");
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_main_head, mLinearLayout, false);
//        mLinearLayout.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
    }

    @Override
    protected void testOnclick() {
        super.testOnclick();
//        Log.d("main===","mLinearLayout setOnClickListener i="+i);
//        final SunWebJavaInterface sunWebJavaInterface = new SunWebJavaInterface(this);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(System.currentTimeMillis()));
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"test");
//        sunWebJavaInterface.firebaseLogEvent("test"+i, bundle);
//        i++;
//        Log.d("main===","firebaseLogEvent i="+i);
    }

    @Override
    protected String getJsKey() {
        return DifPackageConfigUtil.Companion.getJsKey();
    }

    @Override
    protected Object getJsObj() {
        return DifPackageConfigUtil.Companion.getJsObj(this);
    }

    @Override
    protected String getUrl() {
        return DifPackageConfigUtil.Companion.getUrl();
    }

}

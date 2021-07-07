package com.chrc.webapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gyf.barlibrary.ImmersionBar;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

public abstract class BaseWebActivity extends BaseActivity {
    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private AlertDialog mAlertDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0);
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.transparent)
                .init();
        setContentView(mLinearLayout);
        addFirstView(mLinearLayout);
        long p = System.currentTimeMillis();

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testOnclick();
            }
        });

        AgentWeb.PreAgentWeb ready = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .addJavascriptInterface(getJsKey(), getJsObj())
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready();
        WebSettings webSettings = ready.get().getAgentWebSettings().getWebSettings();
//        webSettings.setUserAgentString(webSettings.getUserAgentString() + "/sunshine_apk_ua/Android_Webview/" + BuildConfig.VERSION_CODE);
//        webSettings.setUserAgentString(webSettings.getUserAgentString() + "/luckyabc_apk_ua/Android_Webview/" + BuildConfig.VERSION_CODE);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + getUa() + BuildConfig.VERSION_CODE);
        Log.d("user_agent===","ua="+webSettings.getUserAgentString());
        mAgentWeb = ready.go(getUrl());
        long n = System.currentTimeMillis();
//        Log.i("Info", "init used time:" + (n - p));


    }

    private String getUa() {
        return DifPackageConfigUtil.Companion.getUa();
    }

    protected void testOnclick() {

    }

    protected Object getJsObj() {
        return new Object();
    }

    protected String getJsKey() {
        return "test";
    }

    protected void addFirstView(LinearLayout mLinearLayout) {

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            String flag = "https://play.google.com/store/apps/details?id=";
            if (!TextUtils.isEmpty(url) && url.startsWith(flag)) {
                openBrowser(url);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, request);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
//            Log.i("Info", "BaseWebActivity onPageStarted");
        }

    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        // For Android 3.0+
        @Override
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            super.openFileChooser(valueCallback);
//            Log.d("webview===","openFileChooser(ValueCallback<Uri> valueCallback)");
        }

        // For Android 3.0+
        @Override
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            super.openFileChooser(valueCallback, acceptType);
//            Log.d("webview===","openFileChooser(ValueCallback<Uri> 2)");
        }

        // For Android 4.1
        @Override
        public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
            super.openFileChooser(uploadFile, acceptType, capture);
//            Log.d("webview===","openFileChooser(ValueCallback<Uri> 3)");
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//            Log.d("webview===","onShowFileChooser");
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }

    };

    protected abstract String getUrl();

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
    private void openBrowser(String targetUrl) {
        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            Toast.makeText(this, targetUrl + " 该链接无法使用浏览器打开。", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri mUri = Uri.parse(targetUrl);
        intent.setData(mUri);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}

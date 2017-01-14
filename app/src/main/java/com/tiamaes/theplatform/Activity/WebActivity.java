package com.tiamaes.theplatform.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tiamaes.theplatform.R;
import com.tiamaes.theplatform.utils.ToastUtils;

/**
 * Created by ssn on 2016/12/8.
 */

public class WebActivity extends AppCompatActivity {
    private WebView mWebView;
    private Dialog dialog;
    private ProgressBar bar;
    private String loadUrl;
    private Context context;
    private long clickTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webactivity_layout);
        loadUrl = getIntent().getStringExtra("url");
        dialog = ProgressDialog.show(this, "", "正在加载...");
        dialog.setCancelable(true);
        initSettings();
        mWebView.loadUrl(loadUrl);
    }

    private void initSettings() {
        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        // 开启Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 启用localStorage 和 essionStorage
        webSettings.setDomStorageEnabled(true);
        // 开启应用程序缓存
        String appCacheDir = this.getApplicationContext()
                .getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAllowFileAccess(true);
        // // 启用Webdatabase数据库
        // webSettings.setDatabaseEnabled(true);
        // String databaseDir = this.getApplicationContext()
        // .getDir("database", Context.MODE_PRIVATE).getPath();
        // webSettings.setDatabasePath(databaseDir);// 设置数据库路径
        //
        // // 启用地理定位
        // webSettings.setGeolocationEnabled(true);
        // // 设置定位的数据库路径
        // webSettings.setGeolocationDatabasePath(databaseDir);

        // 开启插件（对flash的支持）
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // webSettings.setBuiltInZoomControls(true);
        webSettings
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setGeolocationEnabled(true);
        webSettings
                .setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        webSettings.setDomStorageEnabled(true);
        mWebView.requestFocus();
        // mWebView.setScrollBarStyle(0);

        mWebView.setWebChromeClient(mChromeClient);
        mWebView.setWebViewClient(mWebViewClient);

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        // 处理页面导航
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebView.loadUrl(url);
            // Android中返回True的意思就是到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private WebChromeClient mChromeClient = new WebChromeClient() {

        private View myView = null;
        private CustomViewCallback myCallback = null;

        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            Log.e("JSresult", result.toString());
            return super.onJsAlert(view, url, message, result);
        }

        public boolean onJsBeforeUnload(WebView view, String url,
                                        String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        public boolean onJsTimeout() {
            return super.onJsTimeout();
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                bar.setVisibility(View.GONE);
            } else {
                if (View.GONE == bar.getVisibility()) {
                    bar.setVisibility(View.VISIBLE);
                }
                bar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        // 配置权限 （在WebChromeClinet中实现）
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

        // Android 使WebView支持HTML5 Video（全屏）播放的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null;
                return;
            }

            ViewGroup parent = (ViewGroup) mWebView.getParent();
            parent.setBackgroundResource(android.R.color.black);
            parent.removeView(mWebView);
            parent.addView(view, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT));
            myView = view;
            myCallback = callback;
            mChromeClient = this;
        }

        @Override
        public void onHideCustomView() {
            if (myView != null) {
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                }

                ViewGroup parent = (ViewGroup) myView.getParent();
                // parent.setBackgroundResource(R.color.background_content);
                parent.removeView(myView);
                parent.addView(mWebView);
                myView = null;
            }
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
         /*   if (mWebView.canGoBack() && !mWebView.getUrl().startsWith(loadUrl)) {
                mWebView.goBack();
                return true;
            }
            // } else {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }*/
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                ToastUtils.showShortToast(WebActivity.this, "再次点击退出应用");
                clickTime = System.currentTimeMillis();
            } else {
                finish();
            }
            // }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onResume() {
        /*
         * if (getRequestedOrientation() !=
		 * ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
		 * setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); }
		 */
        super.onResume();
        if (null != mWebView)
            mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mWebView)
            mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (null != mWebView)
            mWebView.destroy();
    }
}

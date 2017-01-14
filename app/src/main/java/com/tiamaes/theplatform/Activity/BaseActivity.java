package com.tiamaes.theplatform.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import com.tiamaes.theplatform.base.BaseApplication;
import com.tiamaes.theplatform.utils.CollectRms;
import com.tiamaes.theplatform.utils.UIHelper;

import org.xutils.x;

import java.lang.reflect.Field;


/**
 * Created by ssn on 2016/12/27.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * LOG打印标签
     */
    private static final String TAG = BaseActivity.class.getSimpleName();
    /**
     * 全局的Context {@link #mContext = this;}
     */
    protected Context mContext;
    /**
     * intent传递
     */
    protected Intent intent;
    /**
     * Bundle传递值
     */
    protected Bundle bundle;
    protected static int mScreenWidth;
    protected static int mScreenHeight;
    protected static float mScreenDensity;
    public CollectRms crm;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = this;
        ((BaseApplication) this.getApplication()).addActivity(this);
        intent = new Intent();
        bundle = new Bundle();
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mScreenDensity = dm.density;
        crm=new CollectRms(this);
    }

    /**
     * 获取全局的Context
     * @return {@link #mContext = this;}
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 退出当前Activity
     */
    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 隐藏输入法
     */
    public void hideInput(View view){
        InputMethodManager inputMethodManager=(InputMethodManager)view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * intent传递
     */
    public void  passNext(Context from,Class<?> to,Bundle bundle){
        if (null!=intent) {
            if (null!=bundle) {
                startActivity(intent.setClass(from, to).putExtra("fromActivity", bundle));
            }else {
                startActivity(intent.setClass(from, to));
            }
        }else{
            if (null !=bundle) {
                startActivity(new Intent().setClass(from, to).putExtra("fromActivity", bundle));
            }else {
                startActivity(new Intent().setClass(from, to));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.getClass().getName());
//        MobclickAgent.onPause(this);
        //隐藏键盘
        UIHelper.popSoftkeyboard(this, this.getWindow().getDecorView(), false);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //隐藏键盘
        UIHelper.popSoftkeyboard(this, this.getWindow().getDecorView(), false);
        BaseApplication application = (BaseApplication) getApplication();
        application.finishActivity(this);
    }

    /**
     * 屏蔽掉物理Menu键，不然在有物理Menu键的手机上，overflow按钮会显示不出来。 *
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/
    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(this.getClass().getName());
//        MobclickAgent.onResume(this);
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }



    private boolean isFirstEnter(String preStr) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "share", this.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean(preStr, true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!isFirstRun) {
            return false;
        } else {
            editor.putBoolean(preStr, false);
            editor.commit();
            return true;
        }
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }
}

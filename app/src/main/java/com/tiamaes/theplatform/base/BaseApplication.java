package com.tiamaes.theplatform.base;

import android.app.Activity;
import android.app.Application;

import com.tiamaes.theplatform.Activity.BaseActivity;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by ssn on 2016/12/27.
 */

public class BaseApplication extends Application {
    /**
     * Activity集合
     */
    private static ArrayList<BaseActivity> activitys = new ArrayList<BaseActivity>();

    /**
     * 对外提供整个应用生命周期的Context
     */
    public static BaseApplication contextInstance;

    private DbManager.DaoConfig daoConfig;
    public static DbManager db;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        daoConfig = new DbManager.DaoConfig()
                .setDbName(Constant.databaseName)//创建数据库的名称
                .setDbVersion(1)//数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });//数据库更新操作
        db = x.getDb(daoConfig);
    }

    /**
     * 添加Activity到ArrayList<Activity>管理集合
     * @param activity
     */
    public void addActivity(BaseActivity activity) {
        String className = activity.getClass().getName();
        for (Activity at : activitys) {
            if (className.equals(at.getClass().getName())) {
                activitys.remove(at);
                break;
            }
        }
        activitys.add(activity);
    }

    /**
     * 退出应用程序的时候，手动调用
     */
    @Override
    public void onTerminate() {
        removeAllActivity();
        super.onTerminate();
    }

    public static void removeAllActivity() {
        for (BaseActivity activity : activitys) {
            activity.finish();
        }
    }

    /**
     * 返回当前堆栈
     * @return
     */
    public static Activity currentActivity() {
        return activitys.get(activitys.size() - 1);
    }

    /**
     * 删除指定的Activity
     * @param className
     */
    public static void finishActivity(String className){
        for (Activity at : activitys) {
            if (className.equals(at.getClass().getName())) {
                activitys.remove(at);
                at.finish();
                break;
            }
        }
    }

    //退出栈顶Activity
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            //在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activity.finish();
            activitys.remove(activity);
            activity = null;
        }
    }

    public static void finishActivity(Class activity) {
        for (Activity at : activitys) {
            if (activity.equals(at.getClass())) {
                activitys.remove(at);
                at.finish();
                break;
            }
        }
    }

    /**
     *获取指定类名的Activity
     */
    public static Activity getActivity(Class<?> cls) {
        for (Activity activity : activitys) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 对外提供Application Context
     * @return
     */
    public static BaseApplication getContextInstance() {
        return contextInstance;
    }
}

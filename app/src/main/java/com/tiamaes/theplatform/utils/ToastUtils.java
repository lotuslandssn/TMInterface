package com.tiamaes.theplatform.utils;

import android.content.Context;

/**
 * Created by ssn on 2016/12/8.
 */

public class ToastUtils {
    public static android.widget.Toast Toast;

    public static void showShortToast(Context context, String msg) {
        if (Toast == null) {
            Toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        Toast.setText(msg);
        Toast.show();
    }

    public static void showLongToast(Context context, String msg) {
        if (Toast == null) {
            Toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        }
        Toast.setText(msg);
        Toast.show();
    }

}

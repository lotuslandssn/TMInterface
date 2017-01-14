package com.tiamaes.theplatform.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;

import com.tiamaes.theplatform.base.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * @author xiaoman
 * @Date 2015-02-01
 */
public class UIHelper {
	
	/**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * 
     * @param context
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (pxValue / scale + 0.5f); 
    } 
   
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * 
     * @param context
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */ 
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density; 
        return (int) (dipValue * scale + 0.5f); 
    } 
   
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param context
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 
   
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param context
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    }

	/**
	 * 打开或关闭键盘
	 * @param ctx
	 * @param view
	 * @param wantPop
	 */
	public static void popSoftkeyboard(Context ctx, View view, boolean wantPop) {
		InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (wantPop) {
			view.requestFocus();
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		} else {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
    
//    /**
//     * 显示进度对话框
//     * @param context
//     * @param title
//     * @return
//     */
//    public static SweetAlertDialog showProgressSweetDailog(Context context, String title){
//    	final SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//		sDialog.setTitleText(title);
//		sDialog.setCancelable(false);
//		sDialog.setCanceledOnTouchOutside(true);
//		sDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.action_bar_bg));
//		sDialog.show();
//		return sDialog;
//    }

//	/**
//     * 显示进度对话框
//     * @param dialog
//     * @param title
//     * @return
//     */
//    public static SweetAlertDialog showProgressSweetDailog(SweetAlertDialog dialog, String title){
//    	if (dialog == null) {
//    		final SweetAlertDialog sDialog = new SweetAlertDialog(BaseApplication.getContextInstance(), SweetAlertDialog.PROGRESS_TYPE);
//    		sDialog.setTitleText(title);
//    		sDialog.setCancelable(false);
//    		sDialog.getProgressHelper().setBarColor(BaseApplication.getContextInstance().getResources().getColor(R.color.action_bar_bg));
//    		sDialog.show();
//    		return sDialog;
//		}
//    	dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
//    	dialog.setTitleText(title);
//    	dialog.setCancelable(false);
//    	dialog.getProgressHelper().setBarColor(BaseApplication.getContextInstance().getResources().getColor(R.color.action_bar_bg));
//    	return dialog;
//    }
//
//    /**
//     * 显示警告对话框
//     * @param context
//     * @param title
//     * @return
//     */
//    public static SweetAlertDialog showWarningSweetDailog(Context context, String title){
//    	SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
//		sDialog.setTitleText(title);
//		sDialog.setCancelText(context.getString(R.string.common_cancle_str));
//		sDialog.setConfirmText(context.getString(R.string.common_ensure_str));
//		sDialog.showCancelButton(true);
//		sDialog.show();
//		return sDialog;
//    }
//
//    /**
//     * 显示警告对话框
//     * @param dialog
//     * @param title
//     * @return
//     */
//    public static SweetAlertDialog showWarningSweetDailog(SweetAlertDialog dialog, String title){
//    	if (dialog == null) {
//    		SweetAlertDialog sDialog = new SweetAlertDialog(BaseApplication.getContextInstance(), SweetAlertDialog.WARNING_TYPE);
//    		sDialog.setTitleText(title);
//    		sDialog.setCancelText(BaseApplication.getContextInstance().getString(R.string.common_cancle_str));
//    		sDialog.setConfirmText(BaseApplication.getContextInstance().getString(R.string.common_ensure_str));
//    		sDialog.showCancelButton(true);
//    		sDialog.show();
//    		return dialog;
//		}
//    	dialog.setTitleText(title)
//		.showCancelButton(true)
//		.setCancelText(BaseApplication.getContextInstance().getString(R.string.common_cancle_str))
//    	.setConfirmText(BaseApplication.getContextInstance().getString(R.string.common_ensure_str))
//		.setConfirmClickListener(null)
//		.changeAlertType(SweetAlertDialog.WARNING_TYPE);
//		return dialog;
//    }
//
//    /**
//     * 警告对话框,只有一个确定键
//     * @param mContext
//     * @param title
//     */
//	public static void showWarning(Context mContext,String title){
//		SweetAlertDialog dialog = UIHelper.showWarningSweetDailog(mContext, title);
//		dialog.setCancelable(false);
//		dialog.showCancelButton(false);
//	}
//
//    /**
//     * 显示成功对话框
//     * @param dialog
//     * @param title
//     * @return
//     */
//    public static SweetAlertDialog showSuccessSweetDailog(SweetAlertDialog dialog, String title){
//    	if (dialog == null) {
//    		SweetAlertDialog sDialog = new SweetAlertDialog(BaseApplication.getContextInstance(),
//    				SweetAlertDialog.SUCCESS_TYPE);
//    		sDialog.setTitleText(title);
////    		sDialog.setConfirmClickListener(null);
//    		sDialog.show();
//    		return dialog;
//		}
//    	dialog.setTitleText(title)
//		.showCancelButton(false)
////		.setConfirmClickListener(null)
//		.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//		return dialog;
//    }
//
//    /**
//     * 显示错误对话框
//     * @param dialog
//     * @param title
//     * @return
//     */
//    public static SweetAlertDialog showErrorSweetDailog(SweetAlertDialog dialog, String title){
//    	if (dialog == null) {
//    		SweetAlertDialog sDialog = new SweetAlertDialog(BaseApplication.getContextInstance(),
//    				SweetAlertDialog.ERROR_TYPE);
//    		sDialog.setTitleText(title);
//    		sDialog.setConfirmClickListener(null);
//    		sDialog.show();
//    		return dialog;
//		}
//    	dialog.setTitleText(title)
//		.showCancelButton(false)
//		.setConfirmClickListener(null)
//		.changeAlertType(SweetAlertDialog.ERROR_TYPE);
//		return dialog;
//    }
    
    
	public static void setListViewHeightBasedOnChildren(ExpandableListView listView) {
		// 获取ListView对应的Adapter
		BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	/**
	 * 拨打电话
	 *
	 * @param phone 要拨打的电话
	 */
	public static void call(Context context, String phone) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
		//context.startActivity(intent);
	}

	/**
	 * 调用系统分享
	 * @param context
	 * @param text 分享的文本
	 */
	public static void shareText(Context context,String text) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, text);
		shareIntent.setType("text/plain");

		//设置分享列表的标题，并且每次都显示分享列表
		context.startActivity(Intent.createChooser(shareIntent, "分享到"));
	}

//	/**
//	 * 跳转到应用容器
//	 * @param context
//	 * @param title  应用标题
//	 * @param url  应用地址
//	 * @param isRefresh
//	 */
//	public static void toWebActivity(Context context, String title, String url, boolean isRefresh) {
//		Intent intent = new Intent(context, WebActivity.class);
//		intent.putExtra(WebActivity.TITLE, title);
//		intent.putExtra(WebActivity.URL, url);
//		intent.putExtra(WebActivity.REFRESH, isRefresh);
//		context.startActivity(intent);
//	}
//
//	public static WebFragment getWebFragment(String title, String url) {
//		WebFragment webFragment = new WebFragment();
//		webFragment.TITLE = title;
//		webFragment.URL = url;
//		return webFragment;
//	}

	/**
	 * 验证手机号
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){
		//Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		//Pattern p = Pattern.compile("[1][3578]\\\\d{9}");
		Pattern p = Pattern.compile("^(13|14|15|17|18)\\d{9}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.find();
	}

/*	public static void toLogin(Context context,boolean removeAllActivity) {
		Intent intent = new Intent(context, LoginActivity.class);
		if (removeAllActivity) {
			BaseApplication.removeAllActivity();
		}
		context.startActivity(intent);
	}

	public static void toLogin(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		BaseApplication.removeAllActivity();
		context.startActivity(intent);
	}*/
}

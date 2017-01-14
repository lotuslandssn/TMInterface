package com.tiamaes.theplatform.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author jirui_zhao
 *
 */
public class CollectRms {

	private String record = "ThePlatform";
	private Context context;
	public CollectRms(Context context) {
		this.context = context;
	}
	public String loadData(String key) {
		SharedPreferences data = context.getSharedPreferences(record,
				Context.MODE_PRIVATE);
		return data.getString(key, null);
	}

	public void saveData(String key, String str) {
		Editor data = context.getSharedPreferences(record,
				Context.MODE_PRIVATE).edit();
		data.remove(key);
		data.putString(key, str);
		data.commit();
	}
	
	public void removeData(String key) {
		Editor data = context.getSharedPreferences(record,
				Context.MODE_PRIVATE).edit();
		data.remove(key);
		data.commit();
	}
	
	//程序配置
	public boolean loadConfigData(String key) {
		SharedPreferences data = context.getSharedPreferences(record,
				Context.MODE_PRIVATE);
		return data.getBoolean(key, false);
	}

	public void saveConfigData(String key, Boolean str) {
		Editor data = context.getSharedPreferences(record,
				Context.MODE_PRIVATE).edit();
		data.remove(key);
		data.putBoolean(key, str);
		data.commit();
	}

	public void clearData() {
		Editor data = context
				.getSharedPreferences(record, Context.MODE_PRIVATE).edit();
		data.clear();
		data.commit();
	}

}

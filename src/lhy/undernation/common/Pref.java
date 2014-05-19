package lhy.undernation.common;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import arcanelux.library.common.BasePref;

public class Pref extends BasePref {
	/** 캐쉬성 Pref **/
	
	// Description
	public static void setDescriptionString(Context context, String strJsonObject){
		SharedPreferences pref = context.getSharedPreferences("cache", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("descriptionString", strJsonObject);
		editor.commit();
	}
	public static String getDescriptionString(Context context){
		SharedPreferences pref = context.getSharedPreferences("cache", Context.MODE_PRIVATE);
		String strJsonObject = pref.getString("descriptionString", "");
		return strJsonObject;
	}
	public static JSONObject getDescription(Context context){
		SharedPreferences pref = context.getSharedPreferences("cache", Context.MODE_PRIVATE);
		String strJsonObject = pref.getString("descriptionString", "");
		try {
			return new JSONObject(strJsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}

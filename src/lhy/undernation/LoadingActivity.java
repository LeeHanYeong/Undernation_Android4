package lhy.undernation;

import java.util.HashMap;

import lhy.undernation.common.C;
import lhy.undernation.common.Pref;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import arcanelux.library.ArcaneluxFunction;
import arcanelux.library.activity.AdlibrLoadingActivity;

public class LoadingActivity extends AdlibrLoadingActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar.hide();
		showAd = false;
		D = true;
		setContentView(R.layout.loading);

		initAds();
		new UndernationVersionCheckTask(mContext, "버전 체크 중...", false).execute();
	}

	class UndernationVersionCheckTask extends VersionCheckTask{
		private int version_available, version_current;
		private String resultString;

		public UndernationVersionCheckTask(Context context, String title, boolean showDialog) {
			super(context, title, showDialog);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			resultString = getRequest(C.URL_API_VERSION, new HashMap<String, String>());
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(resultString);
				version_available = jsonObject.getInt("available_version");
				version_current = ArcaneluxFunction.checkAppVersion(mContext);
				Log.d(TAG, "Available Version : " + version_available);
				Log.d(TAG, "Current Version : " + version_current);
				if(version_current < version_available){
					ArcaneluxFunction.showForceUpdateDialog(version_current, version_available, LoadingActivity.this);
				} else if(version_current >= version_available){
					new UndernationInitializeTask(mContext, "초기화 중...", false).execute();
				} else{
					new UndernationInitializeTask(mContext, "초기화 중...", false).execute();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(mContext, "버전 체크에 실패했습니다", Toast.LENGTH_SHORT).show();
				ArcaneluxFunction.showForceUpdateDialog(version_current, version_available, LoadingActivity.this);
			}
		}
	}

	class UndernationInitializeTask extends InitializeTask{
		private String resultstring;

		public UndernationInitializeTask(Context context, String title, boolean showDialog) {
			super(context, title, showDialog);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			resultstring = getRequest(C.URL_API_LIST, new HashMap<String, String>());
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(resultstring);
				String created = jsonObject.getString("created");
				Pref.setDescriptionString(mContext, resultstring);
				Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(mContext, "포스트 목록을 받아오는 데 실패했습니다", Toast.LENGTH_SHORT).show();
			}



		}

	}










}

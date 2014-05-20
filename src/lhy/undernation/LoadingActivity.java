package lhy.undernation;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import lhy.undernation.common.C;
import lhy.undernation.common.Pref;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import arcanelux.library.activity.AdlibrLoadingActivity;

public class LoadingActivity extends AdlibrLoadingActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		new UndernationVersionCheckTask(mContext, "버전 체크 중...", true).execute();
	}
	
	class UndernationVersionCheckTask extends VersionCheckTask{
		private String resultString;
		
		public UndernationVersionCheckTask(Context context, String title, boolean showDialog) {
			super(context, title, showDialog);
		}

		@Override
		protected Integer doInBackground(Void... params) {
//			resultString = getRequest(C.URL_API_VERSION, new HashMap<String, String>());
			return super.doInBackground(params);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			new UndernationInitializeTask(mContext, "초기화 중...", true).execute();
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

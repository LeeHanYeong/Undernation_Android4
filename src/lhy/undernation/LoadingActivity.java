package lhy.undernation;

import java.util.HashMap;

import lhy.undernation.common.C;
import android.content.Context;
import android.os.Bundle;
import arcanelux.library.activity.AdlibrLoadingActivity;

public class LoadingActivity extends AdlibrLoadingActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
	}
	
	class UndernationVersionCheckTask extends VersionCheckTask{
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
		}
	}
	
	
	
	
	
	
	
	
	
	
}

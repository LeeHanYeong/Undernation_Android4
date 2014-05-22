package lhy.undernation;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import arcanelux.library.activity.AdlibrActionBarActivity;

public class ComicDetailActivity extends AdlibrActionBarActivity {
	private WebView wv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comicdetail);

		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		String title = intent.getStringExtra("title");
		
		mActionBar.setTitle(title);

		wv = (WebView) findViewById(R.id.wvComicDetail);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClientClass());
//		wv.setVerticalScrollBarEnabled(false);
		wv.setHorizontalScrollBarEnabled(false);
		wv.loadUrl(url);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) { 
			wv.goBack(); 
			return true; 
		} 
		return super.onKeyDown(keyCode, event);
	}

	private class WebViewClientClass extends WebViewClient { 
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) { 
			wv.loadUrl(url);
			return true; 
		} 
	}

}

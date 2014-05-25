package lhy.undernation;

import java.util.ArrayList;

import lhy.undernation.common.C;
import lhy.undernation.common.Pref;
import lhy.undernation.data.DataCategory1;
import lhy.undernation.data.DataCategory2;
import lhy.undernation.data.Description;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import arcanelux.library.activity.AdlibrActionBarActivity;

public class MainActivity extends AdlibrActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private View viewDrawerHeader;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private Description mDescription;
	private ArrayList<DataCategory1> mDataCategory1List;
	private CategoryAdapter mCategoryAdapter;
	private Category2ClickListener mCategory2ClickListener;
	private View viewSelectedCategory2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// LoadingActivity에서 받아온 Description 세팅. InitializeTask에서 유효성 검사하므로 이쪽에서는 검사하지 않는다
		mDescription = new Description(Pref.getDescription(mContext));
		mDataCategory1List = mDescription.getDataCategory1List();
		
		// Listener 설정
		mCategory2ClickListener = new Category2ClickListener();
		
		// NavigationDrawer 설정
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		// NavigationDrawer의 ListView에 Header View추가
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewDrawerHeader = inflater.inflate(R.layout.drawer_header, null);
		mDrawerList.addHeaderView(viewDrawerHeader);
		
		// NavigationDrawer의 ListView에 Adapter 연결, 클릭리스너 할당
		mCategoryAdapter = new CategoryAdapter(mContext, R.layout.drawer_item, R.layout.drawer_item2, mDataCategory1List, mCategory2ClickListener);
		mDrawerList.setAdapter(mCategoryAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// ActionBar AppIcon으로 NavigationDrawer 작동하도록 설정
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}
	
	/**
	 * Category2 아이템 클릭 리스너
	 */
	class Category2ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// 이전에 선택되어있던 Category2 View를 선택해제 한 후, 클릭된 뷰를 선택으로 변환. viewSelectedCategory2에 이벤트 전달되어 온 View 할당
			if(viewSelectedCategory2 != null) setSelected(viewSelectedCategory2, false);
			setSelected(v, true);
			viewSelectedCategory2 = v;
			DataCategory2 curCategory2 = (DataCategory2) v.getTag();
			Log.d(TAG, curCategory2.getTitle());
			
			String urlLogo = "";
			if(curCategory2.getCategory2LogoImage().hasImageFile()){
				urlLogo = curCategory2.getCategory2LogoImage().getUrl();
			} else{
				urlLogo = curCategory2.getCategory1LogoImage().getUrl();
			}
			ComicListFragment fragment = new ComicListFragment(curCategory2.getTitle(), curCategory2.getDescription(), C.URL_BASE + urlLogo, curCategory2.getPostList());
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}
	
	/**
	 * Category2아이템 선택시 선택/비선택 표시해줄 함수
	 */
	private void setSelected(View view, boolean isSelected){
		if(isSelected){
			view.setBackgroundColor(Color.BLUE);
		} else{
			view.setBackgroundColor(Color.TRANSPARENT);
		}
	}
	
	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// position=0 이면 업데이트순 보기(ListView Header)이므로, position에서 -1하며 -1일경우 따로 동작
			selectItem(position);
		}
	}

	/** 
	 * NavigationDrawer의 아이템 클릭시 발생하는 이벤트
	 * 해당 Position의 Fragment를 만들어주며, 클릭 시 Drawer를 닫아줌
	 */
	private void selectItem(int position) {
		ComicListFragment fragment = null;
		if(position == 0){
			DataCategory1 curCategory1 = mDescription.getDataCategory1List().get(0);
			fragment = new ComicListFragment("업데이트 순으로 보기", curCategory1.getDescription(), C.URL_BASE + curCategory1.getCategory1LogoImage().getUrl(), mDescription.getPostList());
		} else{
			position = position - 1;
			DataCategory1 curCategory1 = mDataCategory1List.get(position);
			Log.d(TAG, curCategory1.getTitle());
			
			fragment = new ComicListFragment(curCategory1.getTitle(), curCategory1.getDescription(), C.URL_BASE + curCategory1.getCategory1LogoImage().getUrl(), curCategory1.getPostList());				
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer			
		mDrawerList.setItemChecked(position, true);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/** Actionbar Home/Up 버튼으로 NavigationDrawer 작동시키려면 이 함수 필요 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		// Home/Up 버튼으로 NavigationDrawer 작동시키려면 return true;
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		//        switch(item.getItemId()) {
		//        case R.id.action_websearch:
		//            // create intent to perform web search for this planet
		//            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
		//            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
		//            // catch event that there's no activity to handle intent
		//            if (intent.resolveActivity(getPackageManager()) != null) {
		//                startActivity(intent);
		//            } else {
		//                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
		//            }
		//            return true;
		//        default:
		//            return super.onOptionsItemSelected(item);
		//        }
		return super.onOptionsItemSelected(item);
	}
}

package lhy.undernation;

import java.util.ArrayList;

import lhy.undernation.MainActivity.Category2ClickListener;
import lhy.undernation.data.DataCategory1;
import lhy.undernation.data.DataCategory2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import arcanelux.library.baseclass.BaseArrayAdapter;

public class CategoryAdapter extends BaseArrayAdapter<DataCategory1>{
	private ArrayList<DataCategory1> mDataCategory1List;
	private int layoutResource2;
	private Category2ClickListener mCategory2ClickListener;

	public CategoryAdapter(Context context, int resource, int resource2, ArrayList<DataCategory1> dataCategory1List, Category2ClickListener category2ClickListener) {
		super(context, resource, dataCategory1List);
		mDataCategory1List = dataCategory1List;
		layoutResource2 = resource2;
		mCategory2ClickListener = category2ClickListener;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataCategory1 curCategory1 = mDataCategory1List.get(position);
		View curView = null;
		if(curView == null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			curView = inflateWithCustomFont(inflater, layoutResource);
		}

		TextView tvItemTitle = (TextView) curView.findViewById(R.id.tvDrawerItem);
		LinearLayout llItemList = (LinearLayout) curView.findViewById(R.id.llDrawerItem);

		String categoryTitle = curCategory1.getTitle();
		tvItemTitle.setText(categoryTitle);
		if(curCategory1.hasCategory2()){
			curView.setClickable(false);
			curView.setFocusable(false);
			llItemList.setClickable(false);
			llItemList.setFocusable(false);
			
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ArrayList<DataCategory2> category2List = curCategory1.getCategory2List();
			for(int i=0; i<category2List.size(); i++){
				DataCategory2 curCategory2 = category2List.get(i);
				View category2View = inflateWithCustomFont(inflater, layoutResource2);
				TextView tvTitle = (TextView) category2View.findViewById(R.id.tvDrawerItem2);
				String category2Title = curCategory2.getTitle();
				tvTitle.setText(category2Title);

				category2View.setTag(curCategory2);
				category2View.setClickable(true);
				category2View.setOnClickListener(mCategory2ClickListener);
				llItemList.addView(category2View);
			}
		}
		return curView;
	}		
}
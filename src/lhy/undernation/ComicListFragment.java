package lhy.undernation;

import java.util.ArrayList;

import lhy.undernation.data.DataCategory1;
import lhy.undernation.data.DataPost;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

public class ComicListFragment extends Fragment implements OnItemClickListener{
	public final String ARG_COMICLIST_NUMBER = "ComicList Number";
	private Context mContext;
	private AQuery aq;

	private String fragmentTitle;
	private View viewListHeader, viewListFooter;
	private ListView lvList;
	private ListAdapter mListAdapter;
	private ArrayList<DataPost> mDataPostList;

	private TextView tvHeaderTitle, tvHeaderDescription;
	private ImageView ivHeaderCover;
	private ProgressBar pbHeaderCover;



	public ComicListFragment() {
		super();
	}

	public ComicListFragment(String title) {
		super();
		this.fragmentTitle = title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		aq = new AQuery(mContext);
		View v = inflater.inflate(R.layout.comiclist, container, false);

		// ListView & Header & Footer
		viewListHeader = inflater.inflate(R.layout.comiclist_listview_header, null);
		viewListFooter = inflater.inflate(R.layout.comiclist_listview_footer, null);
		viewListHeader.setFocusable(false);
		viewListFooter.setFocusable(false);
		lvList = (ListView) v.findViewById(R.id.lvComicList);
		lvList.addHeaderView(viewListHeader);
		lvList.addFooterView(viewListFooter);
		lvList.setOnItemClickListener(this);
		tvHeaderTitle = (TextView) viewListHeader.findViewById(R.id.tvComicListHeaderTitle);
		tvHeaderDescription = (TextView) viewListHeader.findViewById(R.id.tvComicListHeaderDescription);
		tvHeaderTitle.setText(fragmentTitle);
		tvHeaderDescription.setText("Description");
		ivHeaderCover = (ImageView) viewListHeader.findViewById(R.id.ivComicListHeaderCover);
		pbHeaderCover = (ProgressBar) viewListHeader.findViewById(R.id.pbComicListHeaderCover);
		ImageOptions options = new ImageOptions();
		options.round = (int)(15 * 2);
		aq.id(ivHeaderCover).image("http://thumb.comic.naver.net/webtoon/186811/thumbnail/title_thumbnail_20130109160631_t125x101.jpg", options);

		// ListAdapter
		mListAdapter = new ListAdapter(mContext, R.layout.comiclist_listview_item, mDataPostList);
		lvList.setAdapter(mListAdapter);



		return v;
	}

	class ListAdapter extends ArrayAdapter<DataPost>{
		private ArrayList<DataPost> dataPostList;
		public ListAdapter(Context context, int textViewResourceId, ArrayList<DataPost> dataPostList) {
			super(context, textViewResourceId, dataPostList);
			this.dataPostList = dataPostList;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View curView = convertView;
			if(curView==null){
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				curView = inflater.inflate(R.layout.comiclist_listview_item, null);
			}
			
			DataPost curPost = dataPostList.get(position);
			String title = curPost.getTitle();
			String created = curPost.getCreatedString();
			String category = curPost.getCategory2Title();

			//			ImageView ivThumbnail = (ImageView) curView.findViewById(R.id.ivListItemThumbnail);
			TextView tvTitle = (TextView) curView.findViewById(R.id.tvComicListItemTitle);
			TextView tvDate = (TextView) curView.findViewById(R.id.tvComicListItemDate);
			TextView tvCategory = (TextView) curView.findViewById(R.id.tvComicListItemCategory);

			tvTitle.setText(title);
			tvDate.setText(created);
			tvCategory.setText(category);
			//			aq.id(ivThumbnail).image

			return curView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}

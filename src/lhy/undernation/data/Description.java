package lhy.undernation.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import lhy.undernation.common.C;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arcanelux.library.data.BaseData;

public class Description extends BaseData {

	public Description() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Description(JSONObject jsonObject) {
		super(jsonObject);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<DataCategory1> getDataCategory1List() {
		ArrayList<DataCategory1> dataCategory1List = new ArrayList<DataCategory1>();
		try{
			JSONArray jsonArrayCategory1List = mJsonObject.getJSONArray("categories");
			for(int i=0; i<jsonArrayCategory1List.length(); i++){
				JSONObject curJsonObjectCategory1 = jsonArrayCategory1List.getJSONObject(i);
				DataCategory1 curDataCategory1 = new DataCategory1(curJsonObjectCategory1);
				dataCategory1List.add(curDataCategory1);
			}
		} catch(JSONException e){
			e.printStackTrace();
		}
		return dataCategory1List;
	}
	
	public ArrayList<DataPost> getPostList(){
		ArrayList<DataPost> dataPostList = new ArrayList<DataPost>();
		
		ArrayList<DataCategory1> dataCategory1List = getDataCategory1List();
		for(int i=0; i<dataCategory1List.size(); i++){
			DataCategory1 curDataCategory1 = dataCategory1List.get(i);
			ArrayList<DataCategory2> curDataCategory2List = curDataCategory1.getCategory2List();
			for(int j=0; j<curDataCategory2List.size(); j++){
				DataCategory2 curDataCategory2 = curDataCategory2List.get(j);
				ArrayList<DataPost> curDataPostList = curDataCategory2.getPostList();
				
				dataPostList.addAll(curDataPostList);
			}
		}
		Collections.sort(dataPostList, new C.IdDescCompare());
		return dataPostList;
	}
	
	public String getCreatedString(){
		return getString("created");
	}
	
	public Calendar getCalendar(){
		return getCalendarFromString(getCreatedString(), C.FORMAT_DATE);
	}
}

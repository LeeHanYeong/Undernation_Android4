package lhy.undernation.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import arcanelux.library.data.BaseData;

public class DataCategory1 extends BaseData {

	public DataCategory1() {
		super();
	}

	public DataCategory1(JSONObject jsonObject) {
		super(jsonObject);
	}

	public String getTitle(){
		return getString("category1_title");
	}

	public boolean hasCategory2(){
		return getBoolean("has_category2");
	}

	public ArrayList<DataCategory2> getCategory2List(){
		ArrayList<DataCategory2> dataCategory2List = new ArrayList<DataCategory2>();
		JSONArray jsonArrayCategory2List = getJsonArray("category2_list");
		try {
			for(int i=0; i<jsonArrayCategory2List.length(); i++){
				DataCategory2 curDataCategory2 = new DataCategory2(jsonArrayCategory2List.getJSONObject(i));
				dataCategory2List.add(curDataCategory2);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataCategory2List;
	}

}

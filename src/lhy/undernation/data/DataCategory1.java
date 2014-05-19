package lhy.undernation.data;

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
	
}

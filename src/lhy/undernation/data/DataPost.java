package lhy.undernation.data;

import java.util.Calendar;

import lhy.undernation.common.C;

import org.json.JSONObject;

import arcanelux.library.data.BaseData;

public class DataPost extends BaseData {

	public DataPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataPost(JSONObject jsonObject) {
		super(jsonObject);
		// TODO Auto-generated constructor stub
	}

	public String getCategory1Title(){
		return getString("category1");
	}
	
	public String getCategory2Title(){
		return getString("category2");
	}
	
	public String getCreatedString(){
		return getString("created");
	}
	
	public Calendar getCalendar(){
		return getCalendarFromString(getCreatedString(), C.FORMAT_DATE);
	}
	
	public String getTitle(){
		return getString("title");
	}
	
	public int getId(){
		return getInt("id");
	}
	
	public String getLogNo(){
		return getString("logno");
	}
}

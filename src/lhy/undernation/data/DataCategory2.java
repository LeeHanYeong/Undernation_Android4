package lhy.undernation.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import arcanelux.library.data.BaseData;

public class DataCategory2 extends BaseData {

	public DataCategory2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataCategory2(JSONObject jsonObject) {
		super(jsonObject);
		// TODO Auto-generated constructor stub
	}

	public String getTitle(){
		return getString("category2_title");
	}
	
	private String getCategory1Description(){
		return getString("category1_description");
	}
	
	public String getDescription(){
		String category2Description = getString("category2_description");
		if(category2Description.equals("") || category2Description == null){
			return getCategory1Description();
		} else{
			return category2Description;
		}
	}
	
	public DataImage getCategory1LogoImage(){
		return new DataImage(getJsonObject("category1_logo"));
	}
	
	public DataImage getCategory2LogoImage(){
		return new DataImage(getJsonObject("category2_logo"));
	}

	public ArrayList<DataPost> getPostList(){
		ArrayList<DataPost> dataPostList = new ArrayList<DataPost>();

		JSONArray jsonArrayPostList = getJsonArray("category2_posts");
		try{
			for(int i=0; i<jsonArrayPostList.length(); i++){
				JSONObject jsonObjectPost = jsonArrayPostList.getJSONObject(i);
				dataPostList.add(new DataPost(jsonObjectPost));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return dataPostList;
	}
}

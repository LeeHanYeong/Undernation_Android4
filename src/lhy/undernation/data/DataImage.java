package lhy.undernation.data;

import org.json.JSONObject;

import arcanelux.library.data.BaseData;

public class DataImage extends BaseData{

	public DataImage(JSONObject jsonObject) {
		super(jsonObject);
	}
	
	public String getUrl(){
		return getString("url");
	}
	public boolean hasImageFile(){
		return getBoolean("has_image");
	}

	public int getWidth(){
		return getInt("width");
	}
	
	public int getHeight(){
		return getInt("height");
	}
}

package lhy.undernation.common;

import java.util.Comparator;

import lhy.undernation.data.DataPost;

public class C {
	public static String FORMAT_DATE = "yyyy-MM-dd HH:mm";
	public static String FORMAT_DATE_DESCRIPTION = "yyyy/MM/dd HH:mm:ss";
	public static String FILENAME_CUSTOMFONT = "NanumBarunGothic.mp3";
	
	public static String URL_BASE = "http://leehanyeong.cafe24.com/";
	private static String URL_UNDERNATION2_BASE = "undernation2/";
	private static String URL_API = URL_BASE + URL_UNDERNATION2_BASE +  "api/";
	
	public static String URL_API_VERSION = URL_API + "version/"; 
	public static String URL_API_LIST = URL_API + "list/";
	public static String URL_COMIC_VIEW = URL_BASE + URL_UNDERNATION2_BASE + "post/";
	
	
	public static class IdDescCompare implements Comparator<DataPost> {
		@Override
		public int compare(DataPost lhs, DataPost rhs) {
			if(lhs.getId() > rhs.getId()){
				return -1;
			} else if(lhs.getId() < rhs.getId()){
				return 1;
			} else{
				return 0;
			}
		}
		
	}
}

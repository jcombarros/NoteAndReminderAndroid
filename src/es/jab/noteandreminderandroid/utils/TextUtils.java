package es.jab.noteandreminderandroid.utils;

public class TextUtils {
	
	private static final String EMPTY_STRING = "";
	
	public static boolean isNullOrEmpty(String myString){
		return myString == null || myString.equals(TextUtils.EMPTY_STRING);
	}

}

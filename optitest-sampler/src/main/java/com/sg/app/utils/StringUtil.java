package com.sg.app.utils;
/**
 * @author Karoonakar
 *
 */
public class StringUtil {
	
	public static boolean isValidString(String str){
		
		if((str !=null && !str.isEmpty() && !"undefined".equalsIgnoreCase(str)) ){
			return true;
		}else{
			return false;
		}
	}
}

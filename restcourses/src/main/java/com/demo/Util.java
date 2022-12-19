package com.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Util {
	public static int getIdFromPath(HttpServletRequest req) {
		String path =req.getPathInfo();
		int id = 0;
		if (path != null){
			Pattern p = Pattern.compile("^/(\\d{1,7})$");
	    	Matcher m = p.matcher(path);
	    	 
	    	if(m.matches()) {
	    		String strid = m.group(1);
	    		id = Integer.parseInt(strid);
	    	 }
		}
		return id ;
	}
}

package com.lzx.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lzx.blog.service.*;

public class testSome {

	public static void main(String[] args) {
		String regex = "^data:image/(jpg|gif|png|jpeg|bmp);base64,";
		String img = "data:image/jpeg;base64,/456321345dfgdfgdfasdf";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(img);
		matcher.find();
		    	System.out.println(img.replace(matcher.group(0),""));
		    	System.out.println(matcher.group(1));
	
	}

}

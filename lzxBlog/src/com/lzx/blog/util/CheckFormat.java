package com.lzx.blog.util;

public class CheckFormat {
	private static String regex = "^[a-zA-Z0-9]+$";
		
	public static String check(String usernum,String password){
			
		if (usernum == null || usernum.equals("") || password == null || password.equals("")) {
			return "用户名或密码为空,请重新填写";
		}
        
        boolean flag_num= usernum.matches(regex);
        boolean flag_pass = password.matches(regex);
		if(!flag_num || !flag_pass){
			return "用户名或密码只能由英文或数字来组合";
		}
		
		return "ok";	
	}
	
	public static String check(String usernum,String password,String rpassword){
		
		if (usernum == null && usernum.equals("") && password == null && password.equals("")) {
			return "用户名或密码为空,请重新填写";
		}
        boolean flag_num= usernum.matches(regex);
        boolean flag_pass = password.matches(regex);
        
		if(!flag_num || !flag_pass){
			return "用户名或密码只能由英文或数字来组合";
		}
		if(!rpassword.equals(password)){
			return "密码不一致!";
		}		
		return "ok";	
	}
}

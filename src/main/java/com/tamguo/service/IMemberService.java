package com.tamguo.service;

import com.tamguo.util.Result;

public interface IMemberService {

	public Result login(String username , String password , String captcha);
	
	public Result checkUsername(String username);

	public Result checkMobile(String mobile);
	
	public Result register(String username , String mobile , String password , String verifyCode);
}
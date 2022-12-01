package com.jiuzhou.plat.cache;

/**
 * 用于缓存用户登录验证码
 * @author xingmh
 *
 */
public class LoginValidateCode extends TimedClearBase {

	private String token;
	private String code;
	
	public LoginValidateCode(long cache_add_time, long cache_save_time) {
		super(cache_add_time, cache_save_time);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}

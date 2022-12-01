package com.jiuzhou.plat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
* @author xingmh
* @version 2018年9月3日 下午10:29:39
* 用户功能访问权限不足时抛出，用于用户功能权限认证
*/
@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class AuthExcetion extends RuntimeException {

	private static final long serialVersionUID = -7445196668851611392L;

}

package com.jiuzhou.plat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 用户需登录时抛出，用于验证用户登录信息
 * @author xingmh
 *
 */
@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class LoginException extends RuntimeException {

	private static final long serialVersionUID = -3101733865570670365L;

}

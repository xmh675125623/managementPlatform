package com.jiuzhou.plat.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;

/**
* @author xingmh
* @version 2018年9月19日 下午6:00:04
* 类说明
*/
public class WebSocketInterceptor implements HandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {

	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse arg1, WebSocketHandler arg2,
			Map<String, Object> map) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = serverHttpRequest.getServletRequest().getSession();
            Map parameterMap = serverHttpRequest.getServletRequest().getParameterMap();
            String[] aidParams = (String[]) parameterMap.get("aid");
            if (aidParams == null || aidParams.length < 1) {
            	return false;
            }
            String aid = aidParams[0];
            if (aid == null || aid.trim().length() < 1) {
            	return false;
            }
            AdminUserLoginInfo loginInfo = 
            		new ServiceBase().getCache(ServiceBase.CACHE_LOGIN_INFO + aid, 
            				AdminUserLoginInfo.class);
            if (loginInfo == null) {
            	return false;
            }
            if (session != null) {
                map.put("aid", Integer.parseInt(aid));
            }

        }
        return true;
	}

}

package com.jiuzhou.plat.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
* @author xingmh
* @version 2018年9月19日 下午3:22:25
* 类说明
*/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemWebsocketHandler(), "/myHandler.do").addInterceptors(new WebSocketInterceptor());

	}
	
	@Bean
    public WebSocketHandler systemWebsocketHandler() {
        return new SystemWebsocketHandler();
    }

}

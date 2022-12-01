package com.jiuzhou.plat.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.jiuzhou.firewall.bean.FirewallDeviceStatus;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.service.impl.ServiceBase;
import com.jiuzhou.plat.thread.FirewallDeviceStatusThread;
import com.jiuzhou.plat.util.WebDataAESUtils;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月19日 下午5:52:51
* 类说明
*/
public class SystemWebsocketHandler extends TextWebSocketHandler {
	
	//在线用户列表
    public static final Map<WebSocketSession, Integer> tempUserSessions;
    //用户标识
    private static final String CLIENT_ID = "aid";

    static {
    	tempUserSessions = new HashMap<>();
    }
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Integer userId = getClientId(session);
        if (userId != null) {
        	tempUserSessions.put(session, userId);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
    	
    	try {
			JSONObject jsonObject = JSONObject.fromObject(WebDataAESUtils.webDataDecrypt(message.getPayload()));
			String method = jsonObject.getString("method");
			if ("socket_regist".equals(method)) {
				registWebsocketSession(jsonObject, session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void registWebsocketSession(JSONObject paramJson, WebSocketSession session) {
    	if (!paramJson.has("sessionid")) {
    		return;
    	}
    	String sessionid = paramJson.getString("sessionid");
    	Integer aid = tempUserSessions.get(session);
    	if (aid == null || aid == 0) {
    		return;
    	}
    	AdminUserLoginInfo loginInfo = 
    			ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + aid, AdminUserLoginInfo.class);
    	if (loginInfo == null || !loginInfo.getSessionid().equals(sessionid)) {
    		return;
    	}
    	loginInfo.setSession(session);
    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
//    public boolean sendMessageToUser(Integer clientId, TextMessage message) {
//        if (tempUserSessions.get(clientId) == null) return false;
//        WebSocketSession session = tempUserSessions.get(clientId);
//        System.out.println("sendMessage:" + session);
//        if (!session.isOpen()) return false;
//        try {
//            session.sendMessage(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers() {
        boolean allSendSuccess = true;
        
        Map<String, Object> cacheMap = ServiceBase.getCacheMap();
        WebSocketSession session = null;
        for (Map.Entry<String, Object> entry : cacheMap.entrySet()) { 
        	Object object = entry.getValue();
        	if (!(object instanceof AdminUserLoginInfo)) {
        		continue;
        	}
        	AdminUserLoginInfo loginInfo = AdminUserLoginInfo.class.cast(object);
        	session = loginInfo.getSession();
        	if (session == null) {
        		continue;
        	}
        	//获取设备名并组织数据发送
    		String deviceName = loginInfo.getIndexCurrentDeviceName();
    		String organizeData = this.organizeData(deviceName);
    		if (StringUtils.isBlank(organizeData)) {
    			continue;
    		}
    		TextMessage message = new TextMessage(this.organizeData(deviceName));
        	try {
                 if (session.isOpen()) {
                	 synchronized (session) {
                		 session.sendMessage(message);
					}
                 }
             } catch (IOException e) {
                 allSendSuccess = false;
             }
    	}
        
        return  allSendSuccess;
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
//        System.out.println("连接出错");
        removeSession(session);
        
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        System.out.println("连接已关闭：" + status);
        removeSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private Integer getClientId(WebSocketSession session) {
        try {
            Integer clientId = (Integer) session.getAttributes().get(CLIENT_ID);
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }
    
    private void removeSession(WebSocketSession session) {
    	Integer aid = tempUserSessions.get(session);
        if (aid == null || aid == 0) {
        	return;
        }
        AdminUserLoginInfo loginInfo = 
        		ServiceBase.getCacheStatic(ServiceBase.CACHE_LOGIN_INFO + aid, 
        									AdminUserLoginInfo.class);
        if (loginInfo != null) {
        	loginInfo.setSession(null);
            tempUserSessions.remove(session);
        }
        
    }
    
    /**
     * 组织广播数据
     * @return
     */
    private String organizeData(String deviceName) {
    	FirewallDeviceStatus deviceStatus = FirewallDeviceStatusThread.deviceStatusMap.get(deviceName);
    	if (deviceStatus == null) {
    		JSONObject resultObj = new JSONObject();
    		resultObj.put("core0", "0");
    		resultObj.put("core1", "0");
    		resultObj.put("core2", "0");
    		resultObj.put("core3", "0");
    		resultObj.put("memoryUsage", "0");
    		resultObj.put("diskUsage", "0");
    		resultObj.put("diskWarning", "0");
    		return resultObj.toString();
    	}
    	//处理cpu占用率用以发送数据
		List<JSONObject> cpuData = new ArrayList<>();
		
		@SuppressWarnings("unchecked")
		LinkedList<String> cpuUsages = (LinkedList<String>) deviceStatus.getCpuUsages().clone();
		if (cpuUsages.size() < 60) {
			int nullDataCount = 60 - cpuUsages.size();
			for (int i = 1; i <= nullDataCount; i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("x", i);
				jsonObject.put("y", 0);
				cpuData.add(jsonObject);
			}
			for (int i = nullDataCount + 1; i <= 60; i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("x", i);
				jsonObject.put("y", Integer.parseInt(cpuUsages.get(i - nullDataCount - 1)));
				cpuData.add(jsonObject);
			}
		} else {
			for (int i = 0; i < cpuUsages.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("x", i+1);
				jsonObject.put("y", Integer.parseInt(cpuUsages.get(i)));
				cpuData.add(jsonObject);
			}
		}
		
		//处理流量数据
		List<JSONObject> flowDataInput = new ArrayList<>();
		List<JSONObject> flowDataOutput = new ArrayList<>();
		List<LinkedList<String>> inputTotal = deviceStatus.getInputTotal();
		List<LinkedList<String>> outputTotal = deviceStatus.getOutputTotal();
		
		if (inputTotal.size() > 0) {
			
			LinkedList<String> inputList = inputTotal.get(0);
			
			if (inputList != null && inputList.size() > 0) {
				for (int i = 0; i < inputList.size(); i++) {
					for (int j = 0; j < inputTotal.size(); j++) {
						LinkedList<String> inputList2 = inputTotal.get(j);
						try {
							JSONObject object = new JSONObject();
							object.put("x", i);
							object.put("t", "eth" + j);
							object.put("y", Integer.parseInt(inputList2.get(i))/1024);
							flowDataInput.add(object);
						} catch (Exception e) {}
					}
				}
				
			}
			
			LinkedList<String> outputList = outputTotal.get(0);
			
			if (outputList != null && outputList.size() > 0) {
				for (int i = 0; i < outputList.size(); i++) {
					
					for (int j = 0; j < outputTotal.size(); j++) {
						LinkedList<String> outputList2 = outputTotal.get(j);
						try {
							JSONObject object = new JSONObject();
							object.put("x", i);
							object.put("t", "eth" + j);
							object.put("y", Integer.parseInt(outputList2.get(i))/1024);
							flowDataOutput.add(object);
						} catch (Exception e) {}
					}
					
				}
				
			}
			
		}
		
		
//		for (int i = 0; i < outputTotal.size(); i++) {
//			JSONObject object = new JSONObject();
//			object.put("x", i);
//			
//			LinkedList<String> putputList = outputTotal.get(i);
//			
//			for (int j = 0; j < putputList.size(); j++) {
//				object.put("y" + i, Integer.parseInt(putputList.get(j)));
//			}
//			flowDataOutput.add(object);
//		}
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("memoryTotal", deviceStatus.getMemoryTotal());
		resultObj.put("diskTotal", deviceStatus.getDiskTotal());
		resultObj.put("core0", deviceStatus.getCore0());
		resultObj.put("core1", deviceStatus.getCore1());
		resultObj.put("core2", deviceStatus.getCore2());
		resultObj.put("core3", deviceStatus.getCore3());
		resultObj.put("cpuUsage", cpuData);
		resultObj.put("memoryUsage", deviceStatus.getMemoryUsage());
		resultObj.put("flowDataInput", flowDataInput);
		resultObj.put("flowDataOutput", flowDataOutput);
		resultObj.put("diskUsage", deviceStatus.getDiskUsage());
		resultObj.put("diskWarning", deviceStatus.getDiskWarning());
		resultObj.put("notices", deviceStatus.getSystemWarnings());
		return resultObj.toString();
    }
    
}

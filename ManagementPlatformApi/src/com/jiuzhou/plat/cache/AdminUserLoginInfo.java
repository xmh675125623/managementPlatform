package com.jiuzhou.plat.cache;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import com.jiuzhou.plat.bean.AdminUser;

/**
 * 用户登录信息
 * @author xingmh
 *
 */
public class AdminUserLoginInfo {
	
	private AdminUser userInfo;					//用户信息
	private List<String> functions;				//用户功能列表
	private long activeTime;					//最后一次活动时间
	private String sessionid;					//会话标识
	private WebSocketSession session;			//websocket的session
	private String indexCurrentDeviceName;      //首页当前展示的设备
	private String requestCode;                 //请求码，用于判断是否为会话重放攻击
	private long admitOperationTime;            //验证操作密码时间
	
	public AdminUserLoginInfo() {
	}

	public AdminUser getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(AdminUser userInfo) {
		this.userInfo = userInfo;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public List<String> getFunctions() {
		return functions;
	}

	public void setFunctions(List<String> functions) {
		this.functions = functions;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	public String getIndexCurrentDeviceName() {
		return indexCurrentDeviceName;
	}

	public void setIndexCurrentDeviceName(String indexCurrentDeviceName) {
		this.indexCurrentDeviceName = indexCurrentDeviceName;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}
	
	// 通过请求码验证是否为会话重放攻击
	public boolean authRequestCode(String requestCode) {
		
		if (requestCode == null || requestCode.length() < 17) {
			return false;
		}
		
		if (this.getRequestCode() == null) {
			this.setRequestCode(requestCode);
			return true;
		}
		
		//判断随机数
		String beforeCode = this.getRequestCode().substring(0, 4);
		String currentCode = requestCode.substring(0, 4);
		if (beforeCode.equals(currentCode)) {
			return false;
		}
		
		//判断时间
		String beforeTime = this.getRequestCode().substring(4);
		String currentTime = requestCode.substring(4);
		try {
			if (Long.parseLong(currentTime) < Long.parseLong(beforeTime)) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		this.setRequestCode(requestCode);
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println("12345678".substring(4));
	}

	public long getAdmitOperationTime() {
		return admitOperationTime;
	}

	public void setAdmitOperationTime(long admitOperationTime) {
		this.admitOperationTime = admitOperationTime;
	}
	
}

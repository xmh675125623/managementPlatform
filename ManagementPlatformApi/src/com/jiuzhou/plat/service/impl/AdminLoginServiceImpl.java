package com.jiuzhou.plat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jiuzhou.plat.bean.AdminUser;
import com.jiuzhou.plat.bean.Function;
import com.jiuzhou.plat.bean.PlatIpWhitelist;
import com.jiuzhou.plat.bean.SystemSetting;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.cache.LoginValidateCode;
import com.jiuzhou.plat.mapper.AdminUserMapper;
import com.jiuzhou.plat.mapper.FunctionMapper;
import com.jiuzhou.plat.mapper.PlatIpWhitelistMapper;
import com.jiuzhou.plat.mapper.RoleFunctionMapper;
import com.jiuzhou.plat.service.AdminLoginService;
import com.jiuzhou.plat.service.SystemSettingService;
import com.jiuzhou.plat.util.MD5;

import net.sf.json.JSONObject;

@Service("AdminLoginService")
public class AdminLoginServiceImpl extends ServiceBase implements AdminLoginService {
	
	@Autowired
	SystemSettingService systemSettingService;
	
	@Autowired
	AdminUserMapper adminUserMapper;
	@Autowired
	RoleFunctionMapper roleFunctionMapper;
	@Autowired
	FunctionMapper functionMapper;
	@Autowired
	PlatIpWhitelistMapper platIpWhitelistMapper; 
	
	@Value("${host_kibana}")
	String hostKibana;
	@Value("${host_grafana}")
	String hostGrafana;
	@Value("${host_index}")
	String hostIndex;
	
	/**
	 * 管理员登录
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String Login(JSONObject paramJson) throws Exception {
		
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		requestAttributes.setAttribute("loginResult", "验证失败", RequestAttributes.SCOPE_REQUEST);
		
		JSONObject resultJson = new JSONObject();
		
		//判断ip白名单
		HttpServletRequest req = requestAttributes.getRequest();
		String IP_address = req.getRemoteAddr();
		int ipCount = platIpWhitelistMapper.getAllCount();
		if (ipCount > 0) {
			PlatIpWhitelist ipWhitelist = platIpWhitelistMapper.getByIpAddress(IP_address);
			if (ipWhitelist == null) {
				if (paramJson.has("account") && "jiuzhou_root".equals(paramJson.get("account"))) {
					//root管理员不受限
				} else {
					resultJson.put("status", "error");
					resultJson.put("errorMsg", "当前IP禁止登录");
					return resultJson.toString();
				}
			}
		}
		
		//判断验证码
		if (!paramJson.has("login_token") || !paramJson.has("validate_code")) {
			resultJson.put("status", "error");
			resultJson.put("errorMsg", "验证码错误");
			return resultJson.toString();
		}
		String login_token = paramJson.getString("login_token");
		String validate_code = paramJson.getString("validate_code");
		LoginValidateCode validateCode = getCache(CACHE_LOGIN_VALIDATE_CODE + login_token, 
				LoginValidateCode.class);
		deleteCache(CACHE_LOGIN_VALIDATE_CODE + login_token);
		
		if (validateCode == null) {
			resultJson.put("status", "error");
			resultJson.put("errorMsg", "验证码已失效");
			return resultJson.toString();
		}
		if (!validate_code.toUpperCase().equals(validateCode.getCode().toUpperCase())) {
			resultJson.put("status", "error");
			resultJson.put("errorMsg", "验证码错误");
			return resultJson.toString();
		}
		
		
		//判断用户名密码
		if (!paramJson.has("account")) {
			resultJson.put("status", "error");
			resultJson.put("errorMsg", "请输入帐号");
			return resultJson.toString();
		}
		if (!paramJson.has("password")) {
			resultJson.put("status", "error");
			resultJson.put("errorMsg", "请输入密码");
			return resultJson.toString();
		}
		String account = paramJson.getString("account");
		String password = paramJson.getString("password");
		AdminUser adminUser = adminUserMapper.getAdminUserByAccount(account);
		if (adminUser == null) {
			resultJson.put("status", "error");
			resultJson.put("errorMsg", "帐号或密码错误");
			return resultJson.toString();
		}
		SystemSetting login_num_setting = systemSettingService.getByName(SystemSetting.LOGIN_ALLOW_SUM);
		if (adminUser.getNum() >= login_num_setting.getIntValue() && adminUser.getLogin_fail_time() != null) {
			//如果超过登录次数
			int waitTime = systemSettingService.getByName(SystemSetting.LOGIN_WAIT_TIME).getIntValue();
			long waitedTime = System.currentTimeMillis() - adminUser.getLogin_fail_time().getTime();
			if (waitedTime < waitTime * 60 * 1000) {
				requestAttributes.setAttribute("loginErrMsg", "帐号因密码输入错误超过"+login_num_setting.getIntValue()+"次已被锁定，禁止登录", RequestAttributes.SCOPE_REQUEST);
				resultJson.put("status", "error");
				long remainTime = waitTime - (waitedTime/1000/60); 
				resultJson.put("errorMsg", "帐号因密码输入错误超过"+login_num_setting.getIntValue()+"次，已被锁定，请"+(remainTime<1?1:remainTime)+"分钟后登录");
				return resultJson.toString();
			}
		}
		String md5Password = 
				MD5.toMD5(password+adminUser.getUser_account()+AdminUserServiceImpl.PASSWORD_SALT).toUpperCase();
		if (!md5Password.equals(adminUser.getPassword())) {
			//如果密码错误则增加错误登录次数
			if (adminUser.getId() != -1) {
				adminUser.setNum(adminUser.getNum() + 1);
				adminUser.setLogin_fail_time(new Date());
				adminUserMapper.updateAdminUser(adminUser);
			}
			resultJson.put("status", "error");
			resultJson.put("errorMsg", "帐号或密码错误");
			return resultJson.toString();
		}
		
		List<Function> firstMenus = null;	//一级菜单
		List<Function> secondMenus = null;	//二级菜单
		List<Function> thirdMenus = null;	//二级菜单
		List<Function> functions = null;	//功能列表
		if (adminUser.getRole_id() == 1) {
			//如果为超级管理员则拥有所有权限
			firstMenus = functionMapper.getFirstMenus();
			secondMenus = functionMapper.getSecondMenus();
			thirdMenus = functionMapper.getThirdMenus();
			functions = functionMapper.getFunctions();
		} else {
			firstMenus = roleFunctionMapper.getFirstMenusByRoleId(adminUser.getRole_id());
			secondMenus = roleFunctionMapper.getSecondMenusByRoleId(adminUser.getRole_id());
			thirdMenus = roleFunctionMapper.getThirdMenusByRoleId(adminUser.getRole_id());
			functions = roleFunctionMapper.getFunctionsByRoleId(adminUser.getRole_id());
		}
		//组织菜单
		Map<String, Function> firstMenuMap = new HashMap<>();
		if (firstMenus == null) {
			firstMenus = new ArrayList<>();
		}
		if (firstMenus.size() > 0) {
			for (int i = 0; i < firstMenus.size(); i++) {
				firstMenuMap.put(firstMenus.get(i).getId()+"", firstMenus.get(i));
			}
		}
		if (secondMenus != null && secondMenus.size() > 0 && firstMenus.size() > 0) {
			for (int i = 0; i < secondMenus.size(); i++) {
				Function parent = firstMenuMap.get(secondMenus.get(i).getParent_id() + "");
				if (parent == null) {
					continue;
				}
				parent.getChildren().add(secondMenus.get(i));
				
			}
		}
		
		Map<String, Function> secondMenuMap = new HashMap<>();
		if (secondMenus.size() > 0) {
			for (Function function : secondMenus) {
				secondMenuMap.put(function.getId() + "", function);
			}
		}
		if (thirdMenus != null && thirdMenus.size() > 0 && secondMenus.size() > 0) {
			for (Function function : thirdMenus) {
				Function parent = secondMenuMap.get(function.getParent_id() + "");
				if (parent == null) {
					continue;
				}
				parent.getChildren().add(function);
			}
		}
		
		//组织功能字符串列表
		List<String> functionStrings = new ArrayList<>();
		if (functions != null && functions.size() > 0) {
			for (int i = 0; i < functions.size(); i++) {
				functionStrings.add(functions.get(i).getMethod());
			}
		}
		
		//判断用户登录密码是否过期
		boolean hasUpdatePass = true;
		if (adminUser.getLogin_password_update_time() != null) {
			int passCycle = systemSettingService.getByName(SystemSetting.LOGIN_PASS_CYCLE).getIntValue();
			long lastUpdatePassTime = adminUser.getLogin_password_update_time().getTime();
			long nowTime = System.currentTimeMillis();
			if (nowTime - lastUpdatePassTime < (long)passCycle*24*60*60*1000) {
				hasUpdatePass = false;
			}
		}
		
		//判断是否含有个人中心的所有权限
		if (hasUpdatePass) {
			if (functionStrings.contains("plat.user_center.search") && functionStrings.contains("plat.user_center.edit") ) {
				List<Function> updateLoginPassMenu = new ArrayList<>();
				for (Function menu : firstMenus) {
					if (menu.getId() == 1) {
						updateLoginPassMenu.add(menu);
						for (Function menu2 : menu.getChildren()) {
							if (menu2.getId() == 15) {
								menu.setChildren(new ArrayList<Function>());
								menu.getChildren().add(menu2);
								break;
							}
						}
						break;
					}
				}
				firstMenus = updateLoginPassMenu;
			} else {
				resultJson.put("status", "error");
				resultJson.put("errorMsg", "您的密码已过期，请联系系统管理员重置登录密码后登录");
				return resultJson.toString();
			}
		}
		
		//登录成功，创建sessionid，拉取菜单数据，缓存功能权限信息，更新用户登录时间，清空登录次数
		adminUser.setLogin_time(new Date());
		adminUser.setNum(0);
		adminUser.setLogin_fail_time(null);
		adminUserMapper.updateAdminUser(adminUser);
		String sessionid = UUID.randomUUID().toString();
		//添加用户登录缓存信息
		AdminUserLoginInfo adminUserLoginInfo = new AdminUserLoginInfo();
		adminUserLoginInfo.setActiveTime(System.currentTimeMillis());
		adminUserLoginInfo.setUserInfo(adminUser);
		adminUserLoginInfo.setSessionid(sessionid);
		adminUserLoginInfo.setFunctions(functionStrings);
		setCache(CACHE_LOGIN_INFO + adminUser.getId(), adminUserLoginInfo);
		
		resultJson.put("status", "ok");
		resultJson.put("currentAuthority", "authority_"+adminUser.getRole_id());
		resultJson.put("function", functionStrings);
		resultJson.put("menu", firstMenus);
		resultJson.put("sessionid", sessionid);
		resultJson.put("aid", adminUser.getId());
		resultJson.put("uname", adminUser.getUser_name());
		resultJson.put("lt", System.currentTimeMillis());
		resultJson.put("ot", systemSettingService.getByName(SystemSetting.OPERATE_PASS_TIME).getIntValue()*60*1000);
		resultJson.put("hostKibana", hostKibana);
		resultJson.put("hostGrafana", hostGrafana);
		resultJson.put("hostIndex", hostIndex);
		
		requestAttributes.setAttribute("loginResult", "验证成功", RequestAttributes.SCOPE_REQUEST);
		
		return resultJson.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(MD5.toMD5("123456jiuzhou_root"+AdminUserServiceImpl.PASSWORD_SALT).toUpperCase());
	}
	
	
}

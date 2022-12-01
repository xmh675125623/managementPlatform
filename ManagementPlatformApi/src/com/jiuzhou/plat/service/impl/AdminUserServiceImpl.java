package com.jiuzhou.plat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.AdminUser;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.OperateLog;
import com.jiuzhou.plat.bean.Role;
import com.jiuzhou.plat.bean.SystemSetting;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.mapper.AdminUserMapper;
import com.jiuzhou.plat.mapper.OperateLogMapper;
import com.jiuzhou.plat.mapper.RoleMapper;
import com.jiuzhou.plat.service.AdminUserService;
import com.jiuzhou.plat.service.SystemSettingService;
import com.jiuzhou.plat.util.MD5;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月4日 下午2:58:26
* 类说明
*/
@Service("AdminUserService")
public class AdminUserServiceImpl extends ServiceBase implements AdminUserService {

	@Autowired
	AdminUserMapper adminUserMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	OperateLogMapper operateLogMapper;
	@Autowired
	SystemSettingService systemSettingService;
	
	/**
	 * 密码加盐
	 */
	public static String PASSWORD_SALT = "JIUZHOU_SALT";
	
	/**
	 * 分页获取用户列表
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getListByPage(JSONObject paramJson) throws Exception {
		
		int page = 1;
		if (paramJson.has("page")) {
			page = paramJson.getInt("page");
		}
		
		int pageSize = 10;
		if (paramJson.has("pageSize")) {
			pageSize = paramJson.getInt("pageSize");
		}
		
		List<AdminUser> users = adminUserMapper.getListByPage((page-1)*pageSize,
																pageSize); 
		if (users == null) {
			users = new ArrayList<>();
		}
		for (AdminUser adminUser : users) {
			adminUser.setPassword("");
			adminUser.setOption_password("");
		}
		
		int listCount = adminUserMapper.getListCount();
		int passLowercase = systemSettingService.getByName(SystemSetting.LOGIN_PASS_LOWERCASE).getIntValue();
		int passUppercase = systemSettingService.getByName(SystemSetting.LOGIN_PASS_UPPERCASE).getIntValue();
		int passSpecialChar = systemSettingService.getByName(SystemSetting.LOGIN_PASS_SPECIAL_CHAR).getIntValue();
		int passNumber = systemSettingService.getByName(SystemSetting.LOGIN_PASS_NUMBER).getIntValue();
		int passLength = systemSettingService.getByName(SystemSetting.LOGIN_PASS_LENGTH).getIntValue();
		
		CommonResult result = new CommonResult(true, "");
		result.put("list", users);
		result.put("count", listCount);
		result.put("passLowercase", passLowercase);
		result.put("passUppercase", passUppercase);
		result.put("passSpecialChar", passSpecialChar);
		result.put("passNumber", passNumber);
		result.put("passLength", passLength);
		if (page == 1) {
			List<Role> roles = roleMapper.getListNoPage();
			result.put("roles", roles);
		}
		
		return result.toString();
	}

	/**
	 * 获取所有用户列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getListNoPage() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 变更用户信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public String update(JSONObject paramJson, int logId, String logTableName) throws Exception {
		CommonResult result = new CommonResult(false, "");
		
		//获取被编辑用户id
		if (!paramJson.has("uid")) {
			result.setErrorMsg("缺少参数uid");
			return result.toString();
		}
		int uid = paramJson.getInt("uid");
		
		//如果为重置登录次数
		if (paramJson.has("action") && "resetLogin".equals(paramJson.getString("action"))) {
			AdminUser user = adminUserMapper.getAdminUserById(uid);
			if (user == null) {
				result.setErrorMsg("用户不存在");
				return result.toString();
			}
			user.setNum(0);
			adminUserMapper.updateAdminUser(user);
			result.setStatus(true);
			super.deleteCache(CACHE_LOGIN_INFO+uid);
			return result.toString();
		}
		
		//获取密码
		String password = "";
		if (paramJson.has("password")) {
			password = paramJson.getString("password");
		}
		
		//获取操作密码
		String option_password = "";
		if (paramJson.has("option_password_1")) {
			option_password = paramJson.getString("option_password_1");
		}
		
		//获取角色id
		if (!paramJson.has("role_id")) {
			result.setErrorMsg("请选择角色");
			return result.toString();
		}
		int role_id = paramJson.getInt("role_id");
		
		//获取备注
		String remark = "";
		if (paramJson.has("remark")) {
			remark = paramJson.getString("remark");
		}
		
		AdminUser user = adminUserMapper.getAdminUserById(uid);
		if (user == null) {
			result.setErrorMsg("用户不存在");
			return result.toString();
		}
		
		//操作日志描述
		String logDescription = "编辑前\n";
		Role role = roleMapper.getById(user.getRole_id());
		user.setRole_name(role.getRole_name());
		logDescription += user.toLogDescription();
		logDescription += "\n编辑后\n";
		
		if (StringUtils.isNotBlank(password)) {
			user.setPassword(MD5.toMD5(password+user.getUser_account()+PASSWORD_SALT).toUpperCase());
			user.setLogin_password_update_time(new Date());
		}
		if (StringUtils.isNotBlank(option_password)) {
			user.setOption_password(MD5.toMD5(option_password));
		}
		user.setRole_id(role_id);
		user.setUpdate_time(new Date());
		user.setUpdate_user(getCache(CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
							AdminUserLoginInfo.class)
							.getUserInfo()
							.getUser_name());
		user.setRemark(remark);
		role =  roleMapper.getById(role_id);
		user.setRole_name(role.getRole_name());
		
		adminUserMapper.updateAdminUser(user);
		
		logDescription += user.toLogDescription();
		OperateLog log = new OperateLog();
		log.setId(logId);
		log.setDescription(logDescription);
		operateLogMapper.update(log, logTableName);
		
		result.setStatus(true);
		//强制该用户退出当前登录
		super.deleteCache(CACHE_LOGIN_INFO+uid);
		return result.toString();
	}

	/**
	 * 添加用户信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public String insert(JSONObject paramJson, int logId, String logTableName) throws Exception {
		CommonResult result = new CommonResult(false, "");
		
		if (!paramJson.has("user_account")) {
			result.setErrorMsg("请输入帐号");
			return result.toString();
		}
		String user_account = paramJson.getString("user_account");
		//判断帐号是否已经存在
		AdminUser adminUser = adminUserMapper.getAdminUserByAccount(user_account);
		if (adminUser != null) {
			result.setErrorMsg("帐号已存在");
			return result.toString();
		}
		
		if (!paramJson.has("password")) {
			result.setErrorMsg("请输入密码");
			return result.toString();
		}
		String password = paramJson.getString("password");
		
		if (!paramJson.has("option_password_1")) {
			result.setErrorMsg("请输入操作密码");
			return result.toString();
		}
		String option_password = paramJson.getString("option_password_1");
		
		if (!paramJson.has("role_id")) {
			result.setErrorMsg("请选择角色");
			return result.toString();
		}
		int role_id = paramJson.getInt("role_id");
		
		if (!paramJson.has("user_name")) {
			result.setErrorMsg("请输入用户名");
			return result.toString();
		}
		String user_name = paramJson.getString("user_name");
		//判断用户名是否存在
		adminUser = adminUserMapper.getAdminUserByUserName(user_name);
		if (adminUser != null) {
			result.setErrorMsg("用户名已存在");
			return result.toString();
		}
		
		String remark = "";
		if (paramJson.has("remark")) {
			remark = paramJson.getString("remark");
		}
		
		AdminUser addUser = new AdminUser();
		addUser.setUser_account(user_account);
		addUser.setPassword(MD5.toMD5(password+user_account+PASSWORD_SALT).toUpperCase());
		addUser.setOption_password(MD5.toMD5(option_password));
		addUser.setRole_id(role_id);
		addUser.setUser_name(user_name);
		addUser.setAdd_time(new Date());
		addUser.setAdd_user(getCache(CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
							AdminUserLoginInfo.class)
							.getUserInfo()
							.getUser_name());
		addUser.setRemark(remark);
		addUser.setLogin_password_update_time(new Date());
		
		Role role = roleMapper.getById(role_id);
		addUser.setRole_name(role.getRole_name());
		
		int resultInt = adminUserMapper.insert(addUser);
		if (resultInt > 0) {
			OperateLog log = new OperateLog();
			log.setId(logId);
			log.setDescription(addUser.toLogDescription());
			operateLogMapper.update(log, logTableName);
			result.setStatus(true);
			return result.toString();
		}
		result.setErrorMsg("添加失败");
		return result.toString();
	}

	/**
	 * 删除用户信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String delete(JSONObject paramJson, int logId, String logTableName) throws Exception {
		
		CommonResult result = new CommonResult(false, "");
		
		//获取被编辑用户id
		if (!paramJson.has("uid")) {
			result.setErrorMsg("缺少参数uid");
			return result.toString();
		}
		int uid = paramJson.getInt("uid");
		
		if (uid == 1 || uid == -1) {
			result.setErrorMsg("该用户不可删除");
			return result.toString();
		}
		
		//判断是不是删除自己
		if (uid == paramJson.getInt("aid")) {
			result.setErrorMsg("不可删除自身");
			return result.toString();
		}
		
		//判断用户是否存在
		AdminUser user = adminUserMapper.getAdminUserById(uid);
		if (user == null) {
			result.setErrorMsg("用户不存在");
			return result.toString();
		}
		
		Role role = roleMapper.getById(user.getRole_id());
		user.setRole_name(role.getRole_name());
		
		adminUserMapper.deleteById(uid);
		
		//操作日志描述
		OperateLog log = new OperateLog();
		log.setId(logId);
		log.setDescription(user.toLogDescription());
		operateLogMapper.update(log, logTableName);
		
		//强制该用户退出当前登录
		super.deleteCache(CACHE_LOGIN_INFO+uid);
		result.setStatus(true);
		
		return result.toString();
	}

	
	@Override
	public String getSelfUserInfo(JSONObject paramJson) throws Exception {
		
		int aid = paramJson.getInt("aid");
		AdminUser adminUser = adminUserMapper.getAdminUserById(aid);
		if (adminUser != null) {
			adminUser.setPassword("");
			adminUser.setOption_password("");
			
			Role role = roleMapper.getById(adminUser.getRole_id());
			adminUser.setRole_name(role.getRole_name());;
		}
		
		int passLowercase = systemSettingService.getByName(SystemSetting.LOGIN_PASS_LOWERCASE).getIntValue();
		int passUppercase = systemSettingService.getByName(SystemSetting.LOGIN_PASS_UPPERCASE).getIntValue();
		int passSpecialChar = systemSettingService.getByName(SystemSetting.LOGIN_PASS_SPECIAL_CHAR).getIntValue();
		int passNumber = systemSettingService.getByName(SystemSetting.LOGIN_PASS_NUMBER).getIntValue();
		int passLength = systemSettingService.getByName(SystemSetting.LOGIN_PASS_LENGTH).getIntValue();
		int hasUpdatePass = 1;
		if (adminUser.getLogin_password_update_time() != null) {
			int passCycle = systemSettingService.getByName(SystemSetting.LOGIN_PASS_CYCLE).getIntValue();
			long lastUpdatePassTime = adminUser.getLogin_password_update_time().getTime();
			long nowTime = System.currentTimeMillis();
			if (nowTime - lastUpdatePassTime < (long)passCycle*24*60*60*1000) {
				hasUpdatePass = 0;
			}
		}
		
		
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("user_info", adminUser);
		commonResult.put("passLowercase", passLowercase);
		commonResult.put("passUppercase", passUppercase);
		commonResult.put("passSpecialChar", passSpecialChar);
		commonResult.put("passNumber", passNumber);
		commonResult.put("passLength", passLength);
		commonResult.put("hasUpdatePass", hasUpdatePass);
		
		return commonResult.toString();
	}

	/**
	 * 修改密码
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updatePassword(JSONObject paramJson, 
			AdminUserLoginInfo loginInfo,
			int logId, 
			String logTableName) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		if (!paramJson.has("action")) {
			commonResult.setErrorMsg("缺少参数action");
			return commonResult.toString();
		}
		String action = paramJson.getString("action");
		
		if (!paramJson.has("old_password")) {
			commonResult.setErrorMsg("请输入原密码");
			return commonResult.toString();
		}
		String old_password = paramJson.getString("old_password");
		
		if ("loginPassword".equals(action)) {
			old_password = 
					MD5.toMD5(old_password+loginInfo.getUserInfo().getUser_account()+PASSWORD_SALT)
					.toUpperCase();
			if (!old_password.equals(loginInfo.getUserInfo().getPassword())) {
				commonResult.setErrorMsg("原登录密码输入错误");
				return commonResult.toString();
			}
			
			if (!paramJson.has("login_password_1") || 
					StringUtils.isBlank(paramJson.getString("login_password_1"))) {
				commonResult.setErrorMsg("请输入新密码");
				return commonResult.toString();
			}
			String login_password_1 = paramJson.getString("login_password_1");
			AdminUser adminUser = adminUserMapper.getAdminUserById(loginInfo.getUserInfo().getId());
			
			Role role = roleMapper.getById(adminUser.getRole_id());
			adminUser.setRole_name(role.getRole_name());
			String logDescription = "编辑前\n" + adminUser.toLogDescription() + "\n编辑后\n";
			
			adminUser.setPassword(MD5.toMD5(login_password_1+adminUser.getUser_account()+PASSWORD_SALT).toUpperCase());
			adminUser.setLogin_password_update_time(new Date());
			adminUserMapper.updateAdminUser(adminUser);
			
			logDescription += adminUser.toLogDescription();
			OperateLog log = new OperateLog();
			log.setId(logId);
			log.setDescription(logDescription);
			operateLogMapper.update(log, logTableName);
			
			commonResult.setStatus(true);
			//强制该用户退出当前登录
			super.deleteCache(CACHE_LOGIN_INFO+loginInfo.getUserInfo().getId());
			return commonResult.toString();
			
		} else if ("optionPassword".equals(action)) {
			
			if (!MD5.toMD5(old_password).equals(loginInfo.getUserInfo().getOption_password())) {
				commonResult.setErrorMsg("原操作密码输入错误");
				return commonResult.toString();
			}
			
			if (!paramJson.has("option_password_1") || 
					StringUtils.isBlank(paramJson.getString("option_password_1"))) {
				commonResult.setErrorMsg("请输入新密码");
				return commonResult.toString();
			}
			String option_password_1 = paramJson.getString("option_password_1");
			AdminUser adminUser = adminUserMapper.getAdminUserById(loginInfo.getUserInfo().getId());
			
			Role role = roleMapper.getById(adminUser.getRole_id());
			adminUser.setRole_name(role.getRole_name());
			String logDescription = "编辑前\n" + adminUser.toLogDescription() + "\n编辑后\n";
			
			adminUser.setOption_password(MD5.toMD5(option_password_1));
			adminUserMapper.updateAdminUser(adminUser);
			
			logDescription += adminUser.toLogDescription();
			OperateLog log = new OperateLog();
			log.setId(logId);
			log.setDescription(logDescription);
			operateLogMapper.update(log, logTableName);
			
			commonResult.setStatus(true);
			//强制该用户退出当前登录
			super.deleteCache(CACHE_LOGIN_INFO+loginInfo.getUserInfo().getId());
			return commonResult.toString();
			
		}
		commonResult.setErrorMsg("修改失败");
		return commonResult.toString();
	}

}

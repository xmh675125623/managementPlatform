package com.jiuzhou.plat.interceptor;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jiuzhou.plat.bean.AdminUser;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.Function;
import com.jiuzhou.plat.bean.OperateLog;
import com.jiuzhou.plat.bean.SystemSetting;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.exception.AuthExcetion;
import com.jiuzhou.plat.exception.LoginException;
import com.jiuzhou.plat.service.FunctionService;
import com.jiuzhou.plat.service.OperateLogService;
import com.jiuzhou.plat.service.SystemSettingService;
import com.jiuzhou.plat.service.impl.AdminUserServiceImpl;
import com.jiuzhou.plat.service.impl.ServiceBase;
import com.jiuzhou.plat.util.MD5;
import com.jiuzhou.plat.util.WebDataAESUtils;
import com.jiuzhou.plat.wrapper.InputStreamRequestWrapper;

import net.sf.json.JSONObject;

/**
 * 拦截器：用于用户登录信息验证、功能权限验证、操作日志记录
 * @author xingmh
 */
public class SystemInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private SystemSettingService systemSettingService;
	
	private ServiceBase serviceBase = new ServiceBase();
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		
		String functionUrl = "/ManagementPlatformApi/function/";
		String logoutUrl = "/ManagementPlatformApi/admin_login/logout";
		
		if (uri.indexOf(functionUrl) == 0) {
			if (request instanceof InputStreamRequestWrapper) {
				//从InputStream包装器中获取参数流
				InputStreamRequestWrapper inputStreamRequestWrapper = (InputStreamRequestWrapper)request;
		        String requestBody = inputStreamRequestWrapper.getBody();
		        JSONObject paramJson = JSONObject.fromObject(requestBody);
		        
		        //用户登录信息及权限认证
		        if (!auth(paramJson, request, response, false)){
		        	return false;
		        }
			} else if (request instanceof DefaultMultipartHttpServletRequest) {
				// 针对文件上传等
				
				// web数据解密
				String key = request.getParameter("key");
				String value = request.getParameter("value");
				JSONObject paramJson = new JSONObject();
				paramJson.put("key", key);
				paramJson.put("value", value);
				String body = WebDataAESUtils.webDataDecrypt(paramJson.toString());
				paramJson = JSONObject.fromObject(body);
//				JSONObject paramJson = new JSONObject();
//				if (request.getParameter("method") != null) {
//					paramJson.put("method", request.getParameter("method"));
//				}
//				if (request.getParameter("aid") != null) {
//					paramJson.put("aid", request.getParameter("aid"));
//				}
//				if (request.getParameter("sessionid") != null) {
//					paramJson.put("sessionid", request.getParameter("sessionid"));
//				}
//				if (request.getParameter("option_password") != null) {
//					paramJson.put("option_password", request.getParameter("option_password"));
//				}
				//用户登录信息及权限认证
		        if (!auth(paramJson, request, response, true)){
		        	return false;
		        }
			}
		} else if (request.getRequestURI().indexOf(logoutUrl) == 0) {
			InputStreamRequestWrapper inputStreamRequestWrapper = (InputStreamRequestWrapper)request;
            String requestBody = inputStreamRequestWrapper.getBody();
            JSONObject jsonObject = JSONObject.fromObject(requestBody);
            String ip = request.getRemoteAddr();
            int aid = 0;
            if (jsonObject.has("aid")) {
            	aid = jsonObject.getInt("aid");
            }
            String sessionid = "";
            if (jsonObject.has("sessionid")) {
            	sessionid = jsonObject.getString("sessionid");
            }
            AdminUserLoginInfo loginInfo = 
            		new ServiceBase().getCache(ServiceBase.CACHE_LOGIN_INFO + aid, 
            				AdminUserLoginInfo.class);
            OperateLog log = new OperateLog();
            if (loginInfo != null) {
            	log.setUser_id(aid);
            	log.setUser_name(loginInfo.getUserInfo().getUser_name());
            }
            log.setType("登录");
            log.setFunction_id(-10000);
            log.setFunction_name("退出登录");
            log.setDescription("管理员id：" + aid +" sessionId：" + sessionid);
            log.setIp_address(ip);
            log.setAdd_time(new Date());
            operateLogService.insert(log);
		}
		
		return super.preHandle(request, response, handler);
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		
		String loginUrl = "/ManagementPlatformApi/admin_login/login";
		
        //如果是登录请求则记录登录日志
        if (request.getRequestURI().indexOf(loginUrl) == 0) {
        	InputStreamRequestWrapper inputStreamRequestWrapper = (InputStreamRequestWrapper)request;
            String requestBody = inputStreamRequestWrapper.getBody();
            JSONObject jsonObject = JSONObject.fromObject(requestBody);
            String ip = request.getRemoteAddr();
            String result = (String)request.getAttribute("loginResult");
            String loginErrMsg = null;
            if (request.getAttribute("loginErrMsg") != null) {
            	loginErrMsg = (String)request.getAttribute("loginErrMsg");
            }
            
            String md5Password = 
            		MD5.toMD5(
            				jsonObject.getString("password")
            				+jsonObject.getString("account")
            				+AdminUserServiceImpl.PASSWORD_SALT
            				)
            		.toUpperCase();
            OperateLog log = new OperateLog();
            log.setType("登录");
            log.setFunction_id(-10000);
            log.setFunction_name("用户登录");
            log.setDescription("用户名：" + jsonObject.getString("account") +" 密码：" 
            					+ md5Password + " "
            					+ (loginErrMsg != null ? loginErrMsg : ""));
            log.setIp_address(ip);
            log.setAuth_result(result);
            log.setAdd_time(new Date());
            operateLogService.insert(log);
        } else if (request.getAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION) != null) {
        	//当前操作日志描述
        	Object currentOperateDesc = request.getAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION);
        	//当前操作日志id
    		Object currentOperateId = request.getAttribute("currentOperateId");
    		//当前操作日志表名
    		Object currentOperateTableName = request.getAttribute("currentOperateTableName");
    		if (currentOperateId != null && currentOperateTableName != null) {
    			OperateLog log = new OperateLog();
    			log.setId((int)currentOperateId);
    			log.setDescription((String)currentOperateDesc);
    			operateLogService.update(log, (String)currentOperateTableName);
    		}
        }
		super.afterCompletion(request, response, handler, ex);
	}
	
	/**
	 * 用户登录信息及权限认证
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private boolean auth(JSONObject paramJson, 
			HttpServletRequest request, 
			HttpServletResponse response,
			boolean isUpload) throws Exception {
		
		//获取用户ip
		String ip = request.getRemoteAddr();
		
		//创建log对象
		OperateLog log = new OperateLog();
		log.setType("功能请求");
		log.setIp_address(ip);
		log.setAdd_time(new Date());
		
		int uid = 0;
		if (paramJson.has("aid")) {
			uid = paramJson.getInt("aid");
		}
		
		String sessionid = null;
		if (paramJson.has("sessionid")) {
			sessionid = paramJson.getString("sessionid");
		}
		
		String method = null;
		if (paramJson.has("method")) {
			method = paramJson.getString("method");
		}
		
		//用户登录缓存信息
		AdminUserLoginInfo loginInfo = serviceBase.getCache(
        		ServiceBase.CACHE_LOGIN_INFO + uid, 
        		AdminUserLoginInfo.class);
		if (loginInfo != null) {
			log.setUser_id(loginInfo.getUserInfo().getId());
			log.setUser_name(loginInfo.getUserInfo().getUser_name());
		}
		
		//功能信息
		Function function = functionService.findByMethod(method);
		if (function != null) {
			log.setFunction_id(function.getId());
			log.setFunction_name(function.getFunction_name());
		}
		
		//验证登录信息
		if (loginInfo == null || !sessionid.equals(loginInfo.getSessionid())) {
			log.setAuth_result("验证失败");
			log.setDescription("用户登录信息验证失败，未通过验证");
			operateLogService.insert(log);
			if (!isUpload)  {
				throw new LoginException();
			} else {
				CommonResult commonResult = new CommonResult(false, "用户登录信息验证失败，未通过验证");
				commonResult.put("code", 401);
				PrintWriter writer = response.getWriter();
		        writer.print(commonResult.toString());
		        writer.close();
		        response.flushBuffer();
		        return false;
			}
			
		}
		
		//验证请求码判断是否为会话重放攻击
		if (!loginInfo.authRequestCode(paramJson.getString("requestCode"))) {
			log.setAuth_result("验证失败");
			log.setDescription("会话重放攻击");
			operateLogService.insert(log);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			CommonResult commonResult = new CommonResult(false, "数据提交异常，请重新登录");
			PrintWriter writer = response.getWriter();
	        writer.print(commonResult.toString());
	        writer.close();
	        response.flushBuffer();
	        return false;
		}
		
		
		//验证功能权限
		if (function == null || !loginInfo.getFunctions().contains(method)) {
			log.setAuth_result("验证失败");
			log.setDescription("用户功能权限验证失败，未通过验证");
			operateLogService.insert(log);
			if (!isUpload)  {
				throw new AuthExcetion();
			} else {
				CommonResult commonResult = new CommonResult(false, "用户功能权限验证失败，未通过验证");
				commonResult.put("code", 403);
				PrintWriter writer = response.getWriter();
		        writer.print(commonResult.toString());
		        writer.close();
		        response.flushBuffer();
		        return false;
			}
			
		}
		
		//验证操作密码
		if (function.getOption_password_auth() == 1) {
			String optionPassword = null;
			if (paramJson.has("option_password")) {
				optionPassword = paramJson.getString("option_password");
			}
			
			String md5OptionPassword = MD5.toMD5(optionPassword);
			AdminUser loginUser = loginInfo.getUserInfo();
			
			//判断是否操作密码验证过期
			int operatePassTime = systemSettingService.getByName(SystemSetting.OPERATE_PASS_TIME).getIntValue()*60*1000;
			if (StringUtils.isBlank(optionPassword) && operatePassTime!= 0) {
				long admitOperationTime = loginInfo.getAdmitOperationTime();
				if (System.currentTimeMillis() - admitOperationTime > operatePassTime) {
					CommonResult commonResult = new CommonResult(false, "操作密码验证过期，请重新操作并输入操作密码");
					commonResult.put("needInputOptPass", 1);
					commonResult.put("ot", operatePassTime);
					PrintWriter writer = response.getWriter();
			        writer.print(commonResult.toString());
			        writer.close();
			        response.flushBuffer();
			        return false;
				}
				
			} else if (StringUtils.isBlank(optionPassword) || 
					!loginUser.getOption_password().equals(md5OptionPassword)) {
				
				//判断连续输入操作密码错误次数,超过设定次数则退出登录
				int operateFailCountSetting = systemSettingService
												.getByName(SystemSetting.OPERATE_ALLOW_SUM)
												.getIntValue();
				if (loginUser.getOperate_num() >= operateFailCountSetting) {
					if (!isUpload)  {
						throw new LoginException();
					} else {
						CommonResult commonResult = new CommonResult(false, "用户操作密码验证失败，未通过验证");
						commonResult.put("code", 401);
						PrintWriter writer = response.getWriter();
				        writer.print(commonResult.toString());
				        writer.close();
				        response.flushBuffer();
				        return false;
					}
				} else {
					loginUser.setOperate_num(loginUser.getOperate_num() + 1);
				}
				log.setAuth_result("验证失败");
				log.setDescription("用户操作密码验证失败，未通过验证");
				operateLogService.insert(log);
				CommonResult commonResult = new CommonResult(false, "操作密码验证失败, 请重试");
				commonResult.put("needInputOptPass", 1);
				commonResult.put("ot", operatePassTime);
				PrintWriter writer = response.getWriter();
		        writer.print(commonResult.toString());
		        writer.close();
		        response.flushBuffer();
		        return false;
			}
			
			loginInfo.setAdmitOperationTime(System.currentTimeMillis());
		}
		
		log.setAuth_result("验证通过");
		String logTableName = operateLogService.insert(log);
		//更新用户会话最后活动时间
		loginInfo.setActiveTime(System.currentTimeMillis());
		//记录当前操作日志id
		request.setAttribute("currentOperateId", log.getId());
		//记录当前操作日志表名
		request.setAttribute("currentOperateTableName", logTableName);
		return true;
		
	}
	
}

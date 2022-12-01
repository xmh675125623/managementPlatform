package com.jiuzhou.plat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiuzhou.plat.bean.AdminUser;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.bean.Function;
import com.jiuzhou.plat.bean.Role;
import com.jiuzhou.plat.bean.RoleFunction;
import com.jiuzhou.plat.cache.AdminUserLoginInfo;
import com.jiuzhou.plat.mapper.AdminUserMapper;
import com.jiuzhou.plat.mapper.FunctionMapper;
import com.jiuzhou.plat.mapper.RoleFunctionMapper;
import com.jiuzhou.plat.mapper.RoleMapper;
import com.jiuzhou.plat.service.RoleService;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 2018年9月9日 下午3:39:10
* 类说明
*/
@Service(value="RoleService")
public class RoleServiceImpl extends ServiceBase implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private FunctionMapper functionMapper;
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	@Autowired
	private AdminUserMapper adminUserMapper;
	
	/**
	 * 获取所有的角色列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getListNoPage() throws Exception {
		
		//角色列表
		List<Role> roles = roleMapper.getListNoPageDESC();
		
		//菜单功能树数据组织
		List<Function> firstMenus = functionMapper.getFirstMenus();		//一级菜单
		List<Function> secondMenus = functionMapper.getSecondMenus();	//二级菜单
		List<Function> thirdMenus = functionMapper.getThirdMenus();		//三级菜单
		List<Function> functions = functionMapper.getFunctions();		//功能列表
		//组织菜单
		Map<String, Function> firstMenuMap = new HashMap<>();
		Map<String, Function> secondMenuMap =  new HashMap<>();
		Map<String, Function> thirdMenuMap =  new HashMap<>();
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
				secondMenuMap.put(secondMenus.get(i).getId()+"", secondMenus.get(i));
				Function parent = firstMenuMap.get(secondMenus.get(i).getParent_id() + "");
				if (parent == null) {
					continue;
				}
				parent.getChildren().add(secondMenus.get(i));
				
			}
		}
		if (thirdMenus != null && thirdMenus.size() > 0 && secondMenus.size() > 0) {
			for (Function thirdMenu : thirdMenus) {
				thirdMenuMap.put(thirdMenu.getId() + "", thirdMenu);
				Function parent = secondMenuMap.get(thirdMenu.getParent_id() + "");
				if (parent == null) {
					continue;
				}
				parent.getChildren().add(thirdMenu);
			}
		}
		if (functions != null && functions.size() > 0) {
			for (int i = 0; i < functions.size(); i++) {
				Function parent = thirdMenuMap.get(functions.get(i).getParent_id() + "");
				if (parent == null) {
					continue;
				}
				parent.getChildren().add(functions.get(i));
				
			}
		}
		
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("roles", roles);
		
		return commonResult.toString();
	}

	/**
	 * 添加角色
	 * @return
	 * @throws Exception
	 */
	@Override
	public String addRole(JSONObject paramJson) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取角色名称
		if (!paramJson.has("role_name")) {
			commonResult.setErrorMsg("请输入角色名称");
			return commonResult.toString();
		}
		String role_name = paramJson.getString("role_name");
		
		//判断角色名称是否已被使用
		Role role = roleMapper.getByName(role_name);
		if (role != null) {
			commonResult.setErrorMsg("角色名称已被使用");
			return commonResult.toString();
		}
		
		String role_description = "";
		if (paramJson.has("role_description")) {
			role_description = paramJson.getString("role_description");
		}
		
		String remark = "";
		if (paramJson.has("remark")) {
			remark = paramJson.getString("remark");
		}
		
		role = new Role();
		role.setRole_name(role_name);
		role.setRole_description(role_description);
		role.setRemark(remark);
		role.setAdd_time(new Date());
		role.setAdd_user(getCache(CACHE_LOGIN_INFO + paramJson.getInt("aid"), 
				AdminUserLoginInfo.class)
				.getUserInfo()
				.getUser_name());
		
		int resultInt = roleMapper.insert(role);
		if (resultInt > 0) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		
		commonResult.setErrorMsg("添加失败");
		return commonResult.toString();
	}

	
	/**
	 * 变更角色信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String updateRole(JSONObject paramJson, AdminUserLoginInfo loginInfo) 
															throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取角色id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//判断角色是否存在
		Role role = roleMapper.getById(id);
		if (role == null) {
			commonResult.setErrorMsg("角色不存在");
			return commonResult.toString();
		}
		
		String action = "";
		if (paramJson.has("action")) {
			action = paramJson.getString("action");
		}
		if ("authTree".equals(action)) {
			//获取菜单功能树
			return getAuthTree(id);
		} else if ("authEdit".equals(action)) {
			//保存权限设置
			String result = authEdit(paramJson, loginInfo);
			//强制拥有该角色的用户退出登录
			forcedLogoutByRoleId(id);
			return result;
		}
		
		//获取角色名称
		if (!paramJson.has("role_name")) {
			commonResult.setErrorMsg("请输入角色名称");
			return commonResult.toString();
		}
		String role_name = paramJson.getString("role_name");
		
		//判断角色名称是否已经被使用
		Role nameRole = roleMapper.getByName(role_name);
		if (nameRole != null && id != nameRole.getId()) {
			commonResult.setErrorMsg("角色名称已被使用");
			return commonResult.toString();
		}
		
		String role_description = "";
		if (paramJson.has("role_description")) {
			role_description = paramJson.getString("role_description");
		}
		
		String remark = "";
		if (paramJson.has("remark")) {
			remark = paramJson.getString("remark");
		}
		
		role.setRole_name(role_name);
		role.setUpdate_time(new Date());
		role.setUpdate_user(loginInfo.getUserInfo().getUser_name());
		role.setRole_description(role_description);
		role.setRemark(remark);
		roleMapper.update(role);
		//强制拥有该角色的用户退出登录
		forcedLogoutByRoleId(id);
		
		commonResult.setStatus(true);;
		return commonResult.toString();
	}

	
	/**
	 * 删除角色信息
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@Override
	public String deleteRole(JSONObject paramJson, AdminUserLoginInfo loginInfo) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取角色id
		if (!paramJson.has("id")) {
			commonResult.setErrorMsg("缺少参数id");
			return commonResult.toString();
		}
		int id = paramJson.getInt("id");
		
		//判断是否有用户正在使用改角色
		List<AdminUser> adminUsers = adminUserMapper.getListByRoleId(id);
		if (adminUsers != null && adminUsers.size() > 0) {
			String userNames = "";
			for (int i = 0; i < adminUsers.size(); i++) {
				if (i == 0) {
					userNames += adminUsers.get(i).getUser_name();
					continue;
				}
				userNames += "," + adminUsers.get(i).getUser_name();
			}
			commonResult.setErrorMsg("以下用户正在使用该权限，无法删除："+userNames);
			return commonResult.toString();
		}
		
		Role role = roleMapper.getById(id);
		role.setDel_flag(1);
		role.setUpdate_time(new Date());
		role.setUpdate_user(loginInfo.getUserInfo().getUser_name());
		roleMapper.update(role);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * 获取菜单功能树及已选数据
	 * @param roleid
	 * @return
	 */
	private String getAuthTree(int roleid) {
		List<Function> firstMenus = functionMapper.getFirstMenus();		//一级菜单
		List<Function> secondMenus = functionMapper.getSecondMenus();	//二级菜单
		List<Function> thirdMenus = functionMapper.getThirdMenus();		//三级菜单
		List<Function> functions = functionMapper.getFunctions();		//功能列表
		//组织菜单
		Map<String, Function> firstMenuMap = new HashMap<>();
		Map<String, Function> secondMenuMap =  new HashMap<>();
		Map<String, Function> thirdMenuMap =  new HashMap<>();
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
				secondMenuMap.put(secondMenus.get(i).getId()+"", secondMenus.get(i));
				Function parent = firstMenuMap.get(secondMenus.get(i).getParent_id() + "");
				if (parent == null) {
					continue;
				}
				parent.getChildren().add(secondMenus.get(i));
				
			}
		}
		if (thirdMenus != null && thirdMenus.size() > 0 && secondMenus.size() > 0) {
			for (Function thirdMenu : thirdMenus) {
				thirdMenuMap.put(thirdMenu.getId() + "", thirdMenu);
				Function parent = secondMenuMap.get(thirdMenu.getParent_id() + "");
				if (parent == null) {
					continue;
				}
				parent.getChildren().add(thirdMenu);
			}
		}
		if (functions != null && functions.size() > 0) {
			for (int i = 0; i < functions.size(); i++) {
				Function parent = thirdMenuMap.get(functions.get(i).getParent_id() + "");
				if (parent == null) {
					continue;
				}
				parent.getChildren().add(functions.get(i));
				
			}
		}
		
		//获取默认展开的节点
		String expand_id = "";
		if (firstMenus != null && firstMenus.size() > 0) {
			for (int i = 0; i < firstMenus.size(); i++) {
				expand_id += firstMenus.get(i).getId() + ",";
			}
		}
		
		//获取默认选中的节点
		List<Function> roleFirstMenus = roleFunctionMapper.getFirstMenusByRoleId(roleid);	//一级菜单
		List<Function> roleSecondMenus = roleFunctionMapper.getSecondMenusByRoleId(roleid);	//二级菜单
		List<Function> roleFunctions = roleFunctionMapper.getFunctionsByRoleId(roleid);	//功能列表
		
		String selected_id = "";
		String half_select_id = "";
		if (roleFirstMenus != null && roleFirstMenus.size() > 0) {
			for (int i = 0; i < roleFirstMenus.size(); i++) {
				if (roleFirstMenus.get(i).getHalf() == 0) {
					selected_id += roleFirstMenus.get(i).getId()+",";
				} else {
					half_select_id += roleFirstMenus.get(i).getId()+",";
				} 
				
			}
		}
		if (roleSecondMenus != null && roleSecondMenus.size() > 0) {
			for (int i = 0; i < roleSecondMenus.size(); i++) {
				if (roleSecondMenus.get(i).getHalf() == 0) {
					selected_id += roleSecondMenus.get(i).getId()+",";
				} else {
					half_select_id += roleSecondMenus.get(i).getId()+",";
				}
				
			}
		}
		if (roleFunctions != null && roleFunctions.size() > 0) {
			for (int i = 0; i < roleFunctions.size(); i++) {
				if (roleFunctions.get(i).getHalf() == 0) {
					selected_id += roleFunctions.get(i).getId()+",";
				} else {
					half_select_id += roleFunctions.get(i).getId()+",";
				}
				
			}
		}
		
		CommonResult commonResult = new CommonResult(true, "");
		commonResult.put("functions", firstMenus);
		commonResult.put("selected_id", selected_id);
		commonResult.put("half_selected_id", half_select_id);
		commonResult.put("expand_id", expand_id);
		
		return commonResult.toString();
	}
	
	/**
	 * 保存角色权限
	 * @param jsonObject
	 * @return
	 */
	private String authEdit(JSONObject paramJson, AdminUserLoginInfo loginInfo) {
		CommonResult commonResult = new CommonResult(false, "");
		
		int id = paramJson.getInt("id");
		//删除之前的权限设置
		roleFunctionMapper.deleteByRoleId(id);
		
		//获取选择的菜单功能id
		String selectedId = "";
		if (paramJson.has("selected_id")) {
			selectedId = paramJson.getString("selected_id");
		}
		selectedId = selectedId.replace("[", "").replace("]", "");
		String[] selectedKeys = null;
		if (StringUtils.isNotBlank(selectedId)) {
			selectedKeys = selectedId.split(",");
		}
		
		if (selectedKeys == null || selectedKeys.length < 1) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		
		//获取选择的菜单功能id
		String HalfSelectedId = "";
		if (paramJson.has("half_selected_id")) {
			HalfSelectedId = paramJson.getString("half_selected_id");
		}
		HalfSelectedId = HalfSelectedId.replace("[", "").replace("]", "");
		String[] HalfSelectedKeys = null;
		if (StringUtils.isNotBlank(HalfSelectedId)) {
			HalfSelectedKeys = HalfSelectedId.split(",");
		}
		
		List<RoleFunction> list = new ArrayList<>();
		for (int i = 0; i < selectedKeys.length; i++) {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setRole_id(id);
			roleFunction.setFunction_id(Integer.parseInt(selectedKeys[i]));
			roleFunction.setAdd_time(new Date());
			roleFunction.setAdd_user(loginInfo.getUserInfo().getUser_name());
			list.add(roleFunction);
		}
		
		if (HalfSelectedKeys != null && HalfSelectedKeys.length > 0) {
			for (int i = 0; i < HalfSelectedKeys.length; i++) {
				RoleFunction roleFunction = new RoleFunction();
				roleFunction.setRole_id(id);
				roleFunction.setFunction_id(Integer.parseInt(HalfSelectedKeys[i]));
				roleFunction.setAdd_time(new Date());
				roleFunction.setAdd_user(loginInfo.getUserInfo().getUser_name());
				roleFunction.setHalf(1);
				list.add(roleFunction);
			}
		}
		roleFunctionMapper.insertBath(list);
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	/**
	 * 强制拥有当前角色的用户退出登录
	 * @param roleId
	 */
	private void forcedLogoutByRoleId(int roleId){
		
		List<AdminUser> list = adminUserMapper.getListByRoleId(roleId);
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				deleteCache(CACHE_LOGIN_INFO+list.get(i).getId());
			}
		}
		
	}
	
	
	
}

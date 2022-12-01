package com.jiuzhou.firewall.bean;

import com.jiuzhou.plat.bean.S7FunctionCode;

/**
* @author xingmh
* @version 创建时间：2020年8月25日 下午4:45:35
* 类说明
*/
public class FirewallRuleS7 extends FirewallStrategy {
	
	private int    id;
	private String pdu_type;
	private String function_code;
	private String user_data_type;
	private String function_group_code;
	private String sub_function_code;
	
	public FirewallRuleS7() {
	}
	
	

	public String getFunction_code() {
		return function_code;
	}


	public void setFunction_code(String function_code) {
		this.function_code = function_code;
	}


	public String getFunction_group_code() {
		return function_group_code;
	}


	public void setFunction_group_code(String function_group_code) {
		this.function_group_code = function_group_code;
	}


	public String getSub_function_code() {
		return sub_function_code;
	}


	public void setSub_function_code(String sub_function_code) {
		this.sub_function_code = sub_function_code;
	}


	@Override
	public String getLogDesc() {
		
		String result =  super.getLogDescProtected();
		if (S7FunctionCode.PDU_TYPE_JOB.equals(this.getPdu_type())) {
			result += "[PDU：Job]\n"
					+ "[功能码："+this.getFunction_code()+"]";
			
		} else if (S7FunctionCode.PDU_TYPE_USERDATA.equals(this.getPdu_type())) {
			result += "[PDU：UserData]\n"
					+ "[参数类型："+this.getUser_data_type()+"]\n"
					+ "[功能组："+this.getFunction_group_code()+"]\n"
					+ "[子功能码："+this.getSub_function_code()+"]";
		}
		
		return result;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}



	public String getPdu_type() {
		return pdu_type;
	}



	public void setPdu_type(String pdu_type) {
		this.pdu_type = pdu_type;
	}



	public String getUser_data_type() {
		return user_data_type;
	}



	public void setUser_data_type(String user_data_type) {
		this.user_data_type = user_data_type;
	}
	
	
}

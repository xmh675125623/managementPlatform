package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2018年10月18日 上午10:35:14
* modbus协议功能码
*/
public class AuditModbusFunctionCode {
	
	public static final int TYPE_MODBUS_TCP = 1;
	public static final int MODE_READ = 1;
	public static final int MODE_WRITE = 2;
	public static final int MODE_READ_WRITE = 3;
	
	private int 	id;			//
	private int 	type;		//协议类型，1：modbus_tcp
	private int 	mode;		//读写类型，1：读，2：写，3：读写
	private String 	func;		//功能
	private int 	code;		//码
	private String 	description;//描述
	
	public AuditModbusFunctionCode() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}
	
	
	
}

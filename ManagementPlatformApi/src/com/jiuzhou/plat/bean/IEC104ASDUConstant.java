package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2019年3月2日 下午3:50:07
* iec104协议常量类
*/
public class IEC104ASDUConstant {

	/**
	 * 常量类型：类型标识
	 */
	public static final int TYPE_TYPE = 1;
	
	/**
	 * 常量类型：传输原因
	 */
	public static final int TYPE_COT = 2;
	
	private int id;
	private int type;
	private int code;
	private String name;
	private String description;
	
	public IEC104ASDUConstant() {
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

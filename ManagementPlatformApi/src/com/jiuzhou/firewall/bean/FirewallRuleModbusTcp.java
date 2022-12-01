package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午2:29:07
* 类说明
*/
public class FirewallRuleModbusTcp extends FirewallStrategy {
	
	/**
	 * 读写类型:读
	 */
	public static final String MODE_READ = "1";
	/**
	 * 读写类型:写
	 */
	public static final String MODE_WRITE = "2";
	/**
	 * 读写类型:读写
	 */
	public static final String MODE_READ_WRITE = "3";
	
	

	private int id;
	private String unit_id;            //modbus设备id
	private String function_code;      //功能码
	private String mode;               //读写类型, 1：读，2：写，3：读写
	private String read_start;         //读起始地址
	private String read_length;        //读地址长度
	private String write_start;        //写起始地址
	private String write_length;       //写地址长度
	private String write_addrs;        //地址列表
	private String write_values;       //写值列表
	
	public FirewallRuleModbusTcp() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}





	public String getWrite_values() {
		return write_values;
	}

	public void setWrite_values(String write_values) {
		this.write_values = write_values;
	}

	@Override
	public String getLogDesc() {
		return super.getLogDescProtected()
				+ "[功能码："+this.getFunction_code()+"]\n"
//				+ "[读写："+this.getModeName(this.getMode())+"]\n"
				+ "[读起始地址："+this.getRead_start()+"]\n"
				+ "[读地址长度："+this.getRead_length()+"]\n"
				+ "[写起始地址："+this.getWrite_start()+"]\n"
				+ "[写地址长度："+this.getWrite_length()+"]\n"
				+ "[地址列表："+this.getWrite_addrs()+"]\n"
				+ "[写值列表："+this.getWrite_values()+"]\n";
	}
	
	private String getModeName(String mode) {
		if (mode == null) {
			return null;
		}
		
		switch (mode) {
			case MODE_READ:
				return "读";
			case MODE_WRITE:
				return "写";
			case MODE_READ_WRITE:
				return "读写";
			default:
				return "未知";
		}
	}


	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getFunction_code() {
		return function_code;
	}

	public void setFunction_code(String function_code) {
		this.function_code = function_code;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getRead_start() {
		return read_start;
	}

	public void setRead_start(String read_start) {
		this.read_start = read_start;
	}

	public String getRead_length() {
		return read_length;
	}

	public void setRead_length(String read_length) {
		this.read_length = read_length;
	}

	public String getWrite_start() {
		return write_start;
	}

	public void setWrite_start(String write_start) {
		this.write_start = write_start;
	}

	public String getWrite_length() {
		return write_length;
	}

	public void setWrite_length(String write_length) {
		this.write_length = write_length;
	}

	public String getWrite_addrs() {
		return write_addrs;
	}

	public void setWrite_addrs(String write_addrs) {
		this.write_addrs = write_addrs;
	}

}

package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2019年3月2日 下午3:59:05
* 类说明
*/
public class FirewallRuleIEC104 extends FirewallStrategy {
	
	private int id;          
	private String type;        //类型标识
	private String cot;         //传输原因
	private String coa;         //公共地址
	private String ioa_start;   //信息对象起始地址
	private String ioa_length;  //信息对象地址长度
	
	public FirewallRuleIEC104() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getLogDesc() {
		return super.getLogDescProtected()
				+ "[类型标识 ："+this.getType()+"]\n"
				+ "[传输原因："+this.getCot()+"]\n"
				+ "[公共地址："+this.getCoa()+"]\n"
				+ "[信息对象起始地址："+this.getIoa_start()+"]\n"
				+ "[信息对象地址长度："+this.getIoa_length()+"]\n";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCot() {
		return cot;
	}

	public void setCot(String cot) {
		this.cot = cot;
	}

	public String getCoa() {
		return coa;
	}

	public void setCoa(String coa) {
		this.coa = coa;
	}

	public String getIoa_start() {
		return ioa_start;
	}

	public void setIoa_start(String ioa_start) {
		this.ioa_start = ioa_start;
	}

	public String getIoa_length() {
		return ioa_length;
	}

	public void setIoa_length(String ioa_length) {
		this.ioa_length = ioa_length;
	}

	
}

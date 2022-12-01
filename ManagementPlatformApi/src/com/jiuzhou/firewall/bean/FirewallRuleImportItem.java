package com.jiuzhou.firewall.bean;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
* @author xingmh
* @version 创建时间：2020年9月2日 下午4:53:10
* 类说明
*/
public class FirewallRuleImportItem {
	
	private long   id;
	private String device_name;
	private String sip;
	private String dip;
	private String protocol;
	private String sport;
	private String dport;
	private int    rule_type;
	private String extension1;
	private String extension2;
	private String extension3;
	private String extension4;
	private String extension5;
	private String extension6;
	private String extension7;
	private String extension8;
	private String extension9;
	private String extension10;
	private String extension11;
	private String extension12;
	private String extension13;
	private String extension14;
	private String extension15;
	private String extension16;
	private String extension17;
	private String extension18;
	private String extension19;
	private String extension20;
	private Date   add_time;
	

	public FirewallRuleImportItem() {
	}
	
	public String getSip() {
		return sip;
	}

	public void setSip(String sip) {
		this.sip = sip;
	}

	public String getDip() {
		return dip;
	}

	public void setDip(String dip) {
		this.dip = dip;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getDport() {
		return dport;
	}

	public void setDport(String dport) {
		this.dport = dport;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	
	public String toCompareString() {
		String result = "";
		result += (StringUtils.isBlank(this.device_name)?"":this.device_name.trim())+"-";
		result += (StringUtils.isBlank(this.sip)?"":this.sip.trim())+"-";
		result += (StringUtils.isBlank(this.dip)?"":this.dip.trim())+"-";
		if (this.rule_type == FirewallStrategy.RULE_TYPE_CUSTOM) {
			result += (StringUtils.isBlank(this.protocol)?"":this.protocol.trim())+"-";
			result += (StringUtils.isBlank(this.sport)?"":this.sport.trim())+"-";
			result += (StringUtils.isBlank(this.dport)?"":this.dport.trim())+"-";
		} else {
			result += "---";
		}
		result += this.rule_type+"-";
		result += (StringUtils.isBlank(this.extension1)?"":this.extension1.trim())+"-";
		result += (StringUtils.isBlank(this.extension2)?"":this.extension2.trim())+"-";
		result += (StringUtils.isBlank(this.extension3)?"":this.extension3.trim())+"-";
		result += (StringUtils.isBlank(this.extension4)?"":this.extension4.trim())+"-";
		result += (StringUtils.isBlank(this.extension5)?"":this.extension5.trim())+"-";
		result += (StringUtils.isBlank(this.extension6)?"":this.extension6.trim())+"-";
		result += (StringUtils.isBlank(this.extension7)?"":this.extension7.trim())+"-";
		result += (StringUtils.isBlank(this.extension8)?"":this.extension8.trim())+"-";
		result += (StringUtils.isBlank(this.extension9)?"":this.extension9.trim())+"-";
		result += (StringUtils.isBlank(this.extension10)?"":this.extension10.trim())+"-";
		result += (StringUtils.isBlank(this.extension11)?"":this.extension11.trim())+"-";
		result += (StringUtils.isBlank(this.extension12)?"":this.extension12.trim())+"-";
		result += (StringUtils.isBlank(this.extension13)?"":this.extension13.trim())+"-";
		result += (StringUtils.isBlank(this.extension14)?"":this.extension14.trim())+"-";
		result += (StringUtils.isBlank(this.extension15)?"":this.extension15.trim())+"-";
		result += (StringUtils.isBlank(this.extension16)?"":this.extension16.trim())+"-";
		result += (StringUtils.isBlank(this.extension17)?"":this.extension17.trim())+"-";
		result += (StringUtils.isBlank(this.extension18)?"":this.extension18.trim())+"-";
		result += (StringUtils.isBlank(this.extension19)?"":this.extension19.trim())+"-";
		result += (StringUtils.isBlank(this.extension20)?"":this.extension20.trim());
		return result;
	}

	public int getRule_type() {
		return rule_type;
	}

	public void setRule_type(int rule_type) {
		this.rule_type = rule_type;
	}

	public String getExtension1() {
		return extension1;
	}

	public void setExtension1(String extension1) {
		this.extension1 = extension1;
	}

	public String getExtension2() {
		return extension2;
	}

	public void setExtension2(String extension2) {
		this.extension2 = extension2;
	}

	public String getExtension3() {
		return extension3;
	}

	public void setExtension3(String extension3) {
		this.extension3 = extension3;
	}

	public String getExtension4() {
		return extension4;
	}

	public void setExtension4(String extension4) {
		this.extension4 = extension4;
	}

	public String getExtension5() {
		return extension5;
	}

	public void setExtension5(String extension5) {
		this.extension5 = extension5;
	}

	public String getExtension6() {
		return extension6;
	}

	public void setExtension6(String extension6) {
		this.extension6 = extension6;
	}

	public String getExtension7() {
		return extension7;
	}

	public void setExtension7(String extension7) {
		this.extension7 = extension7;
	}

	public String getExtension8() {
		return extension8;
	}

	public void setExtension8(String extension8) {
		this.extension8 = extension8;
	}

	public String getExtension9() {
		return extension9;
	}

	public void setExtension9(String extension9) {
		this.extension9 = extension9;
	}

	public String getExtension10() {
		return extension10;
	}

	public void setExtension10(String extension10) {
		this.extension10 = extension10;
	}

	public String getExtension11() {
		return extension11;
	}

	public void setExtension11(String extension11) {
		this.extension11 = extension11;
	}

	public String getExtension12() {
		return extension12;
	}

	public void setExtension12(String extension12) {
		this.extension12 = extension12;
	}

	public String getExtension13() {
		return extension13;
	}

	public void setExtension13(String extension13) {
		this.extension13 = extension13;
	}

	public String getExtension14() {
		return extension14;
	}

	public void setExtension14(String extension14) {
		this.extension14 = extension14;
	}

	public String getExtension15() {
		return extension15;
	}

	public void setExtension15(String extension15) {
		this.extension15 = extension15;
	}

	public String getExtension16() {
		return extension16;
	}

	public void setExtension16(String extension16) {
		this.extension16 = extension16;
	}

	public String getExtension17() {
		return extension17;
	}

	public void setExtension17(String extension17) {
		this.extension17 = extension17;
	}

	public String getExtension18() {
		return extension18;
	}

	public void setExtension18(String extension18) {
		this.extension18 = extension18;
	}

	public String getExtension19() {
		return extension19;
	}

	public void setExtension19(String extension19) {
		this.extension19 = extension19;
	}

	public String getExtension20() {
		return extension20;
	}

	public void setExtension20(String extension20) {
		this.extension20 = extension20;
	}
	
}

package com.jiuzhou.plat.bean;
/**
* @author xingmh
* @version 创建时间：2018年10月18日 上午10:18:30
* modbus_tcp规则
*/

public class AuditRuleModbusTcp implements AuditRuleBase {
	
	public static final int MODE_READ = 1;
	public static final int MODE_WRITE = 2;
	public static final int MODE_READ_WRITE = 3;
	
	private int 	whitelistId;		//白名单id
	private int 	dport;				//目的端口
	private String	protocol;			//协议类型
	private int 	functionCode;		//功能码
	private int  	mode;				//读写类型, 1：读，2：写，3：读写
	private int 	readStart;			//读起始地址
	private int 	readLength;			//读地址长度
	private int 	wirteStart;			//写起始地址
	private int 	writeLength;		//写地址长度
	private Integer[] writeValues = new Integer[0];		//写值列表
	
	public AuditRuleModbusTcp() {
	}

	public int getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(int functionCode) {
		this.functionCode = functionCode;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getReadStart() {
		return readStart;
	}

	public void setReadStart(int readStart) {
		this.readStart = readStart;
	}

	public int getReadLength() {
		return readLength;
	}

	public void setReadLength(int readLength) {
		this.readLength = readLength;
	}

	public int getWirteStart() {
		return wirteStart;
	}

	public void setWirteStart(int wirteStart) {
		this.wirteStart = wirteStart;
	}

	public int getWriteLength() {
		return writeLength;
	}

	public void setWriteLength(int writeLength) {
		this.writeLength = writeLength;
	}

	public int getDport() {
		return dport;
	}

	public void setDport(int dport) {
		this.dport = dport;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Integer[] getWriteValues() {
		return writeValues;
	}

	public void setWriteValues(Integer[] writeValues) {
		this.writeValues = writeValues;
	}
	
	public String toLodDescription () {
		StringBuilder builder = new StringBuilder();
		builder
		.append("[白名单ID：")
		.append(this.whitelistId)
		.append("]")
		.append("[功能码：")
		.append(this.functionCode)
		.append("]")
		.append("[读起始地址：")
		.append(this.readStart)
		.append("]")
		.append("[读地址长度：")
		.append(this.readLength)
		.append("]")
		.append("[写起始地址：")
		.append(this.wirteStart)
		.append("]")
		.append("[写地址长度：")
		.append(this.writeLength)
		.append("]")
		.append("[写入值：");
		
		if (this.writeValues != null) {
			for (Integer integer : writeValues) {
				builder.append(integer).append(" ");
			}
		}
		builder.append("]");
		
		return builder.toString();
	}

	public int getWhitelistId() {
		return whitelistId;
	}

	public void setWhitelistId(int whitelistId) {
		this.whitelistId = whitelistId;
	}

	
}


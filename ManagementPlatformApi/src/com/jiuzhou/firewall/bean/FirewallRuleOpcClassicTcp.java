package com.jiuzhou.firewall.bean;
/**
* @author xingmh
* @version 创建时间：2019年1月29日 下午2:26:17
* 类说明
*/
public class FirewallRuleOpcClassicTcp extends FirewallStrategy {
	
	/**
	 * 读写控制:OFF
	 */
	public static final String SESSION_OFF = "0";
	/**
	 * 读写控制:ON
	 */
	public static final String SESSION_ON = "1";
	
	
	/**
	 * 合理性检查:否
	 */
	public static final String INSPECT_OFF = "0";
	/**
	 * 合理性检查:是
	 */
	public static final String INSPECT_ON = "1";
	
	
	/**
	 * 帧检查：是
	 */
	public static final String ZCHECK_OFF = "0";
	/**
	 * 帧检查：否
	 */
	public static final String ZCHECK_ON = "1";
			
	

	private int id;
	private String session;    //读写控制0:OFF,1:ON
	private String inspect;    //合理性检查1：是，0：否
	private String tos;     //超时时间
	private String zcheck;     //帧检查 0：否，1：是
	private String uuid;
	private String opnum;
	
	public FirewallRuleOpcClassicTcp() {
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
				+ "[读写控制："+this.getSessionName(this.getSession())+"]\n"
				+ "[合理性检查："+this.getInspectName(this.getInspect())+"]\n"
				+ "[连接T/O："+this.getTos()+"]\n"
				+ "[帧检查："+this.getZcheckName(this.getZcheck())+"]\n"
				+ "[uuid："+this.getUuid()+"]\n"
				+ "[opnum："+this.getOpnum()+"]\n";
	}

	private String getSessionName(String session){
		if (session == null) {
			return "未知";
		}
		switch (session) {
			case SESSION_ON:
				return "ON";
			case SESSION_OFF:
				return "OFF";
			default:
				return "未知";
		}
	}
	
	private String getInspectName(String inspect){
		if (inspect == null) {
			return "未知";
		}
		switch (inspect) {
			case INSPECT_ON:
				return "是";
			case INSPECT_OFF:
				return "否";
			default:
				return "未知";
		}
	}
	
	private String getZcheckName(String zcheck){
		if (zcheck == null) {
			return "未知";
		}
		switch (zcheck) {
			case ZCHECK_ON:
				return "是";
			case ZCHECK_OFF:
				return "否";
			default:
				return "未知";
		}
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getInspect() {
		return inspect;
	}

	public void setInspect(String inspect) {
		this.inspect = inspect;
	}

	public String getTos() {
		return tos;
	}

	public void setTos(String tos) {
		this.tos = tos;
	}

	public String getZcheck() {
		return zcheck;
	}

	public void setZcheck(String zcheck) {
		this.zcheck = zcheck;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOpnum() {
		return opnum;
	}

	public void setOpnum(String opnum) {
		this.opnum = opnum;
	}

	
}

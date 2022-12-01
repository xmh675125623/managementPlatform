package com.jiuzhou.firewall.utils;

import org.apache.commons.lang.StringUtils;

import com.jiuzhou.firewall.bean.FirewallStrategy;
import com.jiuzhou.firewall.bean.StudyRuleItem;
import com.jiuzhou.plat.bean.S7FunctionCode;

/**
* @author xingmh
* @version 创建时间：2020年9月21日 上午10:34:06
* 类说明
*/
public class FirewallStudyRuleAnaylsisUtil{
	
	public static StudyRuleItem anaylsisRule(String ruleStr){
		String ruleStr_ = ruleStr + " ";
		
		//丢弃一些私有协议
		if (ruleStr_.indexOf("-s 169.254") >= 0 || 
				ruleStr_.indexOf("-d 169.254") >= 0 || 
				ruleStr_.indexOf("-s 127.0.0.1") >= 0 || 
				ruleStr_.indexOf("-d 127.0.0.1") >= 0) {
			return null;
		}
		
		if (ruleStr_.indexOf("SMOD=") > 0) {
			String mod = ruleStr_.substring(ruleStr_.indexOf("SMOD=")+5, ruleStr_.indexOf("SMOD=")+6);
			
			StudyRuleItem ruleItem = new StudyRuleItem();
			
			if (!"3".equals(mod) && !"4".equals(mod) && !"5".equals(mod) && !"6".equals(mod) && !"7".equals(mod)) {
				return null;
			}
			
			
			anaylsisCustomRule(ruleStr_, ruleItem);
			if ("4".equals(mod)) {
				anaylsisModbusTcpRule(ruleStr_, ruleItem);
			} else if ("5".equals(mod)) {
				anaylsisOpcRule(ruleStr_, ruleItem);
			} else if ("6".equals(mod)) {
				anaylsisIEC104Rule(ruleStr_, ruleItem);
			} else if ("7".equals(mod)) {
				anaylsisS7Rule(ruleStr_, ruleItem);
			} 
			
			return ruleItem;
			
		} else {
			return null;
		}
	}
	
	/**
	 * 解析普通规则部分
	 * @param ruleStr
	 * @param ruleItem
	 */
	private static void anaylsisCustomRule(String ruleStr, StudyRuleItem ruleItem) {
		String ruleStr_ = ruleStr;
		
		ruleItem.setRule_type(FirewallStrategy.RULE_TYPE_CUSTOM);
		
		//源IP
		String sip = null;
		if (ruleStr_.indexOf("-s ") > 0) {
			sip = ruleStr_.substring(ruleStr_.indexOf("-s ")+3, ruleStr_.indexOf(" ", ruleStr_.indexOf("-s ")+3));
		}
		
		//目的IP
		String dip = null;
		if (ruleStr_.indexOf("-d ") > 0) {
			dip = ruleStr_.substring(ruleStr_.indexOf("-d ")+3, ruleStr_.indexOf(" ", ruleStr_.indexOf("-d ")+3));
		}
		
		//协议类型
		String protocol = null;
		if (ruleStr_.indexOf("-p ") > 0) {
			protocol = ruleStr_.substring(ruleStr_.indexOf("-p ")+3, ruleStr_.indexOf(" ", ruleStr_.indexOf("-p ")+3));
		}
		
		//源端口
		String sport = null;
		if (ruleStr_.indexOf("--sport ") > 0) {
			sport = ruleStr_.substring(ruleStr_.indexOf("--sport ")+8, ruleStr_.indexOf(" ", ruleStr_.indexOf("--sport ")+8));
		}
		
		
		//目的端口
		String dport = null;
		if (ruleStr_.indexOf("--dport ") > 0) {
			dport = ruleStr_.substring(ruleStr_.indexOf("--dport ")+8, ruleStr_.indexOf(" ", ruleStr_.indexOf("--dport ")+8));
		}
		
		ruleItem.setSip(sip);
		ruleItem.setDip(dip);
		ruleItem.setProtocol(protocol);
		ruleItem.setSport(sport);
		ruleItem.setDport(dport);
		
	}
	
	/**
	 * 解析modbustcp协议
	 * @param ruleStr
	 * @param ruleItem
	 */
	private static void anaylsisModbusTcpRule(String ruleStr, StudyRuleItem ruleItem) {
		
		String ruleStr_ = ruleStr;
		
		ruleItem.setRule_type(FirewallStrategy.RULE_TYPE_MODBUS_TCP);
		
		//单元id
		String unitId = null;
		if (ruleStr_.indexOf("-ui ") > 0) {
			unitId = ruleStr_.substring(ruleStr_.indexOf("-ui ")+4, ruleStr_.indexOf(" ", ruleStr_.indexOf("-ui ")+4));
		}
		ruleItem.setExtension1(unitId);
		
		//功能码
		String functionCode = null;
		if (ruleStr_.indexOf("-fc ") > 0) {
			functionCode = ruleStr_.substring(ruleStr_.indexOf("-fc ")+4, ruleStr_.indexOf(" ", ruleStr_.indexOf("-fc ")+4));
		}
		ruleItem.setExtension2(functionCode);
		
		//读起始地址
		String readStart = null;
		if (ruleStr_.indexOf("-ra ") > 0) {
			readStart = ruleStr_.substring(ruleStr_.indexOf("-ra ")+4, ruleStr_.indexOf(" ", ruleStr_.indexOf("-ra ")+4));
		}
		ruleItem.setExtension3(readStart);
		
		//读地址长度
		String readLength = null;
		if (ruleStr_.indexOf("-rc ") > 0) {
			readLength = ruleStr_.substring(ruleStr_.indexOf("-rc ")+4, ruleStr_.indexOf(" ", ruleStr_.indexOf("-rc ")+4));
		}
		ruleItem.setExtension4(readLength);
		
		//写起始地址
		String writeStart = null;
		if (ruleStr_.indexOf("-wa ") > 0) {
			writeStart = ruleStr_.substring(ruleStr_.indexOf("-wa ")+4, ruleStr_.indexOf(" ", ruleStr_.indexOf("-wa ")+4));
		}
		ruleItem.setExtension5(writeStart);
		
		//写地址长度
		String writelength = null;
		if (ruleStr_.indexOf("-wc ") > 0) {
			writelength = ruleStr_.substring(ruleStr_.indexOf("-wc ")+4, ruleStr_.indexOf(" ", ruleStr_.indexOf("-wc ")+4));
		}
		ruleItem.setExtension6(writelength);
		
		//写入值
		String writeValue = null;
		if (ruleStr_.indexOf("-vl ") > 0) {
			if (ruleStr_.indexOf(" ", ruleStr_.indexOf("-vl ")+4) > 0) {
				writeValue = ruleStr_.substring(ruleStr_.indexOf("-vl ")+4, ruleStr_.indexOf(" ", ruleStr_.indexOf("-vl ")+4));
			}
		}
		ruleItem.setExtension7(writeValue);
		
	}
	
	/**
	 * 解析iec104协议
	 * @param ruleStr
	 * @param ruleItem
	 */
	private static void anaylsisIEC104Rule(String ruleStr, StudyRuleItem ruleItem) {
		
		String ruleStr_ = ruleStr;
		
		ruleItem.setRule_type(FirewallStrategy.RULE_TYPE_IEC104);
		
		//type
		String type = null;
		if (ruleStr_.indexOf("-type ") > 0) {
			type = ruleStr_.substring(ruleStr_.indexOf("-type ")+6, ruleStr_.indexOf(" ", ruleStr_.indexOf("-type ")+6));
		}
		ruleItem.setExtension1(type);
		
		String cot = null;
		if (ruleStr_.indexOf("-cot ") > 0) {
			cot = ruleStr_.substring(ruleStr_.indexOf("-cot ")+5, ruleStr_.indexOf(" ", ruleStr_.indexOf("-cot ")+5));
		}
		ruleItem.setExtension2(cot);
		
		String coa = null;
		if (ruleStr_.indexOf("-coa ") > 0) {
			coa = ruleStr_.substring(ruleStr_.indexOf("-coa ")+5, ruleStr_.indexOf(" ", ruleStr_.indexOf("-coa ")+5));
		}
		ruleItem.setExtension3(coa);
		
		String ioa = null;
		if (ruleStr_.indexOf("-ioa ") > 0) {
			ioa = ruleStr_.substring(ruleStr_.indexOf("-ioa ")+5, ruleStr_.indexOf(" ", ruleStr_.indexOf("-ioa ")+5));
		}
		ruleItem.setExtension4(ioa);
		
		String ioac = null;
		if (ruleStr_.indexOf("-ioac ") > 0) {
			ioac = ruleStr_.substring(ruleStr_.indexOf("-ioac ")+6, ruleStr_.indexOf(" ", ruleStr_.indexOf("-ioac ")+6));
		}
		ruleItem.setExtension5(ioac);
		
	}
	
	/**
	 * 解析s7协议
	 * @param ruleStr
	 * @param ruleItem
	 */
	private static void anaylsisS7Rule(String ruleStr, StudyRuleItem ruleItem) {
		
		String ruleStr_ = ruleStr;
		
		ruleItem.setRule_type(FirewallStrategy.RULE_TYPE_S7);
		
		//
		String s7_rosctr = null;
		if (ruleStr_.indexOf("--s7-rosctr ") > 0) {
			s7_rosctr = ruleStr_.substring(ruleStr_.indexOf("--s7-rosctr ")+12, ruleStr_.indexOf(" ", ruleStr_.indexOf("--s7-rosctr ")+12));
		}
		
		if (StringUtils.isNotBlank(s7_rosctr)) {
			int s7_rosctrInt = Integer.parseInt(s7_rosctr);
			
			if (hexStringToInt("0x01") == s7_rosctrInt) {
				ruleItem.setExtension1(S7FunctionCode.PDU_TYPE_JOB);
			} else if (hexStringToInt("0x07") == s7_rosctrInt) {
				ruleItem.setExtension1(S7FunctionCode.PDU_TYPE_USERDATA);
			}
		}
		
		String s7_function = null;
		if (ruleStr_.indexOf("--s7-function ") > 0) {
			s7_function = ruleStr_.substring(ruleStr_.indexOf("--s7-function ")+14, ruleStr_.indexOf(" ", ruleStr_.indexOf("--s7-function ")+14));
		}
		ruleItem.setExtension2(s7_function);
		
		String s7_type = null;
		if (ruleStr_.indexOf("--s7-type ") > 0) {
			s7_type = ruleStr_.substring(ruleStr_.indexOf("--s7-type ")+10, ruleStr_.indexOf(" ", ruleStr_.indexOf("--s7-type ")+10));
		}
		ruleItem.setExtension3(s7_type);
		
		String s7_fgroup = null;
		if (ruleStr_.indexOf("--s7-fgroup ") > 0) {
			s7_fgroup = ruleStr_.substring(ruleStr_.indexOf("--s7-fgroup ")+12, ruleStr_.indexOf(" ", ruleStr_.indexOf("--s7-fgroup ")+12));
		}
		ruleItem.setExtension4(s7_fgroup);
		
		String s7_subf = null;
		if (ruleStr_.indexOf("--s7-subf ") > 0) {
			s7_subf = ruleStr_.substring(ruleStr_.indexOf("--s7-subf ")+10, ruleStr_.indexOf(" ", ruleStr_.indexOf("--s7-subf ")+10));
		}
		ruleItem.setExtension5(s7_subf);
		
		
	}
	
	/**
	 * 解析Opc协议
	 * @param ruleStr
	 * @param ruleItem
	 */
	private static void anaylsisOpcRule(String ruleStr, StudyRuleItem ruleItem) {
		
		String ruleStr_ = ruleStr;
		
		ruleItem.setRule_type(FirewallStrategy.RULE_TYPE_OPC_CLASSIC_TCP);
		
		//UUID
		String uuid = null;
		if (ruleStr_.indexOf("-uuid ") > 0) {
			uuid = ruleStr_.substring(ruleStr_.indexOf("-uuid ")+6, ruleStr_.indexOf(" ", ruleStr_.indexOf("-uuid ")+6));
		}
		ruleItem.setExtension1(uuid);
		
		//Opnum
		String opnum = null;
		if (ruleStr_.indexOf("-opnum ") > 0) {
			opnum = ruleStr_.substring(ruleStr_.indexOf("-opnum ")+7, ruleStr_.indexOf(" ", ruleStr_.indexOf("-opnum ")+7));
		}
		ruleItem.setExtension2(opnum);
		
	}
	
	private static int hexStringToInt (String hexString) {
		return Integer.parseInt(hexString.replaceAll("^0[x|X]", ""), 16);
	}
	
}

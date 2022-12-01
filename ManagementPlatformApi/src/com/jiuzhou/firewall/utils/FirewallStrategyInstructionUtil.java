package com.jiuzhou.firewall.utils;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.jiuzhou.firewall.bean.FirewallRuleCustom;
import com.jiuzhou.firewall.bean.FirewallRuleIEC104;
import com.jiuzhou.firewall.bean.FirewallRuleModbusTcp;
import com.jiuzhou.firewall.bean.FirewallRuleOpcClassicTcp;
import com.jiuzhou.firewall.bean.FirewallRuleS7;
import com.jiuzhou.firewall.bean.FirewallRuleTemp;
import com.jiuzhou.firewall.bean.FirewallStrategy;
import com.jiuzhou.firewall.bean.FirewallStrategyGroup;
import com.jiuzhou.plat.bean.S7FunctionCode;

/**
* @author xingmh
* @version 创建时间：2019年2月27日 下午3:51:19
* 类说明
*/
public class FirewallStrategyInstructionUtil {
	
	public static final String QUEUE_NUM = "1";
	
	/**
	 * 组织自定义规则指令
	 * @return
	 */
	public static String buildCustomInstruction(
			FirewallStrategyGroup group, 
			FirewallRuleCustom custom) {
		
		StringBuilder instruction = new StringBuilder();
		switch (custom.getProtocol()) {
		case "IP":
			//1---------------------------------
			//IP地址
			instruction.append(buildIp(group, false, null));
			//网口
			instruction.append(buildEth(custom, false));
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			break;
		case "TCP":
			//1---------------------------------
			//IP地址
			instruction
			.append(buildIp(group, false, null)).append(" -p tcp");
			//网口
			instruction.append(buildEth(custom, false));
			//端口
			if (StringUtils.isNotBlank(custom.getSport())) {
				instruction
				.append(" --sport " + custom.getSport());
			}
			if (StringUtils.isNotBlank(custom.getDport())) {
				instruction
				.append(" --dport " + custom.getDport());
			}
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			
			//4---------------------------------
			//IP地址
			instruction
			.append(buildIp(group, true, null)).append(" -p tcp");
			//网口
			instruction.append(buildEth(custom, true));
			//端口
			if (StringUtils.isNotBlank(custom.getDport())) {
				instruction
				.append(" --sport " + custom.getDport());
			}
			if (StringUtils.isNotBlank(custom.getSport())) {
				instruction
				.append(" --dport " + custom.getSport());
			}
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			break;
		case "UDP":
			//IP地址
			instruction.append(buildIp(group, false, null)).append(" -p udp");
			//网口
			instruction.append(buildEth(custom, false));
			//端口
			if (StringUtils.isNotBlank(custom.getSport())) {
				instruction
				.append(" --sport " + custom.getSport());
			}
			if (StringUtils.isNotBlank(custom.getDport())) {
				instruction
				.append(" --dport " + custom.getDport());
			}
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			break;
//		case "ICMP PING":
//			//1---------------------------------
//			//IP地址
//			instruction.append(buildIp(group, false, null));
//			//网口
//			instruction.append(buildEth(custom, false))
//			.append(" -p icmp --sport 8 --dport 0");
//			//时间
//			instruction.append(buildTime(custom));
//			instruction.append(";");
//			
//			//2---------------------------------
//			//IP地址
//			instruction.append(buildIp(group, true, null));
//			//网口
//			instruction.append(buildEth(custom, true))
//			.append(" -p icmp  --sport 0 --dport 0");
//			//时间
//			instruction.append(buildTime(custom));
//			instruction.append(";");
//			break;
		case "ICMP":
			//1---------------------------------
			//IP地址
			instruction.append(buildIp(group, false, null));
			//网口
			instruction.append(buildEth(custom, false))
			.append(" -p icmp");
			//端口
			if (StringUtils.isNotBlank(custom.getSport())) {
				instruction
				.append(" --sport " + custom.getSport());
			}
			if (StringUtils.isNotBlank(custom.getDport())) {
				instruction
				.append(" --dport " + custom.getDport());
			}
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			
//			//2---------------------------------
//			//IP地址
//			instruction.append(buildIp(group, true, null));
//			//网口
//			instruction.append(buildEth(custom, true))
//			.append(" -p icmp");
//			//时间
//			instruction.append(buildTime(custom));
//			instruction.append(";");
			break;
		default:
			break;
		}
		
		return instruction.toString();
	}
	
	/**
	 * 组织普通规则指令
	 * @return
	 */
	public static String buildCommonInstruction(
			FirewallStrategyGroup group, 
			FirewallRuleTemp custom) {
		StringBuilder instruction = new StringBuilder();
		switch (custom.getProtocol()) {
		case "TCP":
			//1---------------------------------
			//IP地址
			instruction
			.append(buildIp(group, false, null)).append(" -p tcp");
			//端口
			if (StringUtils.isNotBlank(custom.getSport())) {
				instruction
				.append(" --sport " + custom.getSport());
			}
			if (StringUtils.isNotBlank(custom.getDport())) {
				instruction
				.append(" --dport " + custom.getDport());
			}
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			
			//4---------------------------------
			//IP地址
			instruction
			.append(buildIp(group, true, null)).append(" -p tcp");
			//端口
			if (StringUtils.isNotBlank(custom.getDport())) {
				instruction
				.append(" --sport " + custom.getDport());
			}
			if (StringUtils.isNotBlank(custom.getSport())) {
				instruction
				.append(" --dport " + custom.getSport());
			}
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			break;
		case "UDP":
			//IP地址
			instruction.append(buildIp(group, false, null)).append(" -p udp");
			//端口
			if (StringUtils.isNotBlank(custom.getSport())) {
				instruction
				.append(" --sport " + custom.getSport());
			}
			if (StringUtils.isNotBlank(custom.getDport())) {
				instruction
				.append(" --dport " + custom.getDport());
			}
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			break;
		case "ICMP":
			//1---------------------------------
			//IP地址
			instruction.append(buildIp(group, false, null))
			.append(" -p icmp");
			//端口
			if (StringUtils.isNotBlank(custom.getSport())) {
				instruction
				.append(" --sport " + custom.getSport());
			}
			if (StringUtils.isNotBlank(custom.getDport())) {
				instruction
				.append(" --dport " + custom.getDport());
			}
			//时间
			instruction.append(buildTime(custom));
			instruction.append(";");
			
			//3---------------------------------
			//IP地址
//			instruction.append(buildIp(group, true, null))
//			.append(" -p icmp --sport 0 --dport 0");
//			//时间
//			instruction.append(buildTime(custom));
//			instruction.append(";");
			break;
		default:
			break;
		}
		
		return instruction.toString();
	}
	
	/**
	 * 组织特殊规则指令
	 * @return
	 */
	public static String buildSpecialInstruction(
			FirewallStrategyGroup group, 
			FirewallRuleTemp special) {
		StringBuffer str = new StringBuffer();
		
		StringBuffer strinsert = new StringBuffer();
		if (StringUtils.isNotBlank(group.getSource_asset_ip())) {
			strinsert.append(" -s " + group.getSource_asset_ip());
		}
		if (StringUtils.isNotBlank(group.getTarget_asset_ip())) {
			strinsert.append(" -d " + group.getTarget_asset_ip());
		}
		switch (special.getRule_name()) {
		case "802.3-Allow":
			str.append("0x41 -A CMP_RULE_ETH -p length -j ACCEPT; ");
			break;
		case "802_3-Deny(Log)":
			str.append("0x41 -A CMP_RULE_ETH -p length --nflog-prefix EBT_DENY --nflog-group 10 -j DROP; ");
			break;
		case "802_3-Deny(NoLog)":
			str.append("0x41 -A CMP_RULE_ETH -p length -j DROP; ");
			break;
		case "Allow All Ip(Log)":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert
					+ " -m limit --limit 256/minute --limit-burst 256 -j NFLOG --nflog-prefix \"ALLOW_ALL_IP\" --nflog-group 100; ");
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -j ACCEPT; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert
					+ " -m limit --limit 256/minute --limit-burst 256 -j NFLOG --nflog-prefix \"ALLOW_ALL_IP\" --nflog-group 100; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert + " -j ACCEPT; ");
			break;
		case "Allow All Ip(NoLog)":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -j ACCEPT; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert + " -j ACCEPT; ");
			break;
		case "Block All Ip(Log)":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert
					+ " -m limit --limit 256/minute --limit-burst 256 -j NFLOG --nflog-prefix \"DENY_ALL_IP\" --nflog-group 100; ");
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -j DROP; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert
					+ " -m limit --limit 256/minute --limit-burst 256 -j NFLOG --nflog-prefix \"DENY_ALL_IP\" --nflog-group 100; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert + " -j DROP; ");
			break;
		case "Block All Ip(NoLog)":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -j DROP; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert + " -j DROP; ");
			break;
		case "Block Ethernet Multicase":
			str.append(
					"0x41 -A CMP_RULE_ETH --pkttype-type multicast --nflog-prefix EBT_DENY --nflog-group 10 -j DROP; ");
			break;
		case "Block Ethernet Multicase(DenyNoLog)":
			str.append("0x41 -A CMP_RULE_ETH --pkttype-type multicast -j DROP; ");
			break;
		case "Block Fragmanted Ip Messages":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert
					+ " -m limit --limit 256/minute --limit-burst 256  -j NFLOG --nflog-prefix \"DENY_FRAG\" --nflog-group 100; ");
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -j DROP; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert
					+ " -m limit --limit 256/minute --limit-burst 256 -j NFLOG --nflog-prefix \"DENY_FRAG\" --nflog-group 100; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert + " -j DROP; ");
			break;
		case "Block Icmp Redirect Messages":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert
					+ " -p icmp --icmp-type redirect  -m limit --limit 256/minute --limit-burst 256  -j NFLOG --nflog-prefix DENY_ICMP_REDIRECT --nflog-group 100; ");
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -p icmp --icmp-type redirect -j DROP; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert
					+ " -p icmp --icmp-type redirect  -m limit --limit 256/minute --limit-burst 256  -j NFLOG --nflog-prefix DENY_ICMP_REDIRECT --nflog-group 100; ");
			str.append("0x40 -A CMP_RULE_EST" + strinsert + " -p icmp --icmp-type redirect -j DROP; ");
			break;
		case "CISCO DCP/VTP/DTP/PAGP/UDLD Allow":
			str.append("0x41 -A CMP_RULE_ETH -d 01:00:0c:cc:cc:cc -j ACCEPT; ");
			break;
		case "CISCO DCP/VTP/DTP/PAGP/UDLD Deny/NoLog":
			str.append("0x41 -A CMP_RULE_ETH -d 01:00:0c:cc:cc:cc -j DROP; ");
			break;
		case "Cisco ISL Allow":
			str.append(
					"0x41 -A CMP_RULE_ETH -d 01:00:0C:00:00:00/fd:ff:ff:ff:ff:00 -p length --802_3-sap 0xaa  -j ACCEPT; ");
			break;
		case "Cisco ISL Deny/NoLog":
			str.append(
					"0x41 -A CMP_RULE_ETH -d 01:00:0C:00:00:00/fd:ff:ff:ff:ff:00 -p length --802_3-sap 0xaa  -j DROP; ");
			break;
		case "IGMP Allow":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -p igmp -j ACCEPT; ");
			break;
		case "IGMP Deny/NoLog":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -p igmp -j DROP; ");
			break;
		case "Novell Netware Protocol Allow":
			str.append("0x41 -A CMP_RULE_ETH -p length --802_3-sap 0xe0 -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -p length --802_3-sap 0xff -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -p ipx -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -p length --802_3-type 0x8137 -j ACCEPT; ");
			break;
		case "Novell Netware Protocol Deny/NoLog":
			str.append("0x41 -A CMP_RULE_ETH -p length --802_3-sap 0xe0 -j DROP; ");
			str.append("0x41 -A CMP_RULE_ETH -p length --802_3-sap 0xff -j DROP; ");
			str.append("0x41 -A CMP_RULE_ETH -p ipx -j DROP; ");
			str.append("0x41 -A CMP_RULE_ETH -p length --802_3-type 0x8137 -j DROP; ");
			break;
		case "Rate Limit ARP - 1000pps":
			str.append("0x41 -A CMP_RULE_ETH -p arp --limit 1000/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -p arp -j DROP; ");
			break;
		case "Rate Limit ARP - 500pps":
			str.append("0x41 -A CMP_RULE_ETH -p arp --limit 500/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -p arp -j DROP; ");
			break;
		case "Rate Limit ARP - 100pps":
			str.append("0x41 -A CMP_RULE_ETH -p arp --limit 100/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -p arp -j DROP; ");
			break;
		case "Rate Limit Ethernet Broadcasts - 1000pps":
			str.append("0x41 -A CMP_RULE_ETH -d broadcast --limit 1000/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -d broadcast -j DROP; ");
			break;
		case "Rate Limit Ethernet Broadcasts - 500pps":
			str.append("0x41 -A CMP_RULE_ETH -d broadcast--limit 500/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -d broadcast -j DROP; ");
			break;
		case "Rate Limit Ethernet Broadcasts - 100pps":
			str.append("0x41 -A CMP_RULE_ETH -d broadcast--limit 100/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -d broadcast -j DROP; ");
			break;
		case "Rate Limit Ethernet Multicasts - 1000pps":
			str.append("0x41 -A CMP_RULE_ETH --pkttype-type multicast--limit 1000/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -j DROP; ");
			break;
		case "Rate Limit Ethernet Multicasts - 500pps":
			str.append("0x41 -A CMP_RULE_ETH --pkttype-type multicast--limit 500/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -j DROP; ");
			break;
		case "Rate Limit Ethernet Multicasts - 100pps":
			str.append("0x41 -A CMP_RULE_ETH--pkttype-type multicast--limit 100/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -j DROP; ");
			break;
		case "Rate Limit Ethernet Unicasts - 5000pps":
			str.append("0x41 -A CMP_RULE_ETH -d unicast --limit 5000/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -d unicast -j DROP; ");
			break;
		case "Rate Limit Ethernet Unicasts - 2000pps":
			str.append("0x41 -A CMP_RULE_ETH -d unicast --limit 2000/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -d unicast -j DROP; ");
			break;
		case "Rate Limit Ethernet Unicasts - 1000pps":
			str.append("0x41 -A CMP_RULE_ETH -d unicast --limit 1000/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -d unicast -j DROP; ");
			break;
		case "Rate Limit Ethernet Unicasts - 500pps":
			str.append("0x41 -A CMP_RULE_ETH -d unicast --limit 500/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -d unicast -j DROP; ");
			break;
		case "Rate Limit Ethernet Unicasts - 100pps":
			str.append("0x41 -A CMP_RULE_ETH -d unicast --limit 100/second -j ACCEPT; ");
			str.append("0x41 -A CMP_RULE_ETH -d unicast -j DROP; ");
			break;
		case "Spannint Tree Protocol - Allow":
			str.append("0x41 -A CMP_RULE_ETH -d 01:80:c2:00:00:00 -p LENGTH --802_3-sap 0x42  -j ACCEPT; ");
			break;
		case "Spanning Tree Protocol - Deny/NoLog":
			str.append("0x41 -A CMP_RULE_ETH -d 01:80:c2:00:00:00 -p LENGTH --802_3-sap 0x42  -j DROP; ");
			break;
		case "VRRP - Allow":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -p vrrp -j ACCEPT; ");
			break;
		case "VRRP - Deny/NoLog":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -p vrrp -j DROP; ");
			break;
		case "Ospf - Allow":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -p ospf -j ACCEPT; ");
			break;
		case "Ospf - Deny/NoLog":
			str.append("0x40 -A CMP_RULE_NEW" + strinsert + " -p vrrp -j DROP; ");
			break;
		case "UDP Flood Prevention":
			str.append("0x40 -I FORWARD 1" + strinsert + " -p udp -m hashlimit --hashlimit-above "
					+ special.getDport()
					+ "/second --hashlimit-mode srcip --hashlimit-name udp_flood -j DROP; ");
			break;
		case "ICMP Flood Prevention":
			str.append("0x40 -I FORWARD 1" + strinsert + " -p icmp -m hashlimit --hashlimit-above "
					+special.getDport()
					+ "/second --hashlimit-mode srcip --hashlimit-name ping_flood -j DROP; ");
			break;
		case "SYN Flood Prevention":
			str.append("0x40 -I FORWARD 1" + strinsert + " -p tcp ---syn -m hashlimit --hashlimit-above "
					+ special.getDport() + "/second --hashlimit-burst " + special.getSport()
					+ " --hashlimit-mode srcip --hashlimit-name syn_flood -j DROP; ");
			break;
		}
			
		return str.toString();
	}
	
	/**
	 * 组织OpcClassicTcp规则指令
	 * @return
	 */
	public static String buildOpcClassicTcpInstruction(
			FirewallStrategyGroup group, 
			FirewallRuleOpcClassicTcp opc) {
		StringBuilder instruction = new StringBuilder();
		
		instruction
		.append("0x30 opc ")
		.append("-s ").append(group.getTarget_asset_ip()).append(" ")
		.append("-d ").append(group.getSource_asset_ip()).append(" ")
		.append(buildTime(opc))
//		.append(" -rw-ctl ").append(opc.getSession())
		.append(" -uuid ").append(opc.getUuid())
		.append(" -opnum ").append(opc.getOpnum())
		.append(" --action ")
		.append("0")
		.append(" --log ")
		.append("0")
		.append(";");
		
//		//1-----------------------------------------------------------------
//		instruction
//		.append(buildIp(group, true, false, null))
//		//青岛石化版本去掉此端口行
//		.append("-p tcp ")
////		.append("-p tcp ")
//		.append("--syn ")
//		.append(buildTime(opc))
////		.append("-m conntrack --ctstate NEW -j ")
////		.append(opc.getRule_action() == sFirewallStrategy.RULE_ACTION_INTERCEPT ? "DROP":"ACCEPT")
//		.append("-m conntrack --ctstate NEW -j LSM_OPC")
//		.append(";");
////		.append("0x30 opc")
////		.append(" -c ").append(opc.getInspect())
////		.append(" -t ").append(opc.getTos())
////		.append(" -f ").append(opc.getZcheck())
////		.append(" -w ").append(opc.getSession())
////		.append(" -at ")
////		.append(opc.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
////		.append(" -lg ")
////		.append(opc.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
////		.append(";");
//		
//		//2-----------------------------------------------------------------
//		instruction
//		.append(buildIp(group, false, false, null))
//		//青岛石化版本去掉此端口行
//		.append("-p tcp ")
////		.append("-p tcp ")
//		.append(buildTime(opc))
////		.append("-j ")
////		.append(opc.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "DROP":"ACCEPT")
//		.append("-j LSM_OPC")
////		.append(";")
////		.append("0x30 opc")
//		.append(" --rw-ctl ").append(opc.getSession())
////		.append(" -t ").append(opc.getTos())
////		.append(" -f ").append(opc.getZcheck())
////		.append(" -w ").append(opc.getSession())
//		.append(" --action ")
//		.append(opc.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
//		.append(" --log ")
//		.append(opc.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
//		.append(";");
//		
//		//3-----------------------------------------------------------------
//		instruction
//		.append(buildIp(group, false, true, null))
//		//青岛石化版本去掉此端口行
//		.append("-p tcp ")
////		.append("-p tcp ")
//		.append(buildTime(opc))
////		.append("-j ")
////		.append(opc.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "DROP":"ACCEPT")
//		.append("-j LSM_OPC")
////		.append(";")
////		.append("0x30 opc")
//		.append(" --rw-ctl ").append(opc.getSession())
////		.append(" -t ").append(opc.getTos())
////		.append(" -f ").append(opc.getZcheck())
////		.append(" -w ").append(opc.getSession())
//		.append(" --action ")
//		.append(opc.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
//		.append(" --log ")
//		.append(opc.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
//		.append(";");
			
		return instruction.toString();
	}
	
	/**
	 * 组织ModbusTcp New规则指令
	 * @return
	 */
	public static String buildModbusTcpNewInstruction(
			FirewallStrategyGroup group,
			FirewallRuleModbusTcp modbusTcp) {
		StringBuilder instruction = new StringBuilder();
		//1-----------------------------------------------------------------
		instruction
		.append(buildIp(group, false, null))
		.append("-p tcp -dport 502 -j NF_QUEUE -queue-num ")
		.append(QUEUE_NUM)
//		.append("-p tcp -m multiport --dport 502 ")
//		.append("--syn ")
//		.append(buildTime(modbusTcp))
//		.append("-m conntrack --ctstate NEW -j my_LOG ")
//		.append("--log-prefix ")
////		.append("STRATEGY_ID_")
//		.append(modbusTcp.getStrategy_id())
////		.append("_")
////		.append((modbusTcp.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT?"DROP":"ACCEPT"))
//		.append(" --log-action ")
//		.append((modbusTcp.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT?"0":"1"))
//		.append(" --log-switch ")
//		.append((modbusTcp.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1"))
		.append(";");
		
		return instruction.toString();
	}
	
	/**
	 * 组织ModbusTcp规则指令
	 * @return
	 */
	public static String buildModbusTcpInstruction(
			FirewallStrategyGroup group,
			FirewallRuleModbusTcp modbusTcp) {
		
		StringBuilder instruction = new StringBuilder();
			//1-----------------------------------------------------------------
//		instruction
//		.append(buildIp(group, true, false, "RULE_EST_MODBUS"))
//		.append("-p tcp -m multiport --dport 502 ")
//		.append("--syn ")
//		.append(buildTime(modbusTcp))
//		.append("-m conntrack --ctstate NEW -j LSM_MODBUS");
//		if (StringUtils.isNotBlank(modbusTcp.getUnit_id())) {
//			instruction.append(" --ui ").append(modbusTcp.getUnit_id());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getFunction_code())) {
//			instruction.append(" --fc ").append(modbusTcp.getFunction_code());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getRead_start())) {
//			instruction.append(" --ra ").append(modbusTcp.getRead_start());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getRead_length())) {
//			instruction.append(" --rc ").append(modbusTcp.getRead_length());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getWrite_start())) {
//			instruction.append(" --wa ").append(modbusTcp.getWrite_start());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getWrite_length())) {
//			instruction.append(" --wc ").append(modbusTcp.getWrite_length());
//		}
//		
//		if (StringUtils.isNotBlank(modbusTcp.getWrite_values())) {
//			instruction.append(" --vl ").append(modbusTcp.getWrite_values());
//		}
//		instruction
//		.append(" --action ")
//		.append(modbusTcp.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
//		.append(" --log ")
//		.append(modbusTcp.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
//		.append(" --mpolicy 0 ")
//		.append(" --prefix ")
//		.append(modbusTcp.getStrategy_id())
//		.append(modbusTcp.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "_DROP":"_ACCEPT")
//		.append(";");
		
		//2-----------------------------------------------------------------
		instruction
//		.append(buildIp(group, false, false, "RULE_EST_MODBUS"))
////		.append("-p tcp -m multiport --dport 502 ")
//		.append("-p tcp ")
		.append("0x30 modbus ")
		.append("-s ").append(group.getSource_asset_ip()).append(" ")
		.append("-d ").append(group.getTarget_asset_ip()).append(" ")
		.append(buildTime(modbusTcp));
//		.append("-j LSM_MODBUS");
		if (StringUtils.isNotBlank(modbusTcp.getUnit_id())) {
			instruction.append(" -ui ").append(modbusTcp.getUnit_id());
		}
		if (StringUtils.isNotBlank(modbusTcp.getFunction_code())) {
			instruction.append(" -fc ").append(modbusTcp.getFunction_code());
		}
		if (StringUtils.isNotBlank(modbusTcp.getRead_start())) {
			instruction.append(" -ra ").append(modbusTcp.getRead_start());
		}
		if (StringUtils.isNotBlank(modbusTcp.getRead_length())) {
			instruction.append(" -rc ").append(modbusTcp.getRead_length());
		}
		if (StringUtils.isNotBlank(modbusTcp.getWrite_start())) {
			instruction.append(" -wa ").append(modbusTcp.getWrite_start());
		}
		if (StringUtils.isNotBlank(modbusTcp.getWrite_length())) {
			if (!"5".equals(modbusTcp.getFunction_code()) && !"6".equals(modbusTcp.getFunction_code())) {
				instruction.append(" -wc ").append(modbusTcp.getWrite_length());
			}
			
		}
		if (StringUtils.isNotBlank(modbusTcp.getWrite_values())) {
			instruction.append(" -vl ").append(modbusTcp.getWrite_values());
		}
		instruction
		.append(" --action ")
//		.append(modbusTcp.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
		.append("0")
		.append(" --log ")
//		.append(modbusTcp.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
		.append("0")
		.append(" --mpolicy 0 ")
		.append(" --prefix ")
		.append(modbusTcp.getStrategy_id())
//		.append(modbusTcp.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "_DROP":"_ACCEPT")
		.append(";");
		
		//3-----------------------------------------------------------------
//		instruction.append(buildIp(group, false, true, "RULE_EST_MODBUS"))
////		.append("-p tcp -m multiport --sport 502 ")
//		.append("-p tcp ")
//		instruction.append("0x30 modbus ")
//		.append("-s ").append(group.getSource_asset_ip()).append(" ")
//		.append("-d ").append(group.getTarget_asset_ip()).append(" ")
//		.append(buildTime(modbusTcp));
////		.append("-j LSM_MODBUS");
//		if (StringUtils.isNotBlank(modbusTcp.getUnit_id())) {
//			instruction.append(" -ui ").append(modbusTcp.getUnit_id());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getFunction_code())) {
//			instruction.append(" -fc ").append(modbusTcp.getFunction_code());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getRead_start())) {
//			instruction.append(" -ra ").append(modbusTcp.getRead_start());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getRead_length())) {
//			instruction.append(" -rc ").append(modbusTcp.getRead_length());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getWrite_start())) {
//			instruction.append(" -wa ").append(modbusTcp.getWrite_start());
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getWrite_length())) {
//			if (!"5".equals(modbusTcp.getFunction_code()) && !"6".equals(modbusTcp.getFunction_code())) {
//				instruction.append(" -wc ").append(modbusTcp.getWrite_length());
//			}
//		}
//		if (StringUtils.isNotBlank(modbusTcp.getWrite_values())) {
//			instruction.append(" -vl ").append(modbusTcp.getWrite_values());
//		}
//		instruction
//		.append(" --action ")
////		.append(modbusTcp.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
//		.append("0")
//		.append(" --log ")
////		.append(modbusTcp.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
//		.append("0")
//		.append(" --mpolicy 0 ")
//		.append(" --prefix ")
//		.append(modbusTcp.getStrategy_id())
////		.append(modbusTcp.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "_DROP":"_ACCEPT")
//		.append(";");
		
		return instruction.toString();
	}
	
	/**
	 * 组织ModbusTcp New规则指令
	 * @return
	 */
	public static String buildIEC104NewInstruction(
			FirewallStrategyGroup group,
			FirewallRuleIEC104 iec104) {
		StringBuilder instruction = new StringBuilder();
		//1-----------------------------------------------------------------
		instruction
		.append(buildIp(group, false, null))
		.append("-p tcp -dport 2404 -j NF_QUEUE -queue-num ")
		.append(QUEUE_NUM)
//		.append("-p tcp -m multiport --dport 2404 ")
//		.append("--syn ")
//		.append(buildTime(iec104))
//		.append("-m conntrack --ctstate NEW -j my_LOG ")
//		.append("--log-prefix ")
////		.append("STRATEGY_ID_")
//		.append(iec104.getStrategy_id())
////		.append("_")
////		.append((iec104.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT?"DROP":"ACCEPT"))
//		.append(" --log-action ")
//		.append((iec104.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT?"0":"1"))
//		.append(" --log-switch ")
//		.append((iec104.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1"))
		.append(";");
		
		return instruction.toString();
	}
	
	/**
	 * 组织iec104规则指令
	 * @return
	 */
	public static String buildIIEC104Instruction(
			FirewallStrategyGroup group,
			FirewallRuleIEC104 iec104
			) {
		
		StringBuilder instruction = new StringBuilder();
		//1-----------------------------------------------------------------
//		instruction
//		.append(buildIp(group, true, false, "RULE_EST_IEC104"))
//		.append("-p tcp -m multiport --dport 2404 ")
//		.append("--syn ")
//		.append(buildTime(iec104))
//		.append("-m conntrack --ctstate NEW -j LSM_IEC104");
//		
//		if (StringUtils.isNotBlank(iec104.getType())) {
//			instruction.append(" --type ").append(iec104.getType());
//		}
//		if (StringUtils.isNotBlank(iec104.getCot())) {
//			instruction.append(" --cot ").append(iec104.getCot());
//		} else {
//			instruction.append(" --cot ").append("0");
//		}
//		if (StringUtils.isNotBlank(iec104.getCoa())) {
//			instruction.append(" --coa ").append(iec104.getCoa());
//		} else {
//			instruction.append(" --coa ").append("0");
//		}
//		if (StringUtils.isNotBlank(iec104.getIoa_start())) {
//			instruction.append(" --ioa ").append(iec104.getIoa_start());
//		} else {
//			instruction.append(" --ioa ").append("0");
//		}
//		if (StringUtils.isNotBlank(iec104.getIoa_length())) {
//			instruction.append(" --ioac ").append(iec104.getIoa_length());
//		} else {
//			instruction.append(" --ioac ").append("0");
//		}
//		
//		instruction
//		.append(" --action ")
//		.append(iec104.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
//		.append(" --log ")
//		.append(iec104.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
//		.append(" --mpolicy 0 ")
//		.append(" --prefix ")
//		.append(iec104.getStrategy_id())
//		.append(iec104.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "_DROP":"_ACCEPT")
//		.append(";");
		
		//2-----------------------------------------------------------------
		instruction
//		.append(buildIp(group, false, false, "RULE_EST_IEC104"))
////		.append("-p tcp -m multiport --dport 2404 ")
//		.append("-p tcp ")
		.append("0x30 iec104 ")
		.append("-s ").append(group.getSource_asset_ip()).append(" ")
		.append("-d ").append(group.getTarget_asset_ip()).append(" ")
		.append(buildTime(iec104));
//		.append("-j LSM_IEC104");
		if (StringUtils.isNotBlank(iec104.getType())) {
			instruction.append(" -type ").append(iec104.getType());
		}
		if (StringUtils.isNotBlank(iec104.getCot())) {
			instruction.append(" -cot ").append(iec104.getCot());
		}
		if (StringUtils.isNotBlank(iec104.getCoa())) {
			instruction.append(" -coa ").append(iec104.getCoa());
		}
		if (StringUtils.isNotBlank(iec104.getIoa_start())) {
			instruction.append(" -ioa ").append(iec104.getIoa_start());
		}
		if (StringUtils.isNotBlank(iec104.getIoa_length())) {
			instruction.append(" -ioac ").append(iec104.getIoa_length());
		}
		instruction
		.append(" --action ")
//		.append(iec104.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
		.append("0")
		.append(" --log ")
//		.append(iec104.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
		.append("0")
		.append(" --mpolicy 0 ")
		.append(" --prefix ")
		.append(iec104.getStrategy_id())
//		.append(iec104.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "_DROP":"_ACCEPT")
		.append(";");
		
		//3-----------------------------------------------------------------
//		instruction
////		.append(buildIp(group, false, true, "RULE_EST_IEC104"))
//////		.append("-p tcp -m multiport --sport 2404 ")
////		.append("-p tcp ")
//		.append("0x30 iec104 ")
//		.append("-s ").append(group.getTarget_asset_ip()).append(" ")
//		.append("-d ").append(group.getSource_asset_ip()).append(" ")
//		.append(buildTime(iec104));
////		.append("-j LSM_IEC104");
//		if (StringUtils.isNotBlank(iec104.getType())) {
//			instruction.append(" -type ").append(iec104.getType());
//		}
//		if (StringUtils.isNotBlank(iec104.getCot())) {
//			instruction.append(" -cot ").append(iec104.getCot());
//		} else {
//			instruction.append(" -cot ").append("0");
//		}
//		if (StringUtils.isNotBlank(iec104.getCoa())) {
//			instruction.append(" -coa ").append(iec104.getCoa());
//		} else {
//			instruction.append(" -coa ").append("0");
//		}
//		if (StringUtils.isNotBlank(iec104.getIoa_start())) {
//			instruction.append(" -ioa ").append(iec104.getIoa_start());
//		} else {
//			instruction.append(" -ioa ").append("0");
//		}
//		if (StringUtils.isNotBlank(iec104.getIoa_length())) {
//			instruction.append(" -ioac ").append(iec104.getIoa_length());
//		} else {
//			instruction.append(" -ioac ").append("0");
//		}
//		instruction
//		.append(" --action ")
////		.append(iec104.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
//		.append("0")
//		.append(" --log ")
////		.append(iec104.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
//		.append("0")
//		.append(" --mpolicy 0 ")
//		.append(" --prefix ")
//		.append(iec104.getStrategy_id())
////		.append(iec104.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "_DROP":"_ACCEPT")
//		.append(";");
		
		return instruction.toString();
	}
	
	/**
	 * 组织s7 New规则指令
	 * @return
	 */
	public static String buildS7NewInstruction(
			FirewallStrategyGroup group,
			FirewallRuleS7 s7) {
		StringBuilder instruction = new StringBuilder();
		instruction
		.append(buildIp(group, false, null))
		.append("-p tcp -m multiport --dport 102 ")
		.append("--syn ")
		.append(buildTime(s7))
		.append("-m conntrack --ctstate NEW -j my_LOG ")
		.append("--log-prefix ")
//		.append("STRATEGY_ID_")
		.append(s7.getStrategy_id())
//		.append("_")
//		.append((s7.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT?"DROP":"ACCEPT"))
		.append(" --log-action ")
		.append((s7.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT?"0":"1"))
		.append(" --log-switch ")
		.append((s7.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1"))
		.append(";");
		
		return instruction.toString();
	}
	
	/**
	 * 组织iec104规则指令
	 * @return
	 */
	public static String buildS7Instruction(
			FirewallStrategyGroup group,
			FirewallRuleS7 s7
			) {
		
		StringBuilder instruction = new StringBuilder();
		StringBuilder s7instruction = new StringBuilder();
		
		if (StringUtils.isNotBlank(s7.getPdu_type())) {
			
			s7instruction.append(" --s7-protoid ").append(hexStringToInt("0x32"));
			
			if (S7FunctionCode.PDU_TYPE_JOB.equals(s7.getPdu_type())) {
				s7instruction.append(" --s7-rosctr ").append(hexStringToInt("0x01"));
				if (StringUtils.isNotBlank(s7.getFunction_code())) {
					s7instruction.append(" --s7-function ").append(hexStringToInt(s7.getFunction_code()));
				}
				
			} else if (S7FunctionCode.PDU_TYPE_USERDATA.equals(s7.getPdu_type())) {
				s7instruction.append(" --s7-rosctr ").append(hexStringToInt("0x07"));
				if (StringUtils.isNotBlank(s7.getUser_data_type())) {
					s7instruction.append(" --s7-type ").append(hexStringToInt(s7.getUser_data_type()));
				}
				if (StringUtils.isNotBlank(s7.getFunction_group_code())) {
					s7instruction.append(" --s7-fgroup ").append(hexStringToInt(s7.getFunction_group_code()));
				}
				if (StringUtils.isNotBlank(s7.getSub_function_code())) {
					s7instruction.append(" --s7-subf ").append(hexStringToInt(s7.getSub_function_code()));
				}
			}
			
			
		}
		
		instruction
		.append("0x30 s7 ")
		.append("-s ").append(group.getTarget_asset_ip()).append(" ")
		.append("-d ").append(group.getSource_asset_ip()).append(" ")
		.append(buildTime(s7))
		.append(s7instruction)
		.append(";");
		
//		//2-----------------------------------------------------------------
//		instruction
//		.append(buildIp(group, false, false, "RULE_EST_S7"))
//		.append("-p tcp ")
//		.append(buildTime(s7))
//		.append("-j LSM_S7")
//		.append(s7instruction)
//		.append(" --s7-action ")
//		.append(s7.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
//		.append(" --s7-log ")
//		.append(s7.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
//		.append(" --s7-policy 0 ")
//		.append(" --s7-prefix ")
//		.append(s7.getStrategy_id())
////		.append(s7.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "_DROP":"_ACCEPT")
//		.append(";");
//		
//		//3-----------------------------------------------------------------
//		instruction.append(buildIp(group, false, true, "RULE_EST_S7"))
//		.append("-p tcp ")
//		.append(buildTime(s7))
//		.append("-j LSM_S7")
//		.append(s7instruction)
//		.append(" --s7-action ")
//		.append(s7.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "0":"1")
//		.append(" --s7-log ")
//		.append(s7.getLog_power() == FirewallStrategy.LOG_POWER_OFF ? "0":"1")
//		.append(" --s7-policy 0 ")
//		.append(" --s7-prefix ")
//		.append(s7.getStrategy_id())
////		.append(s7.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT ? "_DROP":"_ACCEPT")
//		.append(";");
		
		return instruction.toString();
	}
	
	private static int hexStringToInt (String hexString) {
		return Integer.parseInt(hexString.replaceAll("^0[x|X]", ""), 16);
	}
	
	/**
	 * 组织log指令
	 * @param strategy
	 * @return
	 */
	private static String buildLogAction(FirewallStrategy strategy) {
		StringBuilder logInstruction = new StringBuilder();
		logInstruction.
		append(" -j ACCEPT");
//		if (strategy.getLog_power() == FirewallStrategy.LOG_POWER_ON) {
//			logInstruction.append(" -j my_LOG ")
//			.append("--log-prefix ")
//			.append(strategy.getStrategy_id())
//			.append(" --log-action ")
//			.append((strategy.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT?"0":"1"));
//		} else {
//			logInstruction.
//			append((strategy.getRule_action() == FirewallStrategy.RULE_ACTION_INTERCEPT?" -j DROP":" -j ACCEPT"));
//		}
		return logInstruction.toString();
	}
	
	
	
	/**
	 * 组织IP指令
	 * @param group
	 * @param isNEW
	 * @param isOpposite 是否反向
	 * @return
	 */
	private static String buildIp(FirewallStrategyGroup group, boolean isOpposite, String strategyTableName) {
		StringBuilder instruction = new StringBuilder();
		instruction.append("0x20 0x201");

		if (isOpposite) {
			instruction
			.append(" -s ").append(group.getTarget_asset_ip())
			.append(" -d ").append(group.getSource_asset_ip());
		} else {
			instruction
			.append(" -s ").append(group.getSource_asset_ip())
			.append(" -d ").append(group.getTarget_asset_ip());
		}
		
		return instruction.toString();
	}
	
	private static String buildEth(FirewallStrategy strategy, boolean isOpposite) {
		StringBuilder instruction = new StringBuilder();
		
		String inputEthStr = " -i";
		String oppositeInputEthStr = " -o";
		
		String outputEthStr = " -o";
		String oppositeOutputEthStr = " -i";
		
		if (!isOpposite) {
			if (strategy.getInput_eth() > 0) {
				instruction.append(inputEthStr)
				.append(" ")
				.append(strategy.getInput_eth() - 1);
			}
			
			if (strategy.getOutput_eth() > 0) {
				instruction.append(outputEthStr)
				.append(" ")
				.append(strategy.getOutput_eth() - 1);
			}
		} else {
			if (strategy.getInput_eth() > 0) {
				instruction.append(oppositeInputEthStr)
				.append(" ")
				.append(strategy.getInput_eth() - 1);
			}
			
			if (strategy.getOutput_eth() > 0) {
				instruction.append(oppositeOutputEthStr)
				.append(" ")
				.append(strategy.getOutput_eth() - 1);
			}
		}
		
		return instruction.toString();
//		return "";
	}
	
	/**
	 * 组织时间指令
	 * @return
	 */
	private static String buildTime(FirewallStrategy strategy) {
		StringBuilder instruction = new StringBuilder();
		if (StringUtils.isBlank(strategy.getRelative_time()) 
				&& StringUtils.isBlank(strategy.getStart_time())
				&& StringUtils.isBlank(strategy.getEnd_time())) {
			return "";
		}
		
		//绝对时间
		if (strategy.getTime_type() == FirewallStrategy.TIME_TYPE_ABSOLUTE) {
			if (StringUtils.isNotBlank(strategy.getStart_time())) {
				instruction.append(" --datestart ")
				.append(strategy.getStart_time());
			}
			if (StringUtils.isNotBlank(strategy.getEnd_time())) {
				instruction.append(" --datestop ")
				.append(strategy.getEnd_time());
			}
			
		} else {//相对时间
			instruction
			.append(" --weekdays ")
			.append(
					strategy.getRelative_time()
					.replace("1", "Mon").replace("2", "Tue").replace("3", "Wed")
					.replace("4", "Thu").replace("5", "Fri").replace("6", "Sat")
					.replace("7", "Sun")
			).append(" ")
			.append("--timestart ").append(strategy.getStart_time()).append(" ")
			.append("--timestop ").append(strategy.getEnd_time());
		}
		
		return instruction.toString();
	}
	
	public static void main(String[] args) {
		File file = new File("d:/disktest");
		double used = ((file.getTotalSpace() - file.getFreeSpace()) / 1024.0 / 1024 / 1024);  
		double total = file.getTotalSpace() / 1024.0 / 1024 / 1024;  
		double free = file.getFreeSpace() / 1024 / 1024 / 1024.0;  
		System.out.println("UsedSpace: " + (used > 1024?((int)((100*used)/1024))/100.0 +"T" : ((int)(100*used))/100.0 + "G"));
		System.out.println("TotalSpace: " + (total > 1024?((int)((100*total)/1024))/100.0 +"T" : ((int)(100*total))/100.0 + "G"));
		System.out.println("FreeSpace: " + (free > 1024?((int)((100*free)/1024))/100.0 +"T" : ((int)(100*free))/100.0 + "G"));
	}
}

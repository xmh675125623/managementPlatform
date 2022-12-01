package com.jiuzhou.firewall.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jiuzhou.firewall.bean.FirewallAntiAttackDos;
import com.jiuzhou.firewall.bean.FirewallAntiAttackScan;
import com.jiuzhou.firewall.bean.FirewallDevice;
import com.jiuzhou.firewall.mapper.FirewallAntiAttackMapper;
import com.jiuzhou.firewall.mapper.FirewallDeviceMapper;
import com.jiuzhou.firewall.service.FirewallAntiAttackService;
import com.jiuzhou.firewall.service.FirewallDeviceService;
import com.jiuzhou.firewall.utils.TCPSocketUtil;
import com.jiuzhou.plat.bean.CommonResult;
import com.jiuzhou.plat.service.impl.ServiceBase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年1月23日 下午2:40:35
* 类说明
*/
@Service("FirewallAntiAttackService")
public class FirewallAntiAttackServiceImpl implements FirewallAntiAttackService {
	
	@Autowired
	private FirewallDeviceService firewallDeviceService;
	
	@Autowired
	private FirewallDeviceMapper firewallDeviceMapper;
	
	@Autowired
	private FirewallAntiAttackMapper firewallAntiAttackMapper;

	/**
	 * 获取抗攻击设置信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getInfo(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//获取设备列表用于页面下拉选择
		JSONArray deviceArray = firewallDeviceService.getForSelectTag();
		commonResult.put("deviceArray", deviceArray);
		
		FirewallDevice device = null;
		if (paramJson.has("deviceName")) {
			device = firewallDeviceMapper.getByName(paramJson.getString("deviceName"));
		}else if (deviceArray.size() > 0) {
			device = firewallDeviceMapper.getByName(deviceArray.getJSONObject(0).getString("deviceName"));
		}
		
		if (device == null) {
			commonResult.setStatus(true);
			return commonResult.toString();
		}
		
		//获取dos设置
		FirewallAntiAttackDos dos = firewallAntiAttackMapper.getDosByDeviceName(device.getDevice_name());
		
		//获取扫描设置
		FirewallAntiAttackScan scan = firewallAntiAttackMapper.getScanByDeviceName(device.getDevice_name());
		
		commonResult.put("device", device);
		commonResult.put("dos", dos);
		commonResult.put("scan", scan);
		commonResult.setStatus(true);
		return commonResult.toString();
	}

	/**
	 * 修改抗攻击设置信息
	 * @param paramJson
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public String update(JSONObject paramJson, HttpServletRequest request) throws Exception {
		CommonResult commonResult = new CommonResult(false, "");
		
		//设备名称
		if (!paramJson.has("deviceName")) {
			commonResult.setErrorMsg("缺少参数deviceName");
			return commonResult.toString();
		}
		String deviceName = paramJson.getString("deviceName");
		
		FirewallDevice device = firewallDeviceMapper.getByName(deviceName);
		if (device == null) {
			commonResult.setErrorMsg("设备不存在");
			return commonResult.toString();
		}
		
		//是否启用全局抗DOS
		if (paramJson.has("antiAttackDos")) {
			device.setAnti_attack_dos(paramJson.getInt("antiAttackDos"));
		}
		
		//是否启用全局扫描
		if (paramJson.has("antiAttackScan")) {
			device.setAnti_attack_scan(paramJson.getInt("antiAttackScan"));
		}
		
		firewallDeviceMapper.update(device);
		
		//获取dos设置
		FirewallAntiAttackDos dos = firewallAntiAttackMapper.getDosByDeviceName(deviceName);
		if (dos == null) {
			commonResult.setErrorMsg("记录不存在，请刷新页面重试");
			return commonResult.toString();
		}
		
		//tcp_syn_flood
		if (paramJson.has("tcpSynFlood")) {
			dos.setTcp_syn_flood(paramJson.getInt("tcpSynFlood"));
		}
		
		//win_nuke
		if (paramJson.has("winNuke")) {
			dos.setWin_nuke(paramJson.getInt("winNuke"));
		}
		
		//smurf
		if (paramJson.has("smurf")) {
			dos.setSmurf(paramJson.getInt("smurf"));
		}
		
		//land
		if (paramJson.has("land")) {
			dos.setLand(paramJson.getInt("land"));
		}
		
		//udp_flood
		if (paramJson.has("udpFlood")) {
			dos.setUdp_flood(paramJson.getInt("udpFlood"));
		}
		
		//icmp_flood
		if (paramJson.has("icmpFlood")) {
			dos.setIcmp_flood(paramJson.getInt("icmpFlood"));
		}
		
		//teardrop
		if (paramJson.has("teardrop")) {
			dos.setTeardrop(paramJson.getInt("teardrop"));
		}
		
		firewallAntiAttackMapper.updateDos(dos);
		
		//获取扫描设置
		FirewallAntiAttackScan scan = firewallAntiAttackMapper.getScanByDeviceName(deviceName);
		if (scan == null) {
			commonResult.setErrorMsg("记录不存在，请刷新页面重试");
			return commonResult.toString();
		}
		
		//tcp_scan
		if (paramJson.has("tcpScan")) {
			scan.setTcp_scan(paramJson.getInt("tcpScan"));
		}
		
		//udp_scan
		if (paramJson.has("udpScan")) {
			scan.setUdp_scan(paramJson.getInt("udpScan"));
		}
		firewallAntiAttackMapper.updateScan(scan);
		
		//设备下发
		String errorMsg = this.giveAntiToDevice(device, dos, scan);
		if  (StringUtils.isNotBlank(errorMsg)) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			commonResult.setErrorMsg(errorMsg);
			return commonResult.toString();
		}
		
		String logDesc = "[设备名："+dos.getDevice_name()+"]\n"
				+ "[启用全局抗DOS："+this.getStringVal(device.getAnti_attack_dos())+"]\n"
				+ "[tcp syn flood："+this.getStringVal(dos.getTcp_syn_flood())+"]\n"
				+ "[win nuke："+this.getStringVal(dos.getWin_nuke())+"]\n"
				+ "[smurf："+this.getStringVal(dos.getSmurf())+"]\n"
				+ "[land："+this.getStringVal(dos.getLand())+"]\n"
				+ "[udp flood："+this.getStringVal(dos.getUdp_flood())+"]\n"
				+ "[icmp flood："+this.getStringVal(dos.getIcmp_flood())+"]\n"
				+ "[teardrop："+this.getStringVal(dos.getTeardrop())+"]\n"
				+ "[启用全局扫描："+this.getStringVal(device.getAnti_attack_scan())+"]\n"
				+ "[tcp扫描："+this.getStringVal(scan.getTcp_scan())+"]\n"
				+ "[udp扫描："+this.getStringVal(scan.getUdp_scan())+"]";
		request.setAttribute(ServiceBase.CURRENT_LOG_DESCRIPTION, logDesc);
		
		commonResult.setStatus(true);
		return commonResult.toString();
	}
	
	private String getStringVal(int val){
		if (val != 1) {
			return "关";
		}
		return "开";
	}
	
	/**
	 * 设备下发
	 * @param device
	 * @param dos
	 * @param scan
	 * @return
	 */
	private String giveAntiToDevice(FirewallDevice device,
			FirewallAntiAttackDos dos,
			FirewallAntiAttackScan scan) {
		
		String instruction = "0x20 0x213 flood flush;";
		
		if (device.getAnti_attack_dos() == 1) {
			if (dos.getTcp_syn_flood() == 1) {
				instruction += "0x20 0x213 flood -A ATTACK_FLOOD_LIST -p tcp --syn -m limit --limit 10/s --limit-burst 4 -j RETURN;";
			}
			if (dos.getIcmp_flood() == 1) {
				instruction += "0x20 0x213 flood -A  ATTACK_FLOOD_LIST -p icmp --icmp-type echo-request -m limit --limit 10/m -j RETURN;";
			}
			if (dos.getUdp_flood() == 1) {
				instruction += "0x20 0x213 flood -A  ATTACK_FLOOD_LIST -p udp -m limit --limit 10/m -j RETURN;";
			}
			if (dos.getLand() == 1 || dos.getTeardrop() == 1 ) {
				instruction += "0x20 0x213 attack 1;";
			} else {
				instruction += "0x20 0x213 attack 0;";
			}
		} else {
			instruction += "0x20 0x213 attack 0;";
		}
		
		instruction += "0x20 0x213 flood last;";
		
		Map<String, String> maps = TCPSocketUtil.getNetworkSocketConfig();
		maps.put("mip", device.getIp_address());
		
		//发送指令
		return TCPSocketUtil.sendNetworkInstruction(instruction, maps);
	}

}

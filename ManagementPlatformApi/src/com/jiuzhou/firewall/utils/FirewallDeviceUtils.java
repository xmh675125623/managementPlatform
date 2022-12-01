package com.jiuzhou.firewall.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class FirewallDeviceUtils  {
	
	/**
	 * 探索设备
	 * 
	 * @param big
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] predive(String big,Map maps)
			throws UnsupportedEncodingException {
		byte[] three = TCPSocketUtil.byteMerger("jiuzhoucmp".getBytes(),
				big.getBytes());
		byte[] param_1 = new byte[16];
		byte[] param_2 = new byte[64];
		param_1 = TCPSocketUtil.leftPading("1111", " ", 16).getBytes();
		param_2 = TCPSocketUtil.leftPading("333", " ", 64).getBytes();
		byte[] param = TCPSocketUtil.byteMerger(param_1, param_2);

		byte[] four = TCPSocketUtil.byteMerger(three, param);
		byte[] result = EventUtils.encrypt(four);
		//String strbu = maps.get("pwd").toString();

		return TCPSocketUtil.byteMerger("jiuzhoupac".getBytes(), result);
	}
	
	
}

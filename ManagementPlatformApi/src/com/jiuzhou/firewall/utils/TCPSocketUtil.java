package com.jiuzhou.firewall.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import com.jiuzhou.plat.util.AESUtils;

public class TCPSocketUtil {
	/*
	 * 管理类write 拼接
	 * 
	 */
	public static void ManageWrite(String str) {
		byte[] b1 = "jiuzhoupac".getBytes();
		byte[] b2 = "jiuzhoucmp0x96".getBytes();
		byte[] param_1 = new byte[16];
		byte[] param_2 = new byte[64];
		param_1 = TCPSocketUtil.leftPading("1111", " ", 16).getBytes();
		param_2 = TCPSocketUtil.leftPading("333", " ", 64).getBytes();
		byte[] param = byteMerger(param_1, param_2);
		byte[] b2c = "jiuzhoucmp0x92".getBytes();
		byte[] b3c = byteMerger(b2c, param);
		byte[] b4c = EventUtils.encrypt(byteMerger(b3c, str.getBytes()));
		short s = (short) byteMerger(b1, b4c).length;
		byte[] b = shortToByteArray(s);
		byte[] b2s = byteMerger(b2, b);
		byte[] b3 = EventUtils.encrypt(b2s);

		writes(byteMerger(b1, b3));
		writes(byteMerger(b1, b4c));
		// byte[] ss = EventUtils.decrypt(recive());
		// System.out.println(new String(ss));
		closeSocket();

	}
	
	/**
	 * 获取功能管理类型socket连接的配置map
	 * @return
	 */
	public static Map<String, String> getNetworkSocketConfig() {
		//端口IP信息
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("port","6860");//端口
		return maps;
	}

	
	/**
	 * 功能管理类型
	 * @param str
	 * @param lengths
	 * @param maps
	 * @return
	 */
	public static byte[] ManageWrite(String str, int lengths, Map maps) {

		String pwds = "jiuzhoupac";
		byte[] b1 = pwds.getBytes();
		byte[] param_1 = new byte[16];
		byte[] param_2 = new byte[64];
		param_1 = TCPSocketUtil.leftPading("1111", " ", 16).getBytes();
		param_2 = TCPSocketUtil.leftPading("333", " ", 64).getBytes();
		byte[] param = byteMerger(param_1, param_2);
		byte[] b2c = "jiuzhoucmp0x92".getBytes();
		byte[] b3c = byteMerger(b2c, param);
		byte[] b3cshort = byteMerger(b3c, shortToByteArray((short) lengths));
		byte[] b4c = EventUtils.encrypt(byteMerger(b3cshort, str.getBytes()));

		return recive(byteMerger(b1, b4c), maps);
	}
	
	/**
	 * 发送指令
	 * @param str 指令字符串
	 * @param maps ip端口信息
	 * @return
	 */
	public static String sendNetworkInstruction(String str, Map maps, int ...outTime) {
		//获取指令条数
		String[] len = str.split(";");
		
		//下发
		byte[] result = null;
		try {
			result = TCPSocketUtil.networkManageWrite(str, len.length, maps, outTime);
		} catch (SocketTimeoutException e) {
			return "设备连接超时，操作失败";
		}
		
		if (result == null) {
			return null;
		}
		
		//解密返回值
		if (result != null) {
			byte[] decryptbyte = EventUtils.decrypt(result);
			String resultStr = new String(decryptbyte);
			if (resultStr != null && resultStr.length() > 16) {
				String resu = resultStr.substring(10, 11);
				if (resu.equals("1")) {// 添加成功
					return null;
				} else {
					String[] param = (resultStr.substring(12)).split("@");
					return "设备下发失败：" + param[0];
				}
			} 
		} 
		
		return "设备下发失败， 操作失败";
	}
	
	/**
	 * 功能管理类型指令拼接
	 * @param str
	 * @param lengths
	 * @param maps
	 * @return
	 * @throws SocketTimeoutException 
	 */
	public synchronized static byte[] networkManageWrite(String str, int lengths, Map maps, int ...outTime) throws SocketTimeoutException {

		String pwds = "jiuzhoupac";
		byte[] b1 = pwds.getBytes();
		byte[] b2c = "jiuzhoucmp0x92".getBytes();
		byte[] b3cshort = byteMerger(b2c, shortToByteArray((short) lengths));
		byte[] b4c = EventUtils.encrypt(byteMerger(b3cshort, str.getBytes()));
//		byte[] b4c = byteMerger(b3cshort, str.getBytes());

		return recive_(byteMerger(b1, b4c), maps, outTime);
	}
	
	
	
	/**
	 * 动作类型，如发送设备管理动作，设备删除动作等
	 * @param str 指令
	 * @param lengths 指令条数
	 * @param maps
	 * @return
	 */
	public static byte[] ActionWrite(String action, String str, int lengths, Map maps) {

		String pwds = "jiuzhoupac";
		byte[] b1 = pwds.getBytes();
//		byte[] param_1 = new byte[16];
//		byte[] param_2 = new byte[64];
//		param_1 = TCPSocketUtil.leftPading("1111", " ", 16).getBytes();
//		param_2 = TCPSocketUtil.leftPading("333", " ", 64).getBytes();
//		byte[] param = byteMerger(param_1, param_2);
//		byte[] b2c = "jiuzhoucmp0x97".getBytes();
		byte[] b2c = action.getBytes();
//		byte[] b3c = byteMerger(b2c, param);
		byte[] b3cshort = byteMerger(b2c, shortToByteArray((short) lengths));
		byte[] b4c = EventUtils.encrypt(byteMerger(b3cshort, str.getBytes()));

		return recive(byteMerger(b1, b4c), maps);
	}

	public static byte[] ManageWriteUpgrade(byte[] file, Map maps) {

		String pwds = "upgradejiuzhoupac";
		byte[] b1 = pwds.getBytes();
		byte[] b2 = byteMerger(b1, file);

		return recive(b2, maps);
	}

	public static String ManageWriteLong(String stop, String str, int lengths, Map maps) {
		String pwds = "jiuzhoupac";
		byte[] b1 = pwds.getBytes();
		byte[] param_1 = new byte[16];
		byte[] param_2 = new byte[64];
		param_1 = TCPSocketUtil.leftPading("1111", " ", 16).getBytes();
		param_2 = TCPSocketUtil.leftPading("333", " ", 64).getBytes();
		byte[] param = byteMerger(param_1, param_2);
		byte[] b2c = "jiuzhoucmp0x92".getBytes();
		byte[] b3c = byteMerger(b2c, param);
		byte[] b3cshort = byteMerger(b3c, shortToByteArray((short) lengths));
		byte[] b4c = EventUtils.encrypt(byteMerger(b3cshort, str.getBytes()));

		return resiveLong(stop, byteMerger(b1, b4c), maps);
	}

	public static String resiveLong(String Stopstring, byte[] contents, Map maps) {
		String message = "";
		try {
			// 对服务端发起连接请求
			Socket socket = new Socket(maps.get("mip").toString(), 6860);
			// 表示对于长时间处于空闲状态的Socket, 是否要自动把它关闭.
			socket.setKeepAlive(true);
			// 长连接
			OutputStream oss = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			// String strs = leftPading(str, "0", 1024);
			// oss.write(lengths);
			// oss.write(contents);
			oss.write(byteMerger(intToBytes(contents.length), contents));
			while (true) {
				byte s[] = new byte[10];
				byte b[] = new byte[10240];
				is.read(s);
				int ssa = is.read(b);
				if (ssa > 0) {
					byte bs[] = new byte[ssa];
					System.arraycopy(b, 0, bs, 0, ssa);
					byte[] returns = EventUtils.decrypt(bs);
					message = new String(returns);

					// System.out.println(message+"return");
					// 去掉空格
					if (message.indexOf(Stopstring) != -1) {// 思路是这样，具体还需要解码后判断
						is.close();
						System.out.println(new String(returns)
								+ "----------------dsfvdgvbfgffgfg---------============= ------------");
						return message;
					} else if (message.indexOf("0x93 0") != -1) {
						is.close();
						return message;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	public static void closesoc(Socket socket, InputStream is, OutputStream oss) throws IOException {
		if (socket != null) {
			if (is != null) {
				is.close();
			}
			if (oss != null) {
				oss.close();
			}
			
			socket.close();
		
		}
	}

	/**
	 * 
	 * 接收socket的请求
	 * 
	 * @throws IOException
	 **/
	public static byte[] recive(byte[] contents, Map maps) {

		byte[] message = null;
		byte[] code = "jiuzhoupac".getBytes();
		Socket socket = null;
		InputStream is = null;
		OutputStream oss = null;

		try {
			// 对服务端发起连接请求 通过单利模式
			socket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(maps.get("mip").toString(), 6860);
			socket.connect(socketAddress, 1000);
			socket.setSoTimeout(10000);

			// 接受服务端消息并打印
			// 一次请求
			is = socket.getInputStream();
			oss = socket.getOutputStream();

			// 修改前的传输方式--writes 2次 第一次传长度，第二次传内容
			// 修改后的传输方式--writes 1次 长度拼到明文之前用 int
			oss.write(byteMerger(intToBytes(contents.length), contents));

			byte s[] = new byte[10];
			byte b[] = new byte[10240];

			int ssa = is.read(s);
			if (ssa > 10) {
				if (!(s.equals(code))) {
					try {
						closesoc(socket, is, oss);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return message;
				}
			}
			if (ssa == 0 || ssa == -1) {
				try {
					closesoc(socket, is, oss);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return message;
			}

			int ssb = is.read(b);

			if (ssb > 0) {
				byte bs[] = new byte[ssb];
				System.arraycopy(b, 0, bs, 0, ssb);
				message = b;
			} else if (ssa == 0 || ssa == -1) {
				try {
					closesoc(socket, is, oss);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return message;
			} else {
				message = "ss".getBytes();
			}

			try {
				closesoc(socket, is, oss);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return message;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		try {
			closesoc(socket, is, oss);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message;
	}
	
	
	/**
	 * 接收socket的请求
	 * @param contents
	 * @param maps
	 * @param outTime socket请求超时时间
	 * @return
	 * @throws SocketTimeoutException
	 */
	public static byte[] recive_(byte[] contents, Map maps, int ...outTime) throws SocketTimeoutException {

		byte[] message = null;
		byte[] code = "jiuzhoupac".getBytes();
		Socket socket = null;
		InputStream is = null;
		OutputStream oss = null;

		try {
			// 对服务端发起连接请求 通过单利模式
			socket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(maps.get("mip").toString(), 6860);
			socket.connect(socketAddress, 1000);
			if (outTime != null && outTime.length > 0 && outTime[0] > 0) {
				socket.setSoTimeout(outTime[0] );
			} else {
				socket.setSoTimeout(1000*60*5);
			}
			

			// 接受服务端消息并打印
			// 一次请求
			is = socket.getInputStream();
			oss = socket.getOutputStream();

			// 修改前的传输方式--writes 2次 第一次传长度，第二次传内容
			// 修改后的传输方式--writes 1次 长度拼到明文之前用 int
			oss.write(byteMerger(intToBytes(contents.length), contents));

			byte s[] = new byte[10];
			byte b[] = new byte[10240];

			int ssa = is.read(s);
			if (ssa > 10) {
				if (!(s.equals(code))) {
					try {
						closesoc(socket, is, oss);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return message;
				}
			}
			if (ssa == 0 || ssa == -1) {
				try {
					closesoc(socket, is, oss);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return message;
			}

			int ssb = is.read(b);

			if (ssb > 0) {
				byte bs[] = new byte[ssb];
				System.arraycopy(b, 0, bs, 0, ssb);
				message = b;
			} else if (ssa == 0 || ssa == -1) {
				try {
					closesoc(socket, is, oss);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return message;
			} else {
				message = "ss".getBytes();
			}

			try {
				closesoc(socket, is, oss);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return message;

		} catch (SocketTimeoutException e) {
			try {
				closesoc(socket, is, oss);
			} catch (IOException ie) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw e;
		}  catch (IOException e) {
			e.printStackTrace();
		} 

		try {
			closesoc(socket, is, oss);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message;
	}

	/**
	 * 设备探索
	 * 
	 * @param contents
	 * @param maps
	 * @return
	 */

	public static byte[] recivexx(byte[] contents, Map maps) {
		byte[] message = null;
		try {
			// 对服务端发起连接请求 通过单利模式
			Socket socket = new Socket(maps.get("mip").toString(), 6860);
			// 接受服务端消息并打印
			// 一次请求
			InputStream is = socket.getInputStream();
			OutputStream oss = socket.getOutputStream();
			// String strs = leftPading(str, "0", 1024);
			oss.write(contents);
			// DataInputStream his = new DataInputStream(is);
			byte s[] = new byte[10];
			byte b[] = new byte[102400];
			is.read(s);
			// System.out.print(new String(s));
			int ssa = is.read(b);

			System.out.println(ssa + "--------------------ssassa");
			if (ssa > 0) {
				byte bs[] = new byte[ssa];
				System.arraycopy(b, 0, bs, 0, ssa);
				message = bs;
			}
			// System.out.println(bs[ssa - 1] + "sss" + ssa);
			// his.readInt();
			// his.readInt();
			is.close();
			socket.close();
			// 给服务端发送响应信息
			return message;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 
	 * socket send数据
	 **/
	public static void writes(byte[] str) {
		Socket socket = null;
		OutputStream oss;
		try {
			socket = Singleton.getInstance().getSocket();
			oss = socket.getOutputStream();
			// String strs = leftPading(str, "0", 1024);
			oss.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * socket send数据
	 **/
	public static void writes(byte[] str, Map maps) {
		Socket socket = null;

		try {

			socket = new Socket(maps.get("mip").toString(), 6860);

			OutputStream oss = socket.getOutputStream();
			// String strs = leftPading(str, "0", 1024);
			oss.write(str);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * socket 关闭数据
	 **/
	public static void closeSocket(Map maps) {
		Socket socket = null;
		socket = Singleton.getInstance(maps).getSocket();
		if (socket == null) {
			return;
		}
		try {
			// socket.getOutputStream().close();
			// socket.getInputStream().close();
			socket.setSoLinger(true, 0);
			socket.shutdownInput();
			socket.shutdownOutput();
			// socket.getTcpNoDelay();
			// socket.close();

			System.gc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void closeSocket() {
		Socket socket = null;
		socket = Singleton.getInstance().getSocket();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {

		// update
		String str =  new String(AESUtils.encryption("admin"));
		System.out.println(str);
		System.out.println(AESUtils.dncryption(str));
	}

	private static String macAddressToString(byte[] macAdd) {
		String result = null;

		StringBuffer mac = new StringBuffer();
		for (int i = 0; i < 1040; i++) {
			String s_mac = Integer.toHexString(macAdd[i] & 0xff);
			/*
			 * if (s_mac.length() == 1) { mac.append("0"); }
			 */

			mac.append(s_mac);
		}
		result = mac.toString();

		return result;
	}

	/**
	 * 
	 * byte 拼接
	 **/
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	/**
	 * 
	 * int 转byte
	 **/
	public static byte[] intToBytes(int value) {
		byte[] src = new byte[4];
		src[0] = (byte) ((value >> 24) & 0xFF);
		src[1] = (byte) ((value >> 16) & 0xFF);
		src[2] = (byte) ((value >> 8) & 0xFF);
		src[3] = (byte) (value & 0xFF);
		return src;
	}

	public static byte[] shortToByteArray(short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
			// 0 offset=8 targets[0]=
			// 1 offset=0
		}
		return targets;
	}

	/**
	 * 
	 * 定长字符串拼接
	 **/
	public static String leftPading(String strSrc, String flag, int strSrcLength) {
		String strReturn = "";
		String strtemp = "";
		int curLength = strSrc.trim().length();
		if (strSrc != null && curLength > strSrcLength) {
			strReturn = strSrc.trim().substring(0, strSrcLength);
		} else if (strSrc != null && curLength == strSrcLength) {
			strReturn = strSrc.trim();
		} else {

			for (int i = 0; i < (strSrcLength - curLength); i++) {
				strtemp = strtemp + flag;
			}

			strReturn = strSrc.trim() + strtemp;
		}
		return strReturn;
	}

	/**
	 * 
	 * 整个管理包头拼接 0x9200 16位byte 64位byte 返回拼接好的byte
	 **/
	public static byte[] manageByte() {
		// byte[] b2c = "jiuzhoucmp0x92".getBytes();
		int aa = 0;

		byte[] param_1 = new byte[16];
		byte[] param_2 = new byte[64];
		param_1 = TCPSocketUtil.leftPading("1111", " ", 16).getBytes();
		param_2 = TCPSocketUtil.leftPading("333", " ", 64).getBytes();
		System.out.println(param_2.length + "-----------------890000000000000");
		byte[] param = byteMerger(param_1, param_2);
		// byte[] b3c = byteMerger(byteMerger(b2c, intToBytes(aa)), param);
		return param;
	}

}
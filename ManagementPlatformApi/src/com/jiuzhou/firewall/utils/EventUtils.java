package com.jiuzhou.firewall.utils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventUtils {
	
	static Logger logger = LoggerFactory.getLogger(EventUtils .class);
			
	public final static String[] macList= new String[]{"EC55F9BFE578", 
		  "E89A8F3A197C", "60D819D50249",
	 "005056C00001", "00216A5245F6", "0022680FCACF",
	 "00224D7F23AD"};

	static int byte2int(byte[] res) {
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00);
		return targets;
	}

	static int halfByte(byte res, boolean isFront) {
		int result = 0;
		if (isFront) {
			result = res >> 4 & 0x000F;
		} else {
			result = res & 0x000F;
		}
		return result;
	}
	
	
	/* Following is the crypto methods */
	
	public static final String password = "jiuzhoudecodejiu";
	public static final byte fixByte = 33; // '!' --> ASC code 33
	
	static byte[] encrypt(byte[] content) {
	//	System.out.println(content.length+"----------------content.length");
		if (content == null || content.length <= 0)
			throw new RuntimeException("content is invalid");
		if (content[content.length - 1] == fixByte)
			throw new RuntimeException("content is end with 33");
		int left = content.length % 16;
		if (left == 0)
			return encrypt(content, password);
		byte[] fixedContent = new byte[content.length + 16 - left];
		System.arraycopy(content, 0, fixedContent, 0, content.length);
		/*for (int i = content.length; i < fixedContent.length; i++) {
			fixedContent[i] = fixByte;
		}*/
		return encrypt(fixedContent, password);
	}
	
	public static byte[] encrypt(byte[] content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] a = new byte[16];
			IvParameterSpec sKeySpec = new IvParameterSpec(a);
			cipher.init(Cipher.ENCRYPT_MODE, key, sKeySpec);
			byte[] result = cipher.doFinal(content);
			return result; // ����
		} catch (NoSuchAlgorithmException e) {
			logger.error("",e);
		} catch (NoSuchPaddingException e) {
			logger.error("",e);
		} catch (InvalidKeyException e) {
			logger.error("",e);
		} catch (IllegalBlockSizeException e) {
			logger.error("",e);
		} catch (BadPaddingException e) {
			logger.error("",e);
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
		return null;
	}
	
	public static byte[] decrypt(byte[] content) {
		byte[] temp = decrypt(content, password);
		int count = 0;
		int pos = temp.length;
		while (temp[--pos] == fixByte) {
			count++;
		}
		byte[] result = new byte[temp.length - count];
		System.arraycopy(temp, 0, result, 0, result.length);
		return result;
	}

	public static byte[] decrypt(byte[] content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			byte[] a = new byte[16]; 
			IvParameterSpec sKeySpec = new IvParameterSpec(a);
			cipher.init(Cipher.DECRYPT_MODE, key,sKeySpec);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			logger.error("",e);
		} catch (NoSuchPaddingException e) {
			logger.error("",e);
		} catch (InvalidKeyException e) {
			logger.error("",e);
		} catch (IllegalBlockSizeException e) {
			logger.error("",e);
		} catch (BadPaddingException e) {
			logger.error("",e);
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
		}
		return null;
	}
	
	static boolean licCheck() {
		
//		return false;

		try {
			
			StringBuffer sb = new StringBuffer();

			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			
			boolean flag = false;
			
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = networkInterfaces.nextElement();
				byte[] mac = netInterface.getHardwareAddress();
				
				if (mac != null && mac.length != 0) {
					sb.delete(0, sb.length());
					for (byte b : mac) {
						String hexString = Integer.toHexString(b & 0xFF);
						sb.append((hexString.length() == 1) ? "0" + hexString
								: hexString);
					}
					
					for (int i = 0; i < macList.length; ++i) {
						if (sb.toString().equalsIgnoreCase(macList[i])) {
//							System.out.println(sb.toString());
//							System.out.println("flag = true");
							flag = true;
							break;
						}
					}
				}
				
				if (flag) {
//					System.out.println("flag is true");
					break;
				}
			}
			
			if (!flag) {
//				System.out.println("flag is false");
				return false;
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.set(2013, 7, 28, 00, 00);

		Date currentTime = Calendar.getInstance().getTime();

		if (currentTime.before(cal.getTime())) {
//			System.out.println("before time is " + df.format(currentTime));
			return true;
		} else {
//			System.out.println("before time is " + df.format(cal.getTime()));
			return false;
		}
	}	
	
	static byte[] getAddress(String ipAddr) {
		if (ipAddr == null || ipAddr.equals("")) {
			return null;
		}

		String addr[] = ipAddr.split("\\.");// regular expression
		byte[] address = new byte[4];
		address[0] = (byte) ((Integer.valueOf(addr[0]) & 0xFF));
		address[1] = (byte) ((Integer.valueOf(addr[1]) & 0xFF));
		address[2] = (byte) ((Integer.valueOf(addr[2]) & 0xFF));
		address[3] = (byte) ((Integer.valueOf(addr[3]) & 0xFF));
		return address;
	}
	
	static byte[] toHH(int n) {
		byte[] b = new byte[4];
		b[3] = (byte) (n & 0xff);
		b[2] = (byte) (n >> 8 & 0xff);
		b[1] = (byte) (n >> 16 & 0xff);
		b[0] = (byte) (n >> 24 & 0xff);
		return b;
	}

	/**
	 *change short to high byte in the front and lower byte in the end.
	 * 
	 * @param n
	 *            short
	 * @return byte[]
	 */
	static byte[] toHH(short n) {
		byte[] b = new byte[2];
		b[1] = (byte) (n & 0xff);
		b[0] = (byte) (n >> 8 & 0xff);
		return b;
	}
}
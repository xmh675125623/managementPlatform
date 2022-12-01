package com.jiuzhou.plat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;
/**
* @author xingmh
* @version 2018年9月29日 下午5:07:13
* 字符串加解密
*/
public class AESUtils {

	private static String src = "jiuzhouaudit678^&*";
	
	private static Cipher encryptionCipher;
	
	private static Cipher dncryptionCipher;
	
	private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final int CACHE_SIZE = 1024;
	
	
	static {
		try {
			//生成Key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(src.getBytes()));  
			//使用上面这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			
			//Key转换
			SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
			
			//加密
			encryptionCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
			//解密
			dncryptionCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			dncryptionCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加密
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String encryption(String content) throws Exception {
		
		byte[] encodeResult = encryptionCipher.doFinal(content.getBytes("utf-8"));
//		return new String(base64Encoder.encode(encodeResult));
		return new String(Hex.encode(encodeResult));
		
		
	}
	
	/**
	 * 解密
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String dncryption(String content) throws Exception {
		
		byte[] decodeResult = dncryptionCipher.doFinal(Hex.decode(content.getBytes()));
		return new String (decodeResult);
	}
	
	/**
	 * 文件加密
	 * @param sourceFilePath
	 * @param destFilePath
	 * @throws Exception
	 */
	public static void encryptFile(String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath); 
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            InputStream in = new FileInputStream(sourceFile);
            OutputStream out = new FileOutputStream(destFile);
//            Key k = toKey(Base64Utils.decode(src));
//            
//            byte[] raw = k.getEncoded(); 
            
            //生成Key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(src.getBytes()));  
			//使用上面这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
            
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM); 
            Cipher cipher = Cipher.getInstance(ALGORITHM); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            CipherInputStream cin = new CipherInputStream(in, cipher);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = cin.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            cin.close();
            in.close();
        }
    }
	
	/**
	 * 文件解密
	 * @param key
	 * @param sourceFilePath
	 * @param destFilePath
	 * @throws Exception
	 */
	public static void decryptFile(String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath); 
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(destFile);
//            Key k = toKey(Base64Utils.decode(src));
//            byte[] raw = k.getEncoded(); 
            //生成Key
            try {
            	KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    			keyGenerator.init(128, new SecureRandom(src.getBytes()));  
    			//使用上面这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
    			SecretKey secretKey = keyGenerator.generateKey();
    			byte[] keyBytes = secretKey.getEncoded();
                SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM); 
                Cipher cipher = Cipher.getInstance(ALGORITHM); 
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                CipherOutputStream cout = new CipherOutputStream(out, cipher);
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    cout.write(cache, 0, nRead);
                    cout.flush();
                }
                cout.close();
			} catch (Exception e) {
				out.close();
	            in.close();
	            throw e;
			}
            out.close();
            in.close();
        }
    }
	
	 /**
     * 转换密钥
     * 
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }
    
}

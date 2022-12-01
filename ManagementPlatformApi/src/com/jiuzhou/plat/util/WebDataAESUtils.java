package com.jiuzhou.plat.util;

import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
* @author xingmh
* @version 创建时间：2019年4月10日 上午9:40:38
* web数据加解密
*/
public class WebDataAESUtils {
	
	//密钥 (需要前端和后端保持一致)
    private static final String KEY = "abcdefgabcdefg12";  
    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    
    /** 
     * aes解密 
     * @param encrypt   内容 
     * @return 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encrypt) {  
        try {
            return aesDecrypt(encrypt, KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }  
    }  
      
    /** 
     * aes加密 
     * @param content 
     * @return 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content) {  
        try {
            return aesEncrypt(content, KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }  
    }  
  
    /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }  
  
    /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String base64Encode(byte[] bytes){  
        return Base64.encodeBase64String(bytes);  
    }  
  
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    public static byte[] base64Decode(String base64Code) throws Exception{  
		return StringUtils.isEmpty(base64Code) ? null : Base64.decodeBase64(base64Code);  
    }  
  
      
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));  
  
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
  
  
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
  
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128);  
  
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes, "ISO-8859-1");  
    }  
  
  
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
    }  
    
    /**
     * 解密web数据
     * @param paramJson
     * @return
     * @throws Exception
     */
    public static String webDataDecrypt(String body) throws Exception {
    	JSONObject paramJson = JSONObject.fromObject(body);
    	String AESKey = RSAUtils.decrypt(paramJson.getString("key"), "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJeS9Ns92K2f/TntxMtotSpnQmf0bL8em+leMr5pB/R2sz1Klg1dxrDZvDkFAzWNIT/vvWbWECprxGv6G+FlkKWNAyneQa0tV7PVmdV0fmFpBT7SPpCpjU5OzZQxCidrIR6e6f1ELD+OPP9eJ7w5RIQKfj3sRIZ9KPzdqTTclFgZAgMBAAECgYEAg6F5xxQUoeO485/LMaLDAgoen0yjS+53E9lcVAO+hsy2p9moKf9wV5EfZp01xkHDyFSYtT/dVhm0wNi5carUS6/yzJMLO736VmkUEiDg4oqeD3Rb5RLI14T5YE+9WQ/utD7xTTJb3ybLCuhC8QEsaNv/3Pde2d3jYyFKplk5vSECQQD9dwNM2FG3ZIpad0uop0XGBJUuSVr6R5GEcKmzz06H2No41t1h69k2oLKtgPy+0Kf3E6IsPNVnmusespOJvDelAkEAmRcOVVrccwjnWEXsV+5NZWCIc8TgaIKRNuxJ9YIgzikhkVRj3knbV3KfUA18ZPkCfvzaPW99yReRrOjLv9uUZQJAclPRaEMWsOsnwOCYfu1cepIsnCE4aTYI/D05PsLegEYfQI4ic33Hj21yyvIojwVdDgSMHPofpEjrjwArrm/4hQJAURW9Kchuax+UKVUf0ZMOu1td6rOkiLZfY8/TfI3oAkoW1Xr1So+j9bVoXGZINNMPV2Nl1JRw80nghszm3j/XYQJAZ6cwV8bKhZJXa3cplpiribXbrfapFJVY3/HHj069mbwFfCNXjMLfjyim2Vn3iFCxy8lMZkpyq38NEa682+Jd4Q==");
		String AESBody = WebDataAESUtils.aesDecrypt(paramJson.getString("value"), AESKey);
		return AESBody;
    }
    
    /**
     * 解密web数据
     * @param paramJson
     * @return
     * @throws Exception
     */
    public static JSONObject webDataDecrypt(HttpServletRequest request) throws Exception {
    	String key = request.getParameter("key");
    	String value = request.getParameter("value");
    	String AESKey = RSAUtils.decrypt(key, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJeS9Ns92K2f/TntxMtotSpnQmf0bL8em+leMr5pB/R2sz1Klg1dxrDZvDkFAzWNIT/vvWbWECprxGv6G+FlkKWNAyneQa0tV7PVmdV0fmFpBT7SPpCpjU5OzZQxCidrIR6e6f1ELD+OPP9eJ7w5RIQKfj3sRIZ9KPzdqTTclFgZAgMBAAECgYEAg6F5xxQUoeO485/LMaLDAgoen0yjS+53E9lcVAO+hsy2p9moKf9wV5EfZp01xkHDyFSYtT/dVhm0wNi5carUS6/yzJMLO736VmkUEiDg4oqeD3Rb5RLI14T5YE+9WQ/utD7xTTJb3ybLCuhC8QEsaNv/3Pde2d3jYyFKplk5vSECQQD9dwNM2FG3ZIpad0uop0XGBJUuSVr6R5GEcKmzz06H2No41t1h69k2oLKtgPy+0Kf3E6IsPNVnmusespOJvDelAkEAmRcOVVrccwjnWEXsV+5NZWCIc8TgaIKRNuxJ9YIgzikhkVRj3knbV3KfUA18ZPkCfvzaPW99yReRrOjLv9uUZQJAclPRaEMWsOsnwOCYfu1cepIsnCE4aTYI/D05PsLegEYfQI4ic33Hj21yyvIojwVdDgSMHPofpEjrjwArrm/4hQJAURW9Kchuax+UKVUf0ZMOu1td6rOkiLZfY8/TfI3oAkoW1Xr1So+j9bVoXGZINNMPV2Nl1JRw80nghszm3j/XYQJAZ6cwV8bKhZJXa3cplpiribXbrfapFJVY3/HHj069mbwFfCNXjMLfjyim2Vn3iFCxy8lMZkpyq38NEa682+Jd4Q==");
		String AESBody = WebDataAESUtils.aesDecrypt(value, AESKey);
		return JSONObject.fromObject(AESBody);
    }
    
    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {  
        String content = "123";  
        System.out.println("加密前：" + content);  
        System.out.println("加密密钥和解密密钥：" + KEY);  
        String encrypt = aesEncrypt(content, KEY);  
        System.out.println("加密后：" + encrypt);  
        String decrypt = aesDecrypt(encrypt, KEY);  
        System.out.println("解密后：" + decrypt);  
    }

}

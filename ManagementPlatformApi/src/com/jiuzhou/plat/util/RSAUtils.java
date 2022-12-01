package com.jiuzhou.plat.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
* @author xingmh
* @version 创建时间：2019年4月9日 下午3:20:44
* 类说明
*/
public class RSAUtils {

	private static final KeyPair keyPair = initKey();
    
    private static KeyPair initKey(){
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(1024, random);
            return generator.generateKeyPair();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    /**
     * 生成public key
     * @return
     */
    public static String generateBase64PublicKey(){
        RSAPublicKey key = (RSAPublicKey)keyPair.getPublic();
        return new String(Base64.encodeBase64(key.getEncoded()));
    }
    
    /**
     * 生成public key
     * @return
     */
    public static String generateBase64PrivateKey(){
        RSAPrivateKey key = (RSAPrivateKey)keyPair.getPrivate();
        return new String(Base64.encodeBase64(key.getEncoded()));
    }
     
    /** 
	 * RSA公钥加密 
	 *  
	 * @param str 
	 *            加密字符串
	 * @param publicKey 
	 *            公钥 
	 * @return 密文 
	 * @throws Exception 
	 *             加密过程中的异常信息 
	 */  
	public static String encrypt( String str, String publicKey ) throws Exception{
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}

	/** 
	 * RSA私钥解密
	 *  
	 * @param str 
	 *            加密字符串
	 * @param privateKey 
	 *            私钥 
	 * @return 铭文
	 * @throws Exception 
	 *             解密过程中的异常信息 
	 */  
	public static String decrypt(String str, String privateKey) throws Exception{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}
    
     
//    private static byte[] decrypt(byte[] string) {
//        try {
//            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
//            RSAPrivateKey pbk = (RSAPrivateKey)keyPair.getPrivate();
//            cipher.init(Cipher.DECRYPT_MODE, pbk);
//            byte[] plainText = cipher.doFinal(string);
//            return plainText;
//        }catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
     
    public static void main(String[] args) throws Exception {
        // 生成public key
//        System.out.println(generateBase64PublicKey());
        
        // 生成private key
//        System.out.println(generateBase64PrivateKey());
         
        // 解密
//        System.out.println(decryptBase64("wAfY9JkoKay9SxcPIs1FcG+t6sR+wYwAs/mh9DpfcBraxzqoZdb9LyaAigzFQ0EKck9OyHL0dhv+Uxuw5hHw6CPT0B2Z0i1gwrjDUNaL1gWvqt1pDJVGrIYPLJSjs9xktFhY1jbxQgXGjyCt06Rwid5sJknw90AUO0CyQulfipg="));
    	System.out.println(decrypt(encrypt("哈哈", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXkvTbPditn/057cTLaLUqZ0Jn9Gy/HpvpXjK+aQf0drM9SpYNXcaw2bw5BQM1jSE/771m1hAqa8Rr+hvhZZCljQMp3kGtLVez1ZnVdH5haQU+0j6QqY1OTs2UMQonayEenun9RCw/jjz/Xie8OUSECn497ESGfSj83ak03JRYGQIDAQAB"), 
    			"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJeS9Ns92K2f/TntxMtotSpnQmf0bL8em+leMr5pB/R2sz1Klg1dxrDZvDkFAzWNIT/vvWbWECprxGv6G+FlkKWNAyneQa0tV7PVmdV0fmFpBT7SPpCpjU5OzZQxCidrIR6e6f1ELD+OPP9eJ7w5RIQKfj3sRIZ9KPzdqTTclFgZAgMBAAECgYEAg6F5xxQUoeO485/LMaLDAgoen0yjS+53E9lcVAO+hsy2p9moKf9wV5EfZp01xkHDyFSYtT/dVhm0wNi5carUS6/yzJMLO736VmkUEiDg4oqeD3Rb5RLI14T5YE+9WQ/utD7xTTJb3ybLCuhC8QEsaNv/3Pde2d3jYyFKplk5vSECQQD9dwNM2FG3ZIpad0uop0XGBJUuSVr6R5GEcKmzz06H2No41t1h69k2oLKtgPy+0Kf3E6IsPNVnmusespOJvDelAkEAmRcOVVrccwjnWEXsV+5NZWCIc8TgaIKRNuxJ9YIgzikhkVRj3knbV3KfUA18ZPkCfvzaPW99yReRrOjLv9uUZQJAclPRaEMWsOsnwOCYfu1cepIsnCE4aTYI/D05PsLegEYfQI4ic33Hj21yyvIojwVdDgSMHPofpEjrjwArrm/4hQJAURW9Kchuax+UKVUf0ZMOu1td6rOkiLZfY8/TfI3oAkoW1Xr1So+j9bVoXGZINNMPV2Nl1JRw80nghszm3j/XYQJAZ6cwV8bKhZJXa3cplpiribXbrfapFJVY3/HHj069mbwFfCNXjMLfjyim2Vn3iFCxy8lMZkpyq38NEa682+Jd4Q=="));
    }
	
}

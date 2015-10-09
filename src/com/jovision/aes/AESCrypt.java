/*
 * Copyright 2015 Daomeng Han
 *
 */
package com.jovision.aes;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class AESCrypt {
	
	/**
	 * 将二进制转为字符串
	 * @author Hellon(刘海龙) 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b){ 
		String hs  = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1){
				hs = hs + "0" + stmp;
			}else{
				hs = hs + stmp;
			}
		}
		return hs;
	}
	
	/**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
            if (hexStr.length() < 1)  
                    return null;  
            byte[] result = new byte[hexStr.length()/2];  
            for (int i = 0;i< hexStr.length()/2; i++) {  
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                    result[i] = (byte) (high * 16 + low);  
            }  
            return result;  
    } 
    
	/** AES解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */  
	public static BizAccSession decrypt_session(byte[] content, String password) throws UnsupportedEncodingException {
		
        try {  
       	 byte[] raw = password.getBytes("ASCII");  
            SecretKeySpec skp = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
            cipher.init(Cipher.DECRYPT_MODE, skp);  
            byte[] result = cipher.doFinal(content);
            /**
             * result 是解密后的数据，20字节用户名+8字节时间戳+2字节超时时间 
             */
    		byte[] userid = new byte[16];
    		
    		System.arraycopy(result, 0, userid, 0, 16);
    		
    		//String username = new String(user);
    		String username = byte2hex(userid);
   		
    		/* timestamp */
    		long timestamp = 0;
    		timestamp = ((long)result[16] & 0x00000000000000FF);
    		timestamp <<= 8;
    		timestamp |= ((long)result[17] & 0x00000000000000FF);
    		timestamp <<= 8;
    		timestamp |= ((long)result[18] & 0x00000000000000FF);
    		timestamp <<= 8;
    		timestamp |= ((long)result[19] & 0x00000000000000FF);
    		timestamp <<= 8;
    		timestamp |= ((long)result[20] & 0x00000000000000FF);
    		timestamp <<= 8;
    		timestamp |= ((long)result[21] & 0x00000000000000FF);
    		timestamp <<= 8;
    		timestamp |= ((long)result[22] & 0x00000000000000FF);
    		timestamp <<= 8;
    		timestamp |= ((long)result[23] & 0x00000000000000FF);

    		
    		/* expires */
    		int expires = (short) ((short)(result[24] << 8) | result[25]);
    		
    		BizAccSession session = new BizAccSession();
    		session.setExpires(expires);
    		session.setTimestamp(timestamp);
    		session.setUsername(username);
    		
            return session;
            
        } catch (NoSuchAlgorithmException e) {  
               e.printStackTrace();  
       	} catch (NoSuchPaddingException e) {  
               e.printStackTrace();  
       	} catch (InvalidKeyException e) {  
               e.printStackTrace();  
       	} catch (IllegalBlockSizeException e) {  
               e.printStackTrace();  
       	} catch (BadPaddingException e) {  
               e.printStackTrace();  
       	}  
        
        return null;
	}
	    
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws UnsupportedEncodingException {
		//String encryptResultStr = "371766A5F46C67C21A172571766F9A2A16C3C057E1AE8F76B5CDF7537D8D68BA";
		String encryptResultStr = "9BFC68A75FC92CC78E5DDA4F49D3A9B13F2ABA9A125355F7B9238B5E359535E3";
		String password = "HQM/NS0ujPGbF+/8";

		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);  
		
		BizAccSession ab =  AESCrypt.decrypt_session(decryptFrom, password);

		System.out.println(ab.getUsername().trim());
		System.out.println(ab.getTimestamp());
		System.out.println(ab.getExpires());
		
		System.out.println(System.currentTimeMillis());
		System.out.println(new Date(ab.getTimestamp()).toLocaleString());
		System.out.println(new Date(ab.getTimestamp()+3600*1000).toLocaleString());
	}
}

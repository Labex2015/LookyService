package br.feevale.labex.utils;

import java.security.MessageDigest;

/**
 * Created by 0126128 on 03/07/2015.
 * Créditos para: http://www.mkyong.com/java/java-md5-hashing-example/
 */
public class MD5Hashing {

    public static String generateHash(String param)throws Exception{

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(param.getBytes());

        byte byteData[] = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

package com.nan.weixin.util.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WxTestCon {
    private static String TOKEN="weixin";
    public static boolean check(String signature,String timestamp,String nonce) throws NoSuchAlgorithmException {
        //1、字典排序
        String[] strs=new String[]{TOKEN,timestamp,nonce};
        Arrays.sort(strs);
        //2、拼接后sha1加密
        String str=strs[0]+strs[1]+strs[2];
        String mysig=sha1(str);
        System.out.println(mysig);
        System.out.println(signature);

        //3、与signature比较

        return mysig.equals(signature);
    }

    private static String sha1(String str) throws NoSuchAlgorithmException {
        MessageDigest md =MessageDigest.getInstance("sha1");
        byte[] digest = md.digest(str.getBytes());
        char[] chars={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        StringBuilder sb=new StringBuilder();
        for (byte b : digest) {
            sb.append(chars[(b>>4)&15]);
            sb.append(chars[(b&15)]);
        }
        return sb.toString();
    }
}

package com.suyi.dms.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Util {
    public static String getMD5(String username,String password){
        Md5Hash md5Hash = new Md5Hash(password, username, ParamsPojo.hashIteration);
        return md5Hash.toString();
    }

    public static void main(String[] args) {
        System.out.println(getMD5("20171514112","123456"));
    }
}

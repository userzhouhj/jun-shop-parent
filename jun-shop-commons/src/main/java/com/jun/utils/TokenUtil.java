package com.jun.utils;

import java.util.UUID;

public class TokenUtil {

    public static String getToken(String name){
        return name.toUpperCase()+"_"+ UUID.randomUUID().toString();
    }

    public static String getPayToken(){
        return "PAYTOKEN_"+UUID.randomUUID().toString();
    }
}

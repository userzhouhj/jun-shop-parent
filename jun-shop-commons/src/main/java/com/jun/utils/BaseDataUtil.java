package com.jun.utils;

import com.jun.base.BaseData;
import com.jun.constants.BaseConstants;

/**
 * 用于封装BaseData的数据
 */
public class BaseDataUtil {

    //通用类型
    public static <T> BaseData resultData(Integer code,String msg,T data){
        return new BaseData(code, msg,data);
    }

    //成功返回但不封装数据
    public static BaseData resultSuccess(){
        return new BaseData(BaseConstants.RRTURN_SUCCESS_CODE
                ,BaseConstants.RETURN_SUCCESS_MSG);

    }
    public static BaseData resultSuccess(String msg){
        return new BaseData(BaseConstants.RRTURN_SUCCESS_CODE
                ,msg);
    }

    //成功并封装数据
    public static <T> BaseData resultSuccess(T data){
        return new BaseData(BaseConstants.RRTURN_SUCCESS_CODE,BaseConstants.RETURN_SUCCESS_MSG,data);
    }

    //失败
    public static BaseData resultFail(String msg){
        return new BaseData(BaseConstants.RETURN_FAIL_CODE
                ,msg);
    }

}

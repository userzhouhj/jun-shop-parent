package com.jun.base;

import lombok.Data;

@Data
public class BaseData<T>{

    private Integer code;

    private String msg;

    private T data;


    public BaseData(Integer code,String msg,T data){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseData(Integer code,String msg){
        this.msg = msg;
        this.code = code;
    }

    public BaseData(Integer code){
        this.code = code;
    }

}

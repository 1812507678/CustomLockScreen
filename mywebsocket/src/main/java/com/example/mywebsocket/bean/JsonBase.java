package com.example.mywebsocket.bean;

/**
 * Created by hai on 7/15/2017.
 */
public class JsonBase<T> {
    public int ret;
    public T errDesc;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public T getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(T errDesc) {
        this.errDesc = errDesc;
    }

    @Override
    public String toString() {
        return "JsonBase{" +
                "ret='" + ret + '\'' +
                ", errDesc='" + errDesc + '\'' +
                '}';
    }

}
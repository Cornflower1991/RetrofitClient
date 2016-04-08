package com.myjiashi.merchant;

/**
 * @page Created by yexiuliang on 2016/4/7.
 */
public class HttpResult<T> {
    private String result;
    private T data;
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "HttpResult{" +
                "result='" + result + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}

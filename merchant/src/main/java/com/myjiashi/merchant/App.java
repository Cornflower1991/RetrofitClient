package com.myjiashi.merchant;

import android.app.Application;

import http.RetrofitClient;
import http.SigningInterceptor;

/**
 * @page Created by yexiuliang on 2016/4/7.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient.getInstance()
                .initOkHttpClient(30,new SigningInterceptor("app","android"),null)
                .initRetrofit("http://zhuangbi.info/",null,null);

    }
}

package com.myjiashi.merchant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.Map;

import http.CommonHttpObserver;
import http.HttpErrorResponse;
import http.RetrofitClient;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mTextView = (TextView) findViewById(R.id.text);
        Map<String, String> map = new HashMap<>();
        map.put("key", "1");
        map.put("sign", "asdasdasdasd");
//        RetrofitClient.getInstance().getRetrofit().create(ZhuangbiApi.class)
//                .search(map).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CommonHttpObserver<JsonArray>() {
//                    @Override
//                    protected void onResultError(HttpErrorResponse ex) {
//
//                    }
//
//                    @Override
//                    public void onNext(JsonArray jsonElements) {
//                        textView.setText(jsonElements.toString());
//                    }
//                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        RetrofitClient.getInstance().getRetrofit().create(ZhuangbiApi.class)
                .search("可爱").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonHttpObserver<JsonArray>() {
                    @Override
                    protected void onResultError(HttpErrorResponse ex) {

                    }

                    @Override
                    public void onNext(JsonArray jsonElements) {
                        mTextView.setText(jsonElements.toString());
                    }
                });
    }
}

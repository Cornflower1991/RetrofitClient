// (c)2016 Flipboard Inc, All Rights Reserved.

package com.myjiashi.merchant;

import com.google.gson.JsonArray;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ZhuangbiApi {
    @POST("search")
    @FormUrlEncoded
    Observable<JsonArray> search(@FieldMap Map<String, String> query);


    //    @GET("search")
//    Call<JsonArray> search(@Query("q") String query);
//    @POST("search")
//    @FormUrlEncoded
//    Observable<HttpResponseData<List<ZhuangbiImage>>> search(@Field("query")  String query);

    @FormUrlEncoded  //表单字段提交
    @POST("http://101.201.222.239/server-keke-report/report/batch/app")
    Observable<HttpResult<String>> postStatic(@Field("data") String data);

    @GET("search")
    Observable<JsonArray> search(@Query("q") String query);
}

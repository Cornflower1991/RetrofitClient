package http;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myjiashi.common.CommonApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @page Created by yexiuliang on 2016/4/6.
 */
public class RetrofitClient {
    public static final String API_SERVER = "http://zhuangbi.info/";
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private Retrofit mRetrofit;

    private static RetrofitClient mInstance;
    private Context context;

    /**
     * 获取client实例
     *
     * @return client实例
     */
    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public RetrofitClient() {
        context = CommonApplication.getApplication();
    }

    public RetrofitClient initOkHttpClient(int timeout, SigningInterceptor sign
            , CookiesInterceptor cookies) {
        if (timeout == 0) {
            timeout = 30;
        }
        if (sign == null) {
            sign = new SigningInterceptor("appid", "");
        }
        if (cookies == null) {
            cookies = new CookiesInterceptor(context);
        }
        mOkHttpClient = mOkHttpClient.newBuilder()
                //设定30秒超时
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                        //设置拦截器,用于请求参数的签名
//        .interceptors().add(sign)
                .addInterceptor(sign)
                        //设置拦截器,用于自定义Cookies的设置
                .addNetworkInterceptor(cookies)
                .build();

        return mInstance;
    }

    public RetrofitClient initRetrofit(String baseUrl,
                                       ResponseConvertFactory gsonConverterFactory,
                                       CallAdapter.Factory rxJava) {
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = API_SERVER;
        }
        if (gsonConverterFactory == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();
            gsonConverterFactory = ResponseConvertFactory.create(gson);
        }
        if (rxJava == null) {
            rxJava = RxJavaCallAdapterFactory.create();
        }
        if (mRetrofit == null) {
            //构建Retrofit
            mRetrofit = new Retrofit.Builder()
                    //配置服务器路径
                    .baseUrl(baseUrl)
                            //配置转化库，默认是Gson
                    .addConverterFactory(gsonConverterFactory)
                            //配置回调库，采用RxJava
                    .addCallAdapterFactory(rxJava)
                            //设置OKHttpClient为网络客户端
                    .client(mOkHttpClient)
                    .build();
        }
        return mInstance;
    }

    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            throw new RuntimeException("Retrofit has not init");
        }
        return mRetrofit;
    }

    public OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            throw new RuntimeException("OkHttpClient has not init");
        }
        return mOkHttpClient;
    }

    /**
     * 设置缓存
     *
     * @param flag
     */
    public RetrofitClient enableCache(boolean flag, File cacheDirectory) {
        if (flag) {
            //设置缓存目录
            if (cacheDirectory == null || cacheDirectory != null && !cacheDirectory.exists()) {
                cacheDirectory = new File(context.getCacheDir()
                        .getAbsolutePath(), "HttpCache");
            }
            Cache cache = new Cache(cacheDirectory, 20 * 1024 * 1024);
            //缓存拦截器
            mOkHttpClient.newBuilder().addInterceptor(new CacheInterceptor())
                    .addNetworkInterceptor(new CacheInterceptor())
                            //设置缓存
                    .cache(cache);
            mOkHttpClient.interceptors().add(new CacheInterceptor());
        }
        return mInstance;
    }
}

package http;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加参数签名
 * @page Created by yexiuliang on 2016/4/6.
 */
public class SigningInterceptor implements Interceptor {

    private final String mApiKey;
    private final String mApiSecret;

    public SigningInterceptor(String apiKey, String apiSecret) {
        mApiKey = apiKey;
        mApiSecret = apiSecret;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        //根据key 和mApiSecret算出来的一个值
//        String marvelHash = MarvelApiUtils.generateMarvelHash(mApiKey, mApiSecret);
        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("key", mApiKey)
                .addQueryParameter("Secret",mApiSecret);
//                .addQueryParameter(MarvelService.PARAM_HASH, marvelHash);

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }

    //到家对参数的签名
//    private String generateSignUrl(String url, Map<String, String> postParams) {
//        url = Utility.addParam(url, PARAM_FORMAT, "json");
//        url = Utility.addParam(url, PARAM_SIGNMETHOD, "md5");
//        url = Utility.addParam(url, PARAM_V, "2");
//
//        url = Utility.addParam(url, PARAM_TIMESTAMP, Long.toString(
//                SERVER_TIME_DIFF + System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS));
//        url = Utility.addParam(url, PARAM_NONCE, Double.toString(Math.random()));
//        url = Utility.addParam(url, PARAM_APPSIGN, getAppSign(url, postParams));
//        return url;
//    }
}

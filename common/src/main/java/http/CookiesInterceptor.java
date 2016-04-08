package http;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @page Created by yexiuliang on 2016/4/6.
 */
public class CookiesInterceptor implements Interceptor {
    private Context context;

    public CookiesInterceptor(Context context) {
        this.context = context;
    }
    //重写拦截方法，处理自定义的Cookies信息  CookieUtil则是一些自定义解析和生成方法以及SharedPreferences的存取
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request compressedRequest = request.newBuilder()
//                .header("cookie", CookieUtil.getCookies(context))
                .build();
        Response response = chain.proceed(compressedRequest);
//        CookieUtil.saveCookies(response.headers(), context);
        return response;
    }
}

package http;


import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 网络请求的公共回调
 *
 * @page Created by yexiuliang on 2016/4/7.
 */
public abstract class CommonHttpObserver<T> extends Subscriber<T> {

    //对应HTTP的状态码
    public static final int SUCCESS_CODE = HttpURLConnection.HTTP_OK;
    public static final int HTTP_NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND;
    public static final int CLIENT_TIME_OUT = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
    public static final int HTTP_SERVER_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR;
    public static final int GATEWAY_TIME_OUT = HttpURLConnection.HTTP_GATEWAY_TIMEOUT;
    /**
     * 登录信息失效
     */
    public static final int STATUS_INVALID_LOGIN_SIGN = 514;
    /**
     * 本地时间戳与服务端误差太大（会影响请求的app sign校验）
     */
    public static final int STATUS_INVALID_TIME = 513;

    public static final String NETWORK_TIME_OUT =
            "{'status':408,'result':{'errormsg':'连接超时，请稍后重试'}}";

    public static final String NETWORK_IOERROR =
            "{'status':404,'result':{'errormsg':'连接服务器失败，请检查网络设置'}}";

    public static final String REDIRECT_ERROR =
            "{'status':300,'result':{'errormsg':'您的请求被运营商跳转了，请稍后重试'}}";
    //
    public static final String SERVER_ERROR =
            "{'status':500,'result':{'errormsg':'访问的人太多了，豆豆我一下招呼不过来，请稍后重试'}}";    //SUPPRESS CHECKSTYLE

    protected CommonHttpObserver() {

    }

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }

        HttpErrorResponse errorResponse;
        if (e instanceof IOException) {  //IO异常
            errorResponse = HttpUtil.createHttpErrorData(NETWORK_IOERROR);
        } else if (e instanceof HttpException) {    //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    errorResponse = HttpUtil.createHttpErrorData(NETWORK_TIME_OUT);
                    break;
                case HttpURLConnection.HTTP_MOVED_PERM:
                case HttpURLConnection.HTTP_MOVED_TEMP:
                    errorResponse = HttpUtil.createHttpErrorData(REDIRECT_ERROR);
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    //是否开启缓存，返回缓存 ，ok不支持post缓存
//                    if (cache) {
//                        String cache = readCache(url, postParams);
//                        if (!TextUtils.isEmpty(cache)) {
//                            errorResponse = HttpUtil.createHttpErrorData(String.format(NETWORK_CACHE_FORMAT, cache));
//                        } else {
//                            errorResponse = HttpUtil.createHttpErrorData(NETWORK_IOERROR);
//                        }
//                    } else {
                    errorResponse = HttpUtil.createHttpErrorData(NETWORK_IOERROR);
//                    }
                    break;
                default:
                    errorResponse = HttpUtil.createHttpErrorData(SERVER_ERROR);
                    break;
            }
        } else if (e instanceof ResultException) {    //服务器返回的错误
            ResultException resultException = (ResultException) e;
            errorResponse = resultException.getHttpErrorResponse();
        } else {
            errorResponse = HttpUtil.createHttpErrorData(SERVER_ERROR);
        }
        onResultError(errorResponse);
    }


    /**
     * 服务器返回的错误
     */
    protected abstract void onResultError(HttpErrorResponse ex);

    @Override
    public void onCompleted() {

    }

}

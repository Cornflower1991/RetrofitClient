package http;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @page Created by yexiuliang on 2016/4/6.
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d("Network", "response>>" + response);
        Object json = null;
        try {
            json = new JSONTokener(response).nextValue();
            //JSONObject对象
            if (json instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) json;
                int code = jsonObject.getInt("code");
                if (code == HttpURLConnection.HTTP_OK) {
                    String result = jsonObject.getString("result");
                    return gson.fromJson(result, type);
                } else {
                    //ErrResponse 将msg解析为异常消息文本
                    HttpErrorResponse httpErrorData = HttpUtil.createHttpErrorData(response);
                    throw new ResultException(httpErrorData);
                }
                //JSONArray对象
            } else if (json instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) json;
                return gson.fromJson(response, type);
            }
        } catch (JSONException e) {
            //ErrResponse json解析出错
            e.printStackTrace();
            HttpErrorResponse data = HttpUtil.createHttpErrorData(CommonHttpObserver.SERVER_ERROR);
            throw new ResultException(data);
        }
        return gson.fromJson(response, type);
    }


}


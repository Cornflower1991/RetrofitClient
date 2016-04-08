package http;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @page Created by yexiuliang on 2016/4/7.
 */
public class HttpUtil {

    public static HttpErrorResponse createHttpErrorData(String json) {
        try {
            JSONObject object = new JSONObject(json);
            HttpErrorResponse data = new HttpErrorResponse();
            data.setStatus(object.optInt("status"));
            data.setResult(object.optJSONObject("result"));
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

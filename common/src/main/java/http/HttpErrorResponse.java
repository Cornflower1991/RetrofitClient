package http;

import org.json.JSONObject;

/**
 * @page Created by yexiuliang on 2016/4/6.
 */
public class HttpErrorResponse {
    private int status;
    private JSONObject result;

    public HttpErrorResponse() {
    }

    public HttpErrorResponse(JSONObject result, int status) {
        this.result = result;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HttpErrorResponse{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}

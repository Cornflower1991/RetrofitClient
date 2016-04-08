package http;

/**
 * @page Created by yexiuliang on 2016/4/6.
 */
public class ResultException extends RuntimeException {


    private HttpErrorResponse mHttpErrorResponse;

    public ResultException(HttpErrorResponse data) {

        this.mHttpErrorResponse = data;

    }

    public HttpErrorResponse getHttpErrorResponse() {
        return mHttpErrorResponse;
    }

    public void setHttpErrorResponse(HttpErrorResponse httpErrorResponse) {
        mHttpErrorResponse = httpErrorResponse;
    }
}

package LookBook.weatherData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("header")
    @Expose
    private HeaderData header;

    @SerializedName("body")
    @Expose
    private BodyData body;

    public HeaderData getHeader() {
        return header;
    }

    public void setHeader(HeaderData header) {
        this.header = header;
    }

    public BodyData getBody() {
        return body;
    }

    public void setBody(BodyData body) {
        this.body = body;
    }
}

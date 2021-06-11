package LookBook.currentWeatherData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentResponseData {
    @SerializedName("header")
    @Expose
    private CurrentHeaderData header;

    @SerializedName("body")
    @Expose
    private CurrentBodyData body;

    public CurrentHeaderData getHeader() {
        return header;
    }

    public void setHeader(CurrentHeaderData header) {
        this.header = header;
    }

    public CurrentBodyData getBody() {
        return body;
    }

    public void setBody(CurrentBodyData body) {
        this.body = body;
    }
}

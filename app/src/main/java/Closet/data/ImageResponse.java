package Closet.data;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("indexes")
    private int[] indexes;

    @SerializedName("urls")
    private String[] urls;

    public int imageId;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int[] getIndexes(){ return indexes;}

    public String[] getUrls(){ return urls;}

    public void setCode(){ this.code=code; }

    public void setMessage(){ this.message=message; }

    public void setIndexes(){ this.indexes=indexes; }

    public void setUrls(){ this.urls=urls; }
}

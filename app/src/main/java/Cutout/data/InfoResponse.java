//이거 사용 안함
package Cutout.data;

import com.google.gson.annotations.SerializedName;

public class InfoResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("category")
    private String category;

    public int getCode() { return code; }

    public String getMessage() { return message; }

    public String getCategory(){ return category; }
}
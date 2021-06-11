package Closet.data;

import com.google.gson.annotations.SerializedName;

public class DeleteData {
    @SerializedName("userId")
    private String userId;

    @SerializedName("url")
    private String url;


    public DeleteData(String userId, String url) {
        this.userId=userId;
        this.url=url;
    }
}

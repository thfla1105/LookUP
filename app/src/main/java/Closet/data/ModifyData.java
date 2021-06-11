package Closet.data;

import com.google.gson.annotations.SerializedName;

public class ModifyData {
    @SerializedName("userId")
    private String userId;

    @SerializedName("url")
    private String url;

    @SerializedName("category")
    private String category;


    public ModifyData(String userId, String url, String category) {
        this.userId=userId;
        this.url=url;
        this.category=category;
    }
}

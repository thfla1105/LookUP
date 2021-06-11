package Closet.data;

import com.google.gson.annotations.SerializedName;

public class ImageData {
    public int imageId;

    @SerializedName("userId")
    private String userId;

    @SerializedName("category")
    private String category;


    public ImageData(String userId, String category) {
        this.userId=userId;
        this.category=category;
    }
}

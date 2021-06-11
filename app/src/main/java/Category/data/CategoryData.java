package Category.data;

import com.google.gson.annotations.SerializedName;

public class CategoryData {
    @SerializedName("userId")
    private String userId;

    @SerializedName("imgName")
    private String imgName;


    public CategoryData(String userId, String imgName) {
        this.userId = userId;
        this.imgName = imgName;
    }
}

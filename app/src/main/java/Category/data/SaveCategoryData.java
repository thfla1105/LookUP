package Category.data;

import com.google.gson.annotations.SerializedName;

public class SaveCategoryData {
    @SerializedName("userId")
    private String userId;

    @SerializedName("imgName")
    private String imgName;

    @SerializedName("category")
    private String category;

    public SaveCategoryData(String userId, String imgName, String category) {
        this.userId = userId;
        this.imgName = imgName;
        this.category=category;
    }
}

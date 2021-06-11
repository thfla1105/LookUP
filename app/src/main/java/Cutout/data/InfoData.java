//이거 사용 안함!
package Cutout.data;

import com.google.gson.annotations.SerializedName;

public class InfoData {
    @SerializedName("userId")
    private String userId;

    @SerializedName("imgName")
    private String imgName;


    public InfoData(String userId, String imgName) {
        this.userId = userId;
        this.imgName = imgName;
    }
}

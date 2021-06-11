package ImageSelect;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostItemDataResponse {

    @SerializedName("userID")
    public String userID;

    @SerializedName("imageList")
    public List<Integer> imageList;

    @SerializedName("Purpose")
    public int Purpose;

    public String getUserID() {
        return userID;
    }

    public List<Integer> getImageID() {
        return imageList;
    }

    public int getPurpose() {
        return Purpose;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setImageID(List<Integer> imageList) {
        this.imageList=imageList;
    }

    public void setPurpose(int purpose) {
        Purpose = purpose;
    }
}

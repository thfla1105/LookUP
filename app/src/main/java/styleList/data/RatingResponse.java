package styleList.data;

import com.google.gson.annotations.SerializedName;

public class RatingResponse {
    @SerializedName("imageID")
    public int imageID;

    @SerializedName("rating")
    public int rating;

    @SerializedName("userId")
    public String userId;

    public int getImageId() {
        return imageID;
    }
    public void setImageID(int imageID){
        this.imageID=imageID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserID() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

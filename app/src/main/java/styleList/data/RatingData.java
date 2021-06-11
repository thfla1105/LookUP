package styleList.data;

import com.google.gson.annotations.SerializedName;

public class RatingData {
    @SerializedName("userId")
    public String userId;

    @SerializedName("imageID")
    public int imageID;

    @SerializedName("rating")
    public int rating;


   public RatingData(String userId, int imageID){
       this.userId=userId;
       this.imageID=imageID;
   }

    public RatingData(String userId, int imageID, int rating){
        this.userId=userId;
        this.imageID=imageID;
        this.rating=rating;
    }


}

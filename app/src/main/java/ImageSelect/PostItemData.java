package ImageSelect;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostItemData {

    @SerializedName("userID")
    @Expose
    public String userID;

    @SerializedName("imageList")
    @Expose
    public List<Integer> imageList;
    @SerializedName("Purpose")
    @Expose
    public int Purpose;

    PostItemData(String userID, List<Integer> imageList, int Purpose){
        this.userID=userID;
        this.imageList=imageList;
        this.Purpose=Purpose;
    }



}

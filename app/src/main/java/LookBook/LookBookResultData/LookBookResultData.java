package LookBook.LookBookResultData;

import com.google.gson.annotations.SerializedName;

public class LookBookResultData {
    @SerializedName("userId")
    private String userId;

    @SerializedName("top")
    private String top;

    @SerializedName("bottom")
    private String bottom;

    @SerializedName("outer")
    private String outer;

    @SerializedName("dress")
    private String dress;

    @SerializedName("acc")
    private String acc;

    public LookBookResultData(String userId, String top, String bottom, String outer, String dress, String acc){
        this.userId=userId;
        this.top=top;
        this.bottom=bottom;
        this.outer=outer;
        this.dress=dress;
        this.acc=acc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getOuter() {
        return outer;
    }

    public void setOuter(String outer) {
        this.outer = outer;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }
}

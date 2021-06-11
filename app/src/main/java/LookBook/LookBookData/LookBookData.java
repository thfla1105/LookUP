package LookBook.LookBookData;

import com.google.gson.annotations.SerializedName;

public class LookBookData {
    @SerializedName("userId")
    private String userId;

    @SerializedName("purpose")
    private int purpose;

    @SerializedName("tempConvert")
    private int tempConvert;

    public LookBookData(String userId, int purpose, int tempConvert) {
        this.userId=userId;
        this.purpose=purpose;
        this.tempConvert=tempConvert;
    }
}

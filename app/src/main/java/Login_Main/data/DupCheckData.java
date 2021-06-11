package Login_Main.data;

import com.google.gson.annotations.SerializedName;

public class DupCheckData {
    @SerializedName("userId")
    String userId;


    public DupCheckData(String userId) {
        this.userId = userId;
    }
}

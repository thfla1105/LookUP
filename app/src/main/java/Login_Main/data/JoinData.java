package Login_Main.data;

import com.google.gson.annotations.SerializedName;

public class JoinData {
    @SerializedName("userId")
    private String userId;

    @SerializedName("userPwd")
    private String userPwd;

    @SerializedName("userName")
    private String userName;

    @SerializedName("userGender")
    private String userGender;

    @SerializedName("userAge")
    private String userAge;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("userPhone")
    private String userPhone;

    public JoinData(String userId, String userPwd, String userName, String userGender, String userAge, String userEmail, String userPhone) {
        this.userId=userId;
        this.userPwd=userPwd;
        this.userName = userName;
        this.userGender=userGender;
        this.userAge=userAge;
        this.userEmail = userEmail;
        this.userPhone=userPhone;
    }
}

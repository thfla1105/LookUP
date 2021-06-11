package LookBook.LookBookResultData;

import com.google.gson.annotations.SerializedName;

public class LookBookResultResponse {
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


    @SerializedName("resultCode_top")
    private int resultCode_top;
    @SerializedName("message_top")
    private String message_top;


    @SerializedName("resultCode_bottom")
    private int resultCode_bottom;
    @SerializedName("message_bottom")
    private String message_bottom;


    @SerializedName("resultCode_outer")
    private int resultCode_outer;
    @SerializedName("message_outer")
    private String message_outer;


    @SerializedName("resultCode_dress")
    private int resultCode_dress;
    @SerializedName("message_dress")
    private String message_dress;


    @SerializedName("resultCode_acc")
    private int resultCode_acc;
    @SerializedName("message_acc")
    private String message_acc;


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

    public int getResultCode_top() {
        return resultCode_top;
    }

    public void setResultCode_top(int resultCode_top) {
        this.resultCode_top = resultCode_top;
    }

    public String getMessage_top() {
        return message_top;
    }

    public void setMessage_top(String message_top) {
        this.message_top = message_top;
    }

    public int getResultCode_bottom() {
        return resultCode_bottom;
    }

    public void setResultCode_bottom(int resultCode_bottom) {
        this.resultCode_bottom = resultCode_bottom;
    }

    public String getMessage_bottom() {
        return message_bottom;
    }

    public void setMessage_bottom(String message_bottom) {
        this.message_bottom = message_bottom;
    }

    public int getResultCode_outer() {
        return resultCode_outer;
    }

    public void setResultCode_outer(int resultCode_outer) {
        this.resultCode_outer = resultCode_outer;
    }

    public String getMessage_outer() {
        return message_outer;
    }

    public void setMessage_outer(String message_outer) {
        this.message_outer = message_outer;
    }

    public int getResultCode_dress() {
        return resultCode_dress;
    }

    public void setResultCode_dress(int resultCode_dress) {
        this.resultCode_dress = resultCode_dress;
    }

    public String getMessage_dress() {
        return message_dress;
    }

    public void setMessage_dress(String message_dress) {
        this.message_dress = message_dress;
    }

    public int getResultCode_acc() {
        return resultCode_acc;
    }

    public void setResultCode_acc(int resultCode_acc) {
        this.resultCode_acc = resultCode_acc;
    }

    public String getMessage_acc() {
        return message_acc;
    }

    public void setMessage_acc(String message_acc) {
        this.message_acc = message_acc;
    }
}

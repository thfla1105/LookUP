package Cutout.data;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class BgImageResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;


    @SerializedName("imageuri")
    private Uri imageuri;



    public int getCode() { return code; }

    public String getMessage() { return message; }


    public Uri getUri() { return imageuri;
    }
}
package Category.data;

import com.google.gson.annotations.SerializedName;

public class CategoryResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("category")
    private String[] category;

    public int getCode() { return code; }

    public String getMessage() { return message; }

    public String[] getCategory(){ return category; }

    public String getCategoryResult(){ return category[0]; }
}

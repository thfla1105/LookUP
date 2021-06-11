package Closet.data;

import com.google.gson.annotations.SerializedName;

public class Data {
    public int numId;
    public String url;
    public String category;

    public Data(int numId, String url, String category) {
        this.numId=numId;
        this.url=url;
        this.category=category;
    }

    public int getNumId(){ return numId; }

    public void setNumId(){this.numId=numId;}

    public String getUrl(){
        return url;
    }

    public void setUrl(){
        this.url=url;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(){
        this.category=category;
    }
}

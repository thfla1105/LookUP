package LookBook.weatherData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemsData {
    @SerializedName("item")
    @Expose
    public Item[] item;

    public Item[] getItem() {
        return item ;
    }

    public void setItem(Item[] item) {
        this.item  = item;
    }
}

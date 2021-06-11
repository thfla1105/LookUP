package LookBook.currentWeatherData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentItemsData {
    @SerializedName("item")
    @Expose
    public CurrentItem[] item;

    public CurrentItem[] getItem() {
        return item ;
    }

    public void setItem(CurrentItem[] item) {
        this.item  = item;
    }
}

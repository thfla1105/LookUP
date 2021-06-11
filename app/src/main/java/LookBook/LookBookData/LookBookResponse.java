package LookBook.LookBookData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LookBookResponse{
    @SerializedName("StyleList")
    @Expose
    public List<CoordiData> StyleList;

    public List<CoordiData> getStyleList() {
        return StyleList ;
    }

    public void setStyleList(List<CoordiData> StyleList) {
        this.StyleList = StyleList;
    }
}

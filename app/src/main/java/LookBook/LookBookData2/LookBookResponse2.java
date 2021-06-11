package LookBook.LookBookData2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class LookBookResponse2 {
    @SerializedName("columns")
    @Expose
    private List[] columns;

    @SerializedName("index")
    @Expose
    private List[] index;

    @SerializedName("data")
    @Expose
    private List[][] data;

    public List[] getColumns() {
        return columns;
    }

    public void setColumns(List[] columns) {
        this.columns = columns;
    }

    public List[] getIndex() {
        return index;
    }

    public void setIndex(List[] index) {
        this.index = index;
    }

    public List[][] getData() {
        return data;
    }

    public void setData(List[][] data) {
        this.data = data;
    }

    /*
    @SerializedName("columns")
    @Expose
    private ColumnsData columns;

    @SerializedName("index")
    @Expose
    private List[] index ;

    @SerializedName("data")
    @Expose
    private List[] data ;

    public ColumnsData getColumns() {
        return columns;
    }

    public void setColumns(ColumnsData columns) {
        this.columns = columns;
    }

    public List[] getIndex() {
        return index;
    }

    public void setIndex(List[] index) {
        this.index = index;
    }

    public List[] getData() {
        return data;
    }

    public void setData(List[] data) {
        this.data = data;
    }

     */
}

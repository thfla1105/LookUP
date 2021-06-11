package LookBook.weatherData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyData {
    @SerializedName("dataType")
    @Expose
    private String dataType;

    @SerializedName("items")
    @Expose
    private ItemsData items;

    @SerializedName("numOfRows")
    @Expose
    private String numOfRows;

    @SerializedName("pageNo")
    @Expose
    private String pageNo;

    @SerializedName("totalCount")
    @Expose
    private String totalCount;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public ItemsData getItems() {
        return items;
    }

    public void setItems(ItemsData items) {
        this.items = items;
    }

    public String getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(String numOfRows) {
        this.numOfRows = numOfRows;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}

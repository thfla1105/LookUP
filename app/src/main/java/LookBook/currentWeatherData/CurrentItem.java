package LookBook.currentWeatherData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentItem {
    @SerializedName("baseDate")
    @Expose
    public String baseDate;

    @SerializedName("baseTime")
    @Expose
    public String baseTime;

    @SerializedName("category")
    @Expose
    public String category;

    @SerializedName("nx")
    @Expose
    public String nx;

    @SerializedName("ny")
    @Expose
    public String ny;

    @SerializedName("obsrValue")
    @Expose
    public String obsrValue;

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNx() {
        return nx;
    }

    public void setNx(String nx) {
        this.nx = nx;
    }

    public String getNy() {
        return ny;
    }

    public void setNy(String ny) {
        this.ny = ny;
    }

    public String getObsrValue() {
        return obsrValue;
    }

    public void setObsrValue(String obsrValue) {
        this.obsrValue = obsrValue;
    }
}

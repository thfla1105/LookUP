package LookBook.LookBookData;

import java.io.Serializable;

public class CoordiFiveData implements Serializable {
    private String top;
    private String bottom;
    private String outer;
    private String dress;
    private String acc;

    public CoordiFiveData(String top, String bottom, String outer, String dress, String acc){
        this.top=top;
        this.bottom=bottom;
        this.outer=outer;
        this.dress=dress;
        this.acc=acc;
    }

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
}

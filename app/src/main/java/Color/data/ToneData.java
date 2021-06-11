package Color.data;


import com.google.gson.annotations.SerializedName;

public class ToneData {
    @SerializedName("toneInOn")
    public int InOn;
    public int InOn2;
    public int InOn3;


    public ToneData(int InOn, int InOn2, int InOn3) {
        this.InOn=InOn;
        this.InOn2=InOn2;
        this.InOn3=InOn3;

    }
}

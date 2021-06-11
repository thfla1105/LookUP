package LookBook.ImageAdapter;

import android.net.Uri;

public class ListItem_temp {
    Uri uri;
    String number;

    public ListItem_temp(Uri uri, String number){
        this.uri=uri;
        this.number=number;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

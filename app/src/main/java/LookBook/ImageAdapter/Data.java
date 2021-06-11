package LookBook.ImageAdapter;

import android.net.Uri;

public class Data {
    public int numId;
    public String url;
    public String category;
    public Uri uri;

    public Data(Uri uri) {
        this.uri=uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
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
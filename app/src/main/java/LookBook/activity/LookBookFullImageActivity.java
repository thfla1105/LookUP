package LookBook.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.R;

public class LookBookFullImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook_image_detail);
        // Get intent data
        Intent i = getIntent();
        // Get Selected Image Id
        String imgUrl = i.getExtras().getString("uri");
        Uri imgUri=Uri.parse(imgUrl);
        Log.d("uri:::::: ", imgUri.toString());
        //ImageAdapter imageAdapter = new ImageAdapter(this);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        if(imgUri!=null) {
            imageView.setImageURI(imgUri);
            /*
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(getApplicationContext()).load(imgUrl).apply(options).into(imageView);

             */
        }
        //imageView.setImageResource(imageAdapter.thumbImages[position]);
    }
}

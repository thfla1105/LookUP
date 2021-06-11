package LookBook.activity;
//import com.mvpstars.reactnative.mergeimages.RNMergeImagesPackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.Toast;

import java.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class MergeActivity extends AppCompatActivity{
    //ImageView image;
    //ImageView image2;
    //ImageView image3;
    //ImageView image4;
    ArrayList<String> urls=new ArrayList<>(); //옷 코디 조합(하나임)
    ArrayList<Bitmap> bitmaps=new ArrayList<>(); //각 옷의 url에 대한 비트맵 저장
    ArrayList<Uri> uris = new ArrayList<>(); //룩북의 uri들 (룩북 2개 또는 3개 예정)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook_activity_merge);

        urls.add("https://image.msscdn.net/images/goods_img/20200304/1334438/1334438_1_500.jpg");
        urls.add("https://image.msscdn.net/images/goods_img/20200304/1334438/1334438_1_500.jpg");
        urls.add("https://image.msscdn.net/images/goods_img/20200304/1334438/1334438_1_500.jpg");
        urls.add("https://image.msscdn.net/images/goods_img/20200304/1334438/1334438_1_500.jpg");

        ImageView[] imageViews=new ImageView[4];
        imageViews[0]= findViewById(R.id.iMageView1);
        imageViews[1] = findViewById(R.id.iMageView2);
        imageViews[2] = findViewById(R.id.iMageView3);
        imageViews[3] = findViewById(R.id.iMageView4);

        for(int i=0;i<urls.size();i++){
            try{
                bitmaps.add(new GetImageFromUrl(imageViews[i]).execute(urls.get(i)).get());
                Log.e("Bitmaps", bitmaps.get(i).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000*5); //2초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000*5); //2초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //룩북 개수만큼->for문으로 나중에 작성하기
        try{
            new SaveBitmap(imageViews[1]).execute(combineImageIntoOne(bitmaps));
            //Log.e("Bitmaps", finalBitmap.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public class GetImageFromUrl extends AsyncTask< String, Bitmap, Bitmap >{
        ImageView imageView;
        Bitmap bitmap;

        public GetImageFromUrl(ImageView img){
            this.imageView = img;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            InputStream inputStream;
            try {
                Log.e("URL", url[0]);
                inputStream = new URL(url[0]).openStream();
                bitmap=BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        public void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    // combineImageIntoOne(a);

    // Cobine Multi Image Into One
    private Bitmap combineImageIntoOne(ArrayList<Bitmap> bitmaps) {
        /*
        if(bitmaps.size()==1){
            Bitmap bitmap=sc
        }
        else if(bitmaps.size()==2){

        }
        else if(bitmaps.size()==3){

        }
        else if(bitmaps.size()==4){

        }
        */
        int w = 0, h = 0;
        for (int i = 0; i < bitmaps.size(); i++) {
            Log.d("bitmap 사이즈", String.valueOf(bitmaps.size()));
            if (i < bitmaps.size() - 1) {
                w = bitmaps.get(i).getWidth() > bitmaps.get(i + 1).getWidth() ? bitmaps.get(i).getWidth() : bitmaps.get(i + 1).getWidth();
            }
            h += bitmaps.get(i).getHeight();
        }

        Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(temp);
        int top = 0;
        for (int i = 0; i < bitmaps.size(); i++) {
            //Log.d("HTML", "Combine: " + i + "/" + bitmaps.size() + 1);
            Log.d("HTML", "Combine: " + i + "/" + bitmaps.size());
            top = (i == 0 ? 0 : top + bitmaps.get(i).getHeight());
            canvas.drawBitmap(bitmaps.get(i), 0f, top, null);
        }
        Log.d("bitmap temp", temp.toString());
        return temp;
    }

    //코디 합친 bitmap을 png파일로 저장
    public class SaveBitmap extends AsyncTask<Bitmap, Void, Pair<File, Exception>> {
        ImageView imageView;
        //private final WeakReference<CutOutActivity> activityWeakReference;
        // CutOutActivity activity;
        //ProgressDialog dialog; //progress bar

        SaveBitmap(ImageView img) {
            this.imageView=img;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Pair<File, Exception> doInBackground(Bitmap... bitmap) {
            //try 전까지 내가 추가
            File path = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM), "LookUP");
            if (!path.exists())
                path.mkdir();
            Log.d("SaveBitmapPath", String.valueOf(path));

            try {
                File file=new File(path+"/"+makeName()+".png");
                Log.d("SaveBitmapFilePath", String.valueOf(file));

                try (FileOutputStream out = new FileOutputStream(file)) {
                    bitmap[0].compress(Bitmap.CompressFormat.PNG, 100, out);
                    return new Pair<>(file, null);
                }
            } catch (IOException e) { //IOException e 임
                e.printStackTrace();
                return new Pair<>(null, e);
            }
        }

        protected void onPostExecute(Pair<File, Exception> result) {
            super.onPostExecute(result);

            if (result.first != null) {
                uris.add(Uri.fromFile(result.first));
                imageView.setImageURI(Uri.fromFile(result.first));

            } else {
                Toast.makeText(MergeActivity.this, "오류입니다!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private String makeName(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String time = mFormat.format(date);
        return "LookUP_LookBook"+time;
    }


    /*
    public class GetImageFromUrl extends AsyncTask< ArrayList<String>, Void, Bitmap >{
        ImageView imageView;

        public GetImageFromUrl(ImageView img){
        }

        @Override
        protected Bitmap doInBackground(ArrayList<String>... url) {
            InputStream inputStream;
            try {
                for(int i=0;i<url[0].size();i++){
                    Log.e("URL", url[0].get(i));
                    inputStream = new URL(url[0].get(i)).openStream();
                    bitmaps.add(i, BitmapFactory.decodeStream(inputStream));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmaps.get(0);
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            for(int i=0 ;i<bitmaps.size();i++){
                Log.e("URL", bitmaps.get(i).toString());
            }
        }
    }

     */
}

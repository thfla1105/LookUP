package LookBook.activity;
//import com.mvpstars.reactnative.mergeimages.RNMergeImagesPackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

public class MergeActivity2 extends AppCompatActivity{
    //ImageView image;
    //ImageView image2;
    //ImageView image3;
    //ImageView image4;
    Paint paint;
    ArrayList<String> urls=new ArrayList<>(); //옷 코디 조합(하나임)
    ArrayList<Bitmap> bitmaps=new ArrayList<>(); //각 옷의 url에 대한 비트맵 저장
    ArrayList<Uri> uris = new ArrayList<>(); //룩북의 uri들 (룩북 2개 또는 3개 예정)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook_activity_merge);

        paint = new Paint();
        paint.setColor(Color.WHITE);

        //urls에 url들 채우기
        urls.add("https://image.msscdn.net/images/goods_img/20190404/1005683/1005683_5_500.jpg");
        //urls.add("https://contents.lotteon.com/itemimage/_v144957/LO/15/04/04/20/12/_1/50/40/42/01/3/LO1504042012_1504042013_1.jpg");
        //urls.add("https://img.babathe.com/productimage/VEwxUDFDRDA=TL1P1CD0172_11_682x1000.jpg");
        //urls.add("https://s3.ap-northeast-2.amazonaws.com/productmain/20210204_637479696607137904_185209_0.jpg");



        //urls.add("https://image.msscdn.net/images/goods_img/20180914/858911/858911_6_500.jpg");
        //urls.add("https://image.msscdn.net/images/goods_img/20190802/1108007/1108007_2_500.jpg");
        //urls.add("https://image.msscdn.net/images/goods_img/20210128/1766669/1766669_2_500.jpg");

        //imageview들
        ImageView[] imageViews=new ImageView[4];
        imageViews[0]= findViewById(R.id.iMageView1);
        imageViews[1] = findViewById(R.id.iMageView2);
        imageViews[2] = findViewById(R.id.iMageView3);
        imageViews[3] = findViewById(R.id.iMageView4);

        //url들 bitmap으로 변환하고 bitmaps에 저장
        for(int i=0;i<urls.size();i++){
            try{
                bitmaps.add(new GetImageFromUrl(imageViews[i]).execute(urls.get(i)).get());
                Log.e("Bitmaps", bitmaps.get(i).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        //2초 쉬기
        try {
            Thread.sleep(1000*2); //2초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //룩북 생성하기
        //룩북 개수만큼->for문으로 나중에 작성하기
        try{
            new SaveBitmap(imageViews[1]).execute(combineImageIntoOne(bitmaps)); //이거 결과가 룩북임!!
            //Log.e("Bitmaps", finalBitmap.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    //url을 bitmap으로 -> url 한개씩 넣어야함, 여러개 하려면 위에서 for문으로 사용
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


    // 여러 bitmap들 하나로 합치기(룩북 생성하는 부분)
    private Bitmap combineImageIntoOne(ArrayList<Bitmap> bitmaps) {
        int w = 0, h = 0;
        int firstWidth=bitmaps.get(0).getWidth();
        int secondWidth=bitmaps.get(0).getWidth();
        int firstHeight=bitmaps.get(0).getHeight();
        int secondHeight=bitmaps.get(0).getHeight();

        //첫번째로 큰 width, height와 두번째로 큰 width, height 구하기
        //이걸로 캔버스 사이즈 결정
        for (int i = 0; i < bitmaps.size(); i++) {
            if(bitmaps.get(i).getWidth()>=firstWidth){
                secondWidth=firstWidth;
                firstWidth=bitmaps.get(i).getWidth();
            }
            else if(bitmaps.get(i).getWidth()>secondWidth){
                secondWidth=bitmaps.get(i).getWidth();
            }

            if(bitmaps.get(i).getHeight()>=firstHeight){
                secondHeight=firstHeight;
                firstHeight=bitmaps.get(i).getHeight();
            }
            else if(bitmaps.get(i).getHeight()>secondHeight){
                secondHeight=bitmaps.get(i).getHeight();
            }

            w=firstWidth+secondWidth;
            h=firstHeight+secondHeight;
        }

        //캔버스 사이즈
        Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(temp);
        canvas.drawColor(Color.WHITE);

        //코디의 옷 개수마다 다름(상의, 하의면 bitmaps size=2)
        if(bitmaps.size()==1){
            canvas.drawBitmap(bitmaps.get(0), bitmaps.get(0).getWidth()/2, bitmaps.get(0).getHeight()/2, paint);
        }
        else if(bitmaps.size()==2){
            canvas.drawBitmap(bitmaps.get(0), 0f, h/4, paint);
            canvas.drawBitmap(bitmaps.get(1), bitmaps.get(0).getWidth(), h/4, paint);
        }
        else if(bitmaps.size()==3){
            int leftWidth=0; //왼쪽 오른쪽 중에 왼쪽 width
            //dress 포함 여부 확인해야함->지금은 구현 안함, 이 버전은 상하의아우터 정도?
            canvas.drawBitmap(bitmaps.get(0), 0f, 0f, paint);
            canvas.drawBitmap(bitmaps.get(1), 0f, bitmaps.get(0).getHeight(), paint);
            if(bitmaps.get(0).getWidth()>=bitmaps.get(1).getWidth()){
                leftWidth=bitmaps.get(0).getWidth();
            }
            else{
                leftWidth=bitmaps.get(1).getWidth();
            }
            canvas.drawBitmap(bitmaps.get(2), leftWidth, h/4, paint);
        }
        else if(bitmaps.size()==4){
            int leftWidth=0; //왼쪽 오른쪽 중에 왼쪽 width
            canvas.drawBitmap(bitmaps.get(0), 0f, 0f, paint);
            canvas.drawBitmap(bitmaps.get(1), 0f, bitmaps.get(0).getHeight(), paint);
            if(bitmaps.get(0).getWidth()>=bitmaps.get(1).getWidth()){
                leftWidth=bitmaps.get(0).getWidth();
            }
            else{
                leftWidth=bitmaps.get(1).getWidth();
            }
            canvas.drawBitmap(bitmaps.get(2), leftWidth, 0f, paint);
            canvas.drawBitmap(bitmaps.get(3), leftWidth, bitmaps.get(2).getHeight(), paint);
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
                Toast.makeText(MergeActivity2.this, "오류입니다!", Toast.LENGTH_SHORT).show();
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

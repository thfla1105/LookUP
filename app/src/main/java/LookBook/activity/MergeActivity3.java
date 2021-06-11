package LookBook.activity;
//import com.mvpstars.reactnative.mergeimages.RNMergeImagesPackage;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import androidx.core.content.FileProvider;

import Category.activity.CategoryActivity;
import Login_Main.activity.JoinActivity;
import Login_Main.activity.MainActivity;
import LookBook.ImageAdapter.ImageAdapter_temp;
import LookBook.ImageAdapter.ListItem_temp;

public class MergeActivity3 extends AppCompatActivity{
    //ImageView image;
    //ImageView image2;
    //ImageView image3;
    //ImageView image4;
    Paint paint;
    ArrayList<String> urls=new ArrayList<>(); //옷 코디 조합(하나임)
    ArrayList<String> urls2=new ArrayList<>(); //옷 코디 조합(하나임)
    ArrayList<Bitmap> bitmaps=new ArrayList<>(); //각 옷의 url에 대한 비트맵 저장
    ArrayList<Bitmap> bitmaps2=new ArrayList<>(); //각 옷의 url에 대한 비트맵 저장
    ArrayList<Uri> uris = new ArrayList<>(); //룩북의 uri들 (룩북 2개 또는 3개 예정)

    ProgressDialog serverDialog; //원형 progress bar
    GridView gridView;
    ImageAdapter_temp adapter;

    private RadioGroup mSelectGroupView;
    private RadioButton mSelectButtonView;
    private Button mOkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook_activity_merge3);

        serverDialog = new ProgressDialog(MergeActivity3.this);
        serverDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progress bar가 동그란 형태
        serverDialog.setMessage("룩북을 생성중입니다.");

        serverDialog.show();

        /*
        mSelectGroupView = (RadioGroup) findViewById(R.id.lookbook_select);
        mSelectGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mSelectButtonView=(RadioButton) findViewById(i);
                Toast.makeText(MergeActivity3.this, mSelectButtonView.getText(), Toast.LENGTH_SHORT).show();
                //RadioResult=mGenderButtonView.getText().toString();
                /*
                if(i==R.id.btn_female){
                    Toast.makeText(getApplicationContext(), "여성", Toast.LENGTH_SHORT).show();
                }
                else if(i==R.id.btn_male){
                    Toast.makeText(getApplicationContext(), "남성", Toast.LENGTH_SHORT).show();
                }

                 }
            }
        );

        mOkBtn = (Button) findViewById(R.id.OK_btn);
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSelectButtonView.getText() !=null){
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MergeActivity3.this, "버튼을 선택해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        }); */

        gridView=findViewById(R.id.GridViewLayout);
        adapter=new ImageAdapter_temp();




        paint = new Paint();
        paint.setColor(Color.WHITE);

        //urls에 url들 채우기

        urls.add("https://lookup.run.goorm.io/upload/testid/testid_20210522_153850.png");
        urls.add("https://lookup.run.goorm.io/upload/testid/testid_20210522_162605.png");

        urls2.add("https://lookup.run.goorm.io/upload/testid/testid_20210522_153901.png");
        urls2.add("https://lookup.run.goorm.io/upload/testid/testid_20210522_162605.png");
        urls2.add("https://lookup.run.goorm.io/upload/testid/testid_20210522_162721.png");
        /*urls.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_153840.png");
        urls.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_162605.png");
        urls.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_162721.png");

        urls2.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_153910.png");
        urls2.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_162605.png");
        //urls2.add("https://image.msscdn.net/images/goods_img/20190404/1005683/1005683_5_500.jpg");
        //urls2.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_162851.png");

        //urls.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_153855.png");
        //urls.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_162605.png");
       // urls.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_162721.png");

       // urls2.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_153805.png");
       // urls2.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_162657.png");
        //urls2.add("https://image.msscdn.net/images/goods_img/20190404/1005683/1005683_5_500.jpg");
        //urls2.add("https://lookup.run.goorm.io/upload/ewhacse/ewhacse_20210522_162851.png");



        //urls.add("https://image.msscdn.net/images/goods_img/20190404/1005683/1005683_5_500.jpg");
        //urls.add("https://contents.lotteon.com/itemimage/_v144957/LO/15/04/04/20/12/_1/50/40/42/01/3/LO1504042012_1504042013_1.jpg");
        //urls.add("https://img.babathe.com/productimage/VEwxUDFDRDA=TL1P1CD0172_11_682x1000.jpg");
        //urls.add("https://s3.ap-northeast-2.amazonaws.com/productmain/20210204_637479696607137904_185209_0.jpg");

        //urls2.add("https://image.msscdn.net/images/goods_img/20190404/1005683/1005683_5_500.jpg");
        //urls.add("https://contents.lotteon.com/itemimage/_v144957/LO/15/04/04/20/12/_1/50/40/42/01/3/LO1504042012_1504042013_1.jpg");
        //urls.add("https://img.babathe.com/productimage/VEwxUDFDRDA=TL1P1CD0172_11_682x1000.jpg");
        //urls.add("https://s3.ap-northeast-2.amazonaws.com/productmain/20210204_637479696607137904_185209_0.jpg");



        //urls.add("https://image.msscdn.net/images/goods_img/20180914/858911/858911_6_500.jpg");
        //urls.add("https://image.msscdn.net/images/goods_img/20190802/1108007/1108007_2_500.jpg");
        //urls.add("https://image.msscdn.net/images/goods_img/20210128/1766669/1766669_2_500.jpg");

        //imageview들
        //ImageView[] imageViews=new ImageView[4];
        //imageViews[0]= findViewById(R.id.iMageView1);
        //imageViews[1] = findViewById(R.id.iMageView2);
        //imageViews[2] = findViewById(R.id.iMageView3);
        //imageViews[3] = findViewById(R.id.iMageView4);

        //url들 bitmap으로 변환하고 bitmaps에 저장
        for(int i=0;i<urls.size();i++){
            try{
                bitmaps.add(new GetImageFromUrl().execute(urls.get(i)).get());
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

        //url들 bitmap으로 변환하고 bitmaps에 저장
        for(int i=0;i<urls2.size();i++){
            try{
                bitmaps2.add(new GetImageFromUrl().execute(urls2.get(i)).get());
                Log.e("Bitmaps", bitmaps2.get(i).toString());
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
            new SaveBitmap().execute(combineImageIntoOne(bitmaps)); //이거 결과가 룩북임!!
            //Log.e("Bitmaps", finalBitmap.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000*5); //2초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try{
            new SaveBitmap().execute(combineImageIntoOne(bitmaps2)); //이거 결과가 룩북임!!
            //Log.e("Bitmaps", finalBitmap.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

/*
        //2초 쉬기
        try {
            Thread.sleep(1000*2); //2초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

 */
/*
        Log.e("BITMAPS 1", bitmaps.get(0).toString());
        Log.e("BITMAPS 2", bitmaps.get(1).toString());
        Log.e("BITMAPS 3", bitmaps.get(2).toString());
        Log.e("BITMAPS2 1",  bitmaps2.get(0).toString());
        Log.e("BITMAPS2 2",  bitmaps2.get(1).toString());
        Log.e("BITMAPS2 3",  bitmaps2.get(2).toString());

        Log.e("URI1", uris.get(0).toString());
        //Log.e("URI2", uris.get(1).toString());
        adapter.addItem(new ListItem_temp(uris.get(0), "1"));
        //adapter.addItem(new ListItem_temp(uris.get(1), "2"));
        gridView.setAdapter(adapter);

 */

        if(serverDialog !=null){ //progress bar 닫기
            serverDialog.dismiss();
        }
    }

    //url을 bitmap으로 -> url 한개씩 넣어야함, 여러개 하려면 위에서 for문으로 사용
    public class GetImageFromUrl extends AsyncTask< String, Bitmap, Bitmap >{
        //ImageView imageView;
        Bitmap bitmap;

        //public GetImageFromUrl(ImageView img){
            //this.imageView = img;
        //}

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
            //imageView.setImageBitmap(bitmap);
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
        //ImageView imageView;
        //private final WeakReference<CutOutActivity> activityWeakReference;
        // CutOutActivity activity;
        //ProgressDialog dialog; //progress bar

        //SaveBitmap(ImageView img) {
           // this.imageView=img;
        //}

        SaveBitmap() {

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
                //File file = File.createTempFile(makeName() , ".png" , getApplicationContext().getCacheDir() );
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
                //FileProvider.getUriForFile(MergeActivity3.this,
                  //      getApplicationContext().getPackageName() + ".fileprovider", result.first);
                Log.e("URI_SAVEMITMAP", Uri.fromFile(result.first).toString());
                adapter.addItem(new ListItem_temp(Uri.fromFile(result.first), String.valueOf(uris.size())));
                gridView.setAdapter(adapter);
               // imageView.setImageURI(Uri.fromFile(result.first));

            } else {
                Log.e("URI_SAVEMITMAP_FAIL", Uri.fromFile(result.first).toString());
                Toast.makeText(MergeActivity3.this, "오류입니다!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private String makeName(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = mFormat.format(date);
        return "LookUP_LookBook"+time;
    }

    @Override
    public void onDestroy(){
        if(serverDialog !=null && serverDialog.isShowing()){
            serverDialog.dismiss();
        }
        super.onDestroy();
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

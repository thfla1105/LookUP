package LookBook.activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Closet.activity.TopActivity;
import Cookie.SaveSharedPreference;
import Login_Main.activity.MainActivity;
import LookBook.ImageAdapter.ImageAdapter;
import LookBook.ImageAdapter.ImageAdapter_temp;
import LookBook.ImageAdapter.ListItem_temp;
import LookBook.LookBookData.CoordiFiveData;
import LookBook.LookBookResultData.LookBookResultData;
import LookBook.LookBookResultData.LookBookResultResponse;
import network.RetrofitClient;
import network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LookBookResultActivity extends AppCompatActivity {
    ArrayList<CoordiFiveData> coordiFiveDataArrayList=new ArrayList<>(); //LookBookActivity에서 받아 온 코디 리스트
    ArrayList<CoordiFiveData> urlsList=new ArrayList<>(); //여러 코디 조합의 각 url들
    ArrayList<ArrayList<Bitmap>> bitmapsList=new ArrayList<>(); //여러 코디 조합의 각 bitmap들

    ArrayList<String> urls=new ArrayList<>(); //옷 코디 조합(하나임)
    ArrayList<String> urls2=new ArrayList<>(); //옷 코디 조합(하나임)

    ArrayList<Bitmap> bitmaps1=new ArrayList<>(); //각 옷의 url에 대한 비트맵 저장
    ArrayList<Bitmap> bitmaps2=new ArrayList<>(); //각 옷의 url에 대한 비트맵 저장

    private ServiceApi service;

    Context context;
    Paint paint;
    //ArrayList<String> urls=new ArrayList<>(); //옷 코디 조합(하나)
    //ArrayList<Bitmap> bitmaps=new ArrayList<>(); //각 옷의 url에 대한 비트맵 저장
    ArrayList<Uri> uris = new ArrayList<>(); //룩북의 uri들 (룩북 2개 또는 3개 예정)

    //imageview들
    ImageView[] imageViews=new ImageView[4];
    //imageViews[0]= findViewById(R.id.iMageView1);
    //imageViews[1] = findViewById(R.id.iMageView2);
    //imageViews[2] = findViewById(R.id.iMageView3);
    //imageViews[3] = findViewById(R.id.iMageView4);

    GridView gridView;
    ImageAdapter_temp imageAdapter;
    ProgressDialog serverDialog; //원형 progress bar

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook_activity_result);
        service= RetrofitClient.getClient().create(ServiceApi.class);
        id= (SaveSharedPreference.getString(getApplicationContext(), "ID"));

        serverDialog = new ProgressDialog(LookBookResultActivity.this);
        serverDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progress bar가 동그란 형태
        serverDialog.setMessage("룩북을 생성중입니다.");

        Intent intent = getIntent(); /*데이터 수신*/
        //coordiFiveDataArrayList = (ArrayList<CoordiFiveData>) intent.getExtras().getSerializable("coordiFiveDataList");
        urlsList = (ArrayList<CoordiFiveData>) intent.getExtras().getSerializable("urlsList");
       // Log.e("coordiFiveDataList0", coordiFiveDataArrayList.get(0).getTop());
     //   Log.e("coordiFiveDataList1", coordiFiveDataArrayList.get(1).getBottom());

        paint = new Paint();
        paint.setColor(Color.WHITE);

        gridView=findViewById(R.id.GridViewLayout);
        imageAdapter=new ImageAdapter_temp();
        /*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgUri = imageAdapter.itemList.get(position).getUri().toString();
                Log.d("uri:::::: ", imgUri);
                Intent intent = new Intent(context, LookBookFullImageActivity.class);
                intent.putExtra("uri", imgUri);
                context.startActivity(intent);
            }
        });
         */

        serverDialog.show();
        Timer timer = new Timer();

        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                mainFunc();
                timer.cancel();//타이머 종료
                if(serverDialog !=null){ //progress bar 닫기
                    serverDialog.dismiss();
                }
            }
        };

        timer.schedule(TT, 2000, 1000*60*60); //Timer 실행

    }

    public void mainFunc(){
        if(!urlsList.get(0).getTop().equals("x")){
            try{
                bitmaps1.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(0).getTop()).get());
                Log.e("Bitmaps", bitmaps1.get(0).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!urlsList.get(0).getBottom().equals("x")){
            try{
                bitmaps1.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(0).getBottom()).get());
                Log.e("Bitmaps", bitmaps1.get(0).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!urlsList.get(0).getOuter().equals("x")){
            try{
                bitmaps1.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(0).getOuter()).get());
                Log.e("Bitmaps", bitmaps1.get(0).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!urlsList.get(0).getDress().equals("x")){
            try{
                bitmaps1.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(0).getDress()).get());
                Log.e("Bitmaps", bitmaps1.get(0).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!urlsList.get(0).getAcc().equals("x")){
            try{
                bitmaps1.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(0).getAcc()).get());
                Log.e("Bitmaps", bitmaps1.get(0).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


///////

        if(!urlsList.get(1).getTop().equals("x")){
            try{
                bitmaps2.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(1).getTop()).get());
                Log.e("Bitmaps", bitmaps2.get(1).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!urlsList.get(1).getBottom().equals("x")){
            try{
                bitmaps2.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(1).getBottom()).get());
                Log.e("Bitmaps", bitmaps2.get(1).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!urlsList.get(1).getOuter().equals("x")){
            try{
                bitmaps2.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(1).getOuter()).get());
                Log.e("Bitmaps", bitmaps2.get(1).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!urlsList.get(1).getDress().equals("x")){
            try{
                bitmaps2.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(1).getDress()).get());
                Log.e("Bitmaps", bitmaps2.get(1).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!urlsList.get(1).getAcc().equals("x")){
            try{
                bitmaps2.add(new LookBookResultActivity.GetImageFromUrl().execute(urlsList.get(1).getAcc()).get());
                Log.e("Bitmaps", bitmaps2.get(1).toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


//////
        try {
            Thread.sleep(1000*5); //2초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //룩북 생성하기
        //룩북 개수만큼->for문으로 나중에 작성하기
        try{
            new SaveBitmap().execute(combineImageIntoOne(bitmaps1)); //이거 결과가 룩북임!!
            //Log.e("Bitmaps", finalBitmap.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }

        /*
        try {
            Thread.sleep(1000*5); //2초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */

        try{
            new SaveBitmap().execute(combineImageIntoOne(bitmaps2)); //이거 결과가 룩북임!!
            //Log.e("Bitmaps", finalBitmap.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }


        try {
            Thread.sleep(1000*2); //2초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy(){
        if(serverDialog !=null && serverDialog.isShowing()){
            serverDialog.dismiss();
        }
        super.onDestroy();
    }

    public void startGetUrls(LookBookResultData data){
        service.getUrlsList(data).enqueue(new Callback<LookBookResultResponse>() {
            @Override
            public void onResponse(Call<LookBookResultResponse> call, Response<LookBookResultResponse> response) {
                LookBookResultResponse result = response.body();
                //Toast.makeText(LookBookResultActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                //showProgress(false);
                if(response.isSuccessful()) {
                    Log.e("룩북 StyleList api1", response.toString());
                    Log.e("Url-top", result.getTop());
                    Log.e("Url-bottom", result.getBottom());
                    Log.e("Url-outer", result.getOuter());
                    Log.e("Url-dress", result.getDress());
                    Log.e("Url-acc", result.getAcc());

                    if (result.getTop().equals("1")) {
                        Toast.makeText(LookBookResultActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookResultActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (result.getBottom().equals("1")) {
                        Toast.makeText(LookBookResultActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookResultActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (result.getOuter().equals("1")) {
                        Toast.makeText(LookBookResultActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookResultActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (result.getDress().equals("1")) {
                        Toast.makeText(LookBookResultActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookResultActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (result.getAcc().equals("1")) {
                        Toast.makeText(LookBookResultActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookResultActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    urlsList.add(new CoordiFiveData(result.getTop(), result.getBottom(), result.getOuter(), result.getDress(), result.getAcc())); //받아온 url들
                }
            }

            @Override
            public void onFailure(Call<LookBookResultResponse> call, Throwable t) {
                Toast.makeText(LookBookResultActivity.this, "룩북 서버 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("룩북 서버 에러 발생", t.getMessage());
                //showProgress(false);
            }
        });
    }



    //url을 bitmap으로 -> url 한개씩 넣어야함, 여러개 하려면 위에서 for문으로 사용
    public class GetImageFromUrl extends AsyncTask< String, Bitmap, Bitmap > {
        //ImageView imageView;
        Bitmap bitmap;
        /*
        public GetImageFromUrl(ImageView img){
            this.imageView = img;
        }
 */
        public GetImageFromUrl(){

        }

        @Override
        protected Bitmap doInBackground(String... url) {
            InputStream inputStream;
            try {
                Log.e("URL", url[0]);
                inputStream = new URL(url[0]).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
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
            canvas.drawBitmap(bitmaps.get(1), 0f, 0f, paint);
            canvas.drawBitmap(bitmaps.get(0), bitmaps.get(1).getWidth(), 0f, paint);
            canvas.drawBitmap(bitmaps.get(2), bitmaps.get(1).getWidth(), bitmaps.get(0).getHeight(), paint);
            //dress 포함 여부 확인해야함->지금은 구현 안함, 이 버전은 상하의아우터 정도?
            /*
            canvas.drawBitmap(bitmaps.get(0), 0f, 0f, paint);
            canvas.drawBitmap(bitmaps.get(1), 0f, bitmaps.get(0).getHeight(), paint);
            if(bitmaps.get(0).getWidth()>=bitmaps.get(1).getWidth()){
                leftWidth=bitmaps.get(0).getWidth();
            }
            else{
                leftWidth=bitmaps.get(1).getWidth();
            }
            //canvas.drawBitmap(bitmaps.get(2), leftWidth, h/4, paint);
            canvas.drawBitmap(bitmaps.get(2), leftWidth, 0f, paint);
             */
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
        SaveBitmap(){

        }
        /*
        SaveBitmap(ImageView img) {
            this.imageView=img;
        }

         */

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
                //imageView.setImageURI(Uri.fromFile(result.first));
                //imageAdapter=new ImageAdapter(context);
                //Uri uri=Uri.fromFile(result.first);
                //imageAdapter.addList(uri);

                Log.e("URI_SAVEMITMAP", Uri.fromFile(result.first).toString());
                imageAdapter.addItem(new ListItem_temp(Uri.fromFile(result.first), String.valueOf(uris.size())));
                gridView.setAdapter(imageAdapter);

                /*
                imageAdapter.notifyDataSetChanged();
                TopActivity.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setAdapter(imageAdapter);
                        // Stuff that updates the UI

                    }
                });
                 */

            } else {
                Log.e("URI_SAVEMITMAP_FAIL", Uri.fromFile(result.first).toString());
                Toast.makeText(LookBookResultActivity.this, "오류입니다!", Toast.LENGTH_SHORT).show();
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
}

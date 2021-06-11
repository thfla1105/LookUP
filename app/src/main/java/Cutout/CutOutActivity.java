package Cutout;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v4.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.alexvasilkov.gestures.views.interfaces.GestureView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import Category.activity.CategoryActivity;
import Cookie.SaveSharedPreference;
import Cutout.data.InfoData;
import Cutout.data.InfoResponse;
import Login_Main.activity.MainActivity;
import LookBook.activity.LookBookResultActivity;
import network.RetrofitClient;
import network.ServiceApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.R;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.defaults.checkerboarddrawable.CheckerboardDrawable;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
//import static com.github.gabrielbb.cutout.CutOut.CUTOUT_EXTRA_INTRO;
import static Cutout.CutOut.CUTOUT_EXTRA_INTRO;

public class CutOutActivity extends AppCompatActivity {
    //public static final int sub = 11; //다른 액티비티로 넘어갈 때 넘겨주는 상수
    private static final int INTRO_REQUEST_CODE = 4;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private static final int IMAGE_CHOOSER_REQUEST_CODE = 2;
    private static final int CAMERA_REQUEST_CODE = 3;

    private static final String INTRO_SHOWN = "INTRO_SHOWN";
    FrameLayout loadingModal;
    private GestureView gestureView;
    private DrawView drawView;
    private LinearLayout manualClearSettingsLayout;

    private static final short MAX_ERASER_SIZE = 150;
    private static final short BORDER_SIZE = 45;
    private static final float MAX_ZOOM = 4F;
    private ServiceApi service;

   // ProgressDialog serverDialog; //원형 progress bar
    ProgressDialog dialog; //원형 progress bar
    Bitmap bitmap;
    Bitmap bitmap2=null;
    String url;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //serverDialog = new ProgressDialog(CutOutActivity.this);
      //  serverDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progress bar가 동그란 형태
       // serverDialog.setMessage("사진을 저장중입니다.");
        dialog = new ProgressDialog(CutOutActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progress bar가 동그란 형태
        dialog.setMessage("사진을 저장중입니다.");

        service = RetrofitClient.getClient().create(ServiceApi.class);
        setContentView(R.layout.cutout_activity_photo_edit);

        Toolbar toolbar = findViewById(R.id.photo_edit_toolbar);
        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        FrameLayout drawViewLayout = findViewById(R.id.drawViewLayout);

        int sdk = android.os.Build.VERSION.SDK_INT;

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            drawViewLayout.setBackground(CheckerboardDrawable.create());
        } else {
            drawViewLayout.setBackground(CheckerboardDrawable.create());
        }

        SeekBar strokeBar = findViewById(R.id.strokeBar);
        strokeBar.setMax(MAX_ERASER_SIZE);
        strokeBar.setProgress(50);

        gestureView = findViewById(R.id.gestureView);

        drawView = findViewById(R.id.drawView);
        drawView.setDrawingCacheEnabled(true);
        drawView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //drawView.setDrawingCacheEnabled(true);
        drawView.setStrokeWidth(strokeBar.getProgress());

        strokeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                drawView.setStrokeWidth(seekBar.getProgress());
            }
        });

        loadingModal = findViewById(R.id.loadingModal);
        loadingModal.setVisibility(INVISIBLE);

        drawView.setLoadingModal(loadingModal);

        manualClearSettingsLayout = findViewById(R.id.manual_clear_settings_layout);

        setUndoRedo();
        initializeActionButtons();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            if (toolbar.getNavigationIcon() != null) {
                toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }

        }

        id= (SaveSharedPreference.getString(getApplicationContext(), "ID"));
        url="https://lookup.run.goorm.io/upload2bg/"+id+"_removeBg.jpg";
        String imgName=makeImgName(getApplicationContext());
        Button doneButton = findViewById(R.id.done);

        doneButton.setOnClickListener(new View.OnClickListener() { //완료 버튼=>사진 저장됨(LookUP폴더에)
            @Override
            public void onClick(View view) {
                dialog.show();
                startSaveDrawingTask();
                /*
                try {
                    Thread.sleep(1000); //5초 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startUploadInfo(new InfoData(id, imgName));

                 */
                try {
                    Thread.sleep(1000*6); //6초 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //serverDialog.show();
                startUpload();
            }
        });

        if (getIntent().getBooleanExtra(CUTOUT_EXTRA_INTRO, false) && !getPreferences(Context.MODE_PRIVATE).getBoolean(INTRO_SHOWN, false)) {
            Intent intent = new Intent(this, IntroActivity.class);
            startActivityForResult(intent, INTRO_REQUEST_CODE);
        } else {
            start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Uri getExtraSource() {
        return getIntent().hasExtra(CutOut.CUTOUT_EXTRA_SOURCE) ? (Uri) getIntent().getParcelableExtra(CutOut.CUTOUT_EXTRA_SOURCE) : null;
    }

    private void start() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Uri uri = getExtraSource();

            if (getIntent().getBooleanExtra(CutOut.CUTOUT_EXTRA_CROP, false)) {

                CropImage.ActivityBuilder cropImageBuilder;
                if (uri != null) {
                    cropImageBuilder = CropImage.activity(uri);
                } else {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                        cropImageBuilder = CropImage.activity();
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA},
                                CAMERA_REQUEST_CODE);
                        return;
                    }
                }

                cropImageBuilder = cropImageBuilder.setGuidelines(CropImageView.Guidelines.ON);
                cropImageBuilder.start(this);
            } else {
                if (uri != null) {
                    setDrawViewBitmap(uri);
                } else {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                        EasyImage.openChooserWithGallery(this, getString(R.string.image_chooser_message), IMAGE_CHOOSER_REQUEST_CODE);
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA},
                                CAMERA_REQUEST_CODE);
                    }
                }
            }

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_CODE);
        }
    }

    private void startSaveDrawingTask() {
        SaveDrawingTask task = new SaveDrawingTask(this);

        int borderColor;
        if ((borderColor = getIntent().getIntExtra(CutOut.CUTOUT_EXTRA_BORDER_COLOR, -1)) != -1) {
            Bitmap image = BitmapUtility.getBorderedBitmap(this.drawView.getDrawingCache(), borderColor, BORDER_SIZE);
            task.execute(image);
            if(dialog ==null){
                Log.d("progress bar", "progress bar가 사라졌슈");
            }
        } else {
            task.execute(this.drawView.getDrawingCache());
            if(dialog ==null){
                Log.d("progress bar", "progress bar가 사라졌슈");
            }
        }
    }

    //이 함수 내가 추가함, file 이름: id_20210416_235510 이런 형식으로 리턴
    public static String makeImgName(Context context){
        String id= (SaveSharedPreference.getString(context, "ID"));
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String time = mFormat.format(date);
        return id+"_"+time+".png";
    }

    //파일 정보 서버로 전송: 사용자ID(쿠키 이용), 파일 이름
    private void startUploadInfo(InfoData data){
        service.postImageInfo(data).enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(Call<InfoResponse> call, Response<InfoResponse> response) {
                InfoResponse result = response.body();
                Toast.makeText(CutOutActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<InfoResponse> call, Throwable t) {
                Toast.makeText(CutOutActivity.this, "이미지 정보 업로드 오류", Toast.LENGTH_SHORT).show();
                Log.e("이미지 정보 업로드 오류", t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy(){
        //if(serverDialog !=null && serverDialog.isShowing()){
           // serverDialog.dismiss();
        //}
        if(dialog !=null && dialog.isShowing()){
            dialog.dismiss();
        }
        super.onDestroy();
    }

    //사진 파일 서버로 업로드
    private void startUpload() {
        //setDirEmpty();
        //FileList();
        // cacheApplicationData(getApplicationContext());
        //File file = new File(getApplicationContext().getCacheDir(), "cutout_tmp.png");


        Log.d("startUpload", "startUpload 함수 시작은 되는구만");
        File file = getNewestFile();
        String imgName = makeImgName(getApplicationContext());
        Log.d("file", String.valueOf(file));
        //String mimeType = Files.probeContentType(String.valueOf(file));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("upload", imgName, requestBody);
        Log.d("file name", file.getName());

        service.postImage(fileToUpload, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {//ResponseBody result = response.body();
                //Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (response.code() == 200) {
                    if(dialog !=null){
                        dialog.dismiss();
                    }
                    // Toast.makeText(getApplicationContext(), "편집한 사진 업로드 성공!", Toast.LENGTH_SHORT).show();
                    //setDirEmpty();
                    //finish();


                    Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                    intent.putExtra("imgFile",String.valueOf(file));
                    intent.putExtra("imgName",imgName);

                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(dialog !=null){
                    dialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "req fail", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.e("파일 업로드 에러 발생", t.getMessage());
            }
        });
    }

    /*
    //사진 파일 서버로 업로드
    private void startUpload() {
        //setDirEmpty();
        //FileList();
       // cacheApplicationData(getApplicationContext());
        //File file = new File(getApplicationContext().getCacheDir(), "cutout_tmp.png");


        Log.d("startUpload", "startUpload 함수 시작은 되는구만");
        File file = getNewestFile();
        String imgName = makeImgName(getApplicationContext());
        Log.d("file", String.valueOf(file));
        //String mimeType = Files.probeContentType(String.valueOf(file));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("upload", imgName, requestBody);
        Log.d("file name", file.getName());

        service.postImage(fileToUpload, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {//ResponseBody result = response.body();
                //Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (response.code() == 200) {
                    if(dialog !=null){
                        dialog.dismiss();
                   }
                   // Toast.makeText(getApplicationContext(), "편집한 사진 업로드 성공!", Toast.LENGTH_SHORT).show();
                    //setDirEmpty();
                    //finish();


                    Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                    intent.putExtra("imgFile",String.valueOf(file));
                    intent.putExtra("imgName",imgName);

                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(dialog !=null){
                    dialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "req fail", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.e("파일 업로드 에러 발생", t.getMessage());
            }
        });
    }

     */

    public class GetImageFromUrl extends AsyncTask< String, Bitmap, Bitmap > {
        Bitmap bitmap;
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

    public class SaveDrawingTask2 extends AsyncTask< Bitmap, Pair<File, Exception>, Pair<File, Exception> > {

        public SaveDrawingTask2(){

        }
        @Override
        protected Pair<File, Exception> doInBackground(Bitmap... bitmaps) {
            //try 전까지 내가 추가
            File path = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM), "LookUP");
            if (!path.exists())
                path.mkdir();
            Log.d("SaveDrawingTaskPath", String.valueOf(path));

            try {
                File file = new File(path + "/" + makeName() + ".png");

                try (FileOutputStream out = new FileOutputStream(file)) {
                    bitmaps[0].compress(Bitmap.CompressFormat.PNG, 100, out);
                    return new Pair<>(file, null);
                }
            } catch (IOException e) { //IOException e 임
                e.printStackTrace();
                return new Pair<>(null, e);
            }
        }

        protected void onPostExecute(Pair<File, Exception> result) {
            super.onPostExecute(result);
        }
    }

    private String makeName(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String time = mFormat.format(date);
        return "LookUP"+time;
    }

    //크롭한 후 사진 업로드
    private void startUpload2(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri resultUri;
        Log.d("startUpload2", "startUpload2 함수 시작은 되는구만");
        String imgName = makeImgName(getApplicationContext());
        Pair<File, Exception> pairResult=null;
        ////File file = getNewestFile();
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data); //0603 추가함

            resultUri = result.getUri();
            String getUriPath = resultUri.getPath();
            Log.e("data uri", resultUri.toString());

            try{
                bitmap=new CutOutActivity.GetImageFromUrl().execute(resultUri.toString()).get();
                Log.e("Bitmaps", bitmap.toString());
            }
            catch(Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000*2); //2초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try{
                pairResult=new CutOutActivity.SaveDrawingTask2().execute(bitmap).get();
                if(pairResult.first ==null){
                    Toast.makeText(CutOutActivity.this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            //String mimeType = Files.probeContentType(String.valueOf(file));
            //RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), String.valueOf(result));
            Log.e("image file", pairResult.first.toString());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), pairResult.first );
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("upload2bg", imgName, requestBody);

            service.postImage2bg(fileToUpload, requestBody).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {//ResponseBody result = response.body();
                    //Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                    if (response.code() == 200) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        /////Intent intent = new Intent(getApplicationContext(), CutOut_MainActivity.class);
                        /////intent.putExtra("imgFile",String.valueOf(result));
                        /////intent.putExtra("imgName",imgName);
                        /////startActivity(intent);
                        //response.body();
                        Log.e("response body", response.body().toString());
                        try{
                            bitmap2=new CutOutActivity.GetImageFromUrl().execute(url).get();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "req fail", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    Log.e("파일 업로드 에러 발생", t.getMessage());
                }
            });
        }
    }


    private void removebgUpload() {
        //setDirEmpty();
        //FileList();
        // cacheApplicationData(getApplicationContext());
        //File file = new File(getApplicationContext().getCacheDir(), "cutout_tmp.png");

        Log.d("startUpload", "startUpload 함수 시작은 되는구만");
        File file = getNewestFile();
        String imgName = makeImgName(getApplicationContext());
        Log.d("file", String.valueOf(file));
        //String mimeType = Files.probeContentType(String.valueOf(file));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("upload", imgName, requestBody);
        Log.d("file name", file.getName());

        service.postImage(fileToUpload, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {//ResponseBody result = response.body();
                //Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (response.code() == 200) {
                    if(dialog !=null){
                        dialog.dismiss();
                    }
                    // Toast.makeText(getApplicationContext(), "편집한 사진 업로드 성공!", Toast.LENGTH_SHORT).show();
                    //setDirEmpty();
                    //finish();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(dialog !=null){
                    dialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "req fail", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.e("파일 업로드 에러 발생", t.getMessage());
            }
        });
    }





    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "lookup_" + timeStamp + "_";

        // 이미지가 저장될 파일 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/LookUP/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d("createImageFile", "createImageFile : " + image.getAbsolutePath());

        return image;
    }


    //가장 최신 파일 불러오기
    public File getNewestFile() {
        File newestFile = null;
        //String path = getApplicationContext().getCacheDir() + "/"; //캐시 경로임
        File path = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "LookUP");
        File dir = new File(path+"/");
        Log.d("getNewestFile", String.valueOf(path));

        File[] childFileList = dir.listFiles();

        if (dir.exists()) {
            Log.d("path 읽음", String.valueOf(path));
            for (File childFile : childFileList) {
                if (newestFile == null || childFile.lastModified()>newestFile.lastModified()) {
                    newestFile = childFile;
                }
            }
        }
        return newestFile;
    }
/*
    class DialogTask extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(CutOutActivity.this);
        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("잠시만 기다려주세요...");
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < 5; i++) {
                asyncDialog.setProgress(i * 30);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String abc = "완료";
            return abc;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            asyncDialog.dismiss();
            Toast.makeText(CutOutActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
 */

    //디렉토리 밑에 파일들 삭제
    public void setDirEmpty(){
        String path = getApplicationContext().getCacheDir() + "/";
        File dir = new File(path);
        File[] childFileList = dir.listFiles();
        if (dir.exists()) {
            for (File childFile : childFileList) {
                childFile.delete(); //하위 파일
            }
            //dir.delete();
        }
    }

    //파일들 목록 보기
    private List<String> FileList()
    {
        //String path = getApplicationContext().getCacheDir() + "/";
        File path = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "LookUP");

        File directory = new File(String.valueOf(path));
        File[] files = directory.listFiles();
        Log.d("path", String.valueOf(path));
        List<String> filesNameList = new ArrayList<>();

        for (int i=0; i< files.length; i++) {
            filesNameList.add(files[i].getName());
            //Log.d("test", files[i].getName());
        }

        for (int i=0; i< filesNameList.size(); i++) {
            Log.d("test", files[i].getName());
        }
        return  filesNameList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            start();
        } else {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }

    private void activateGestureView() {
        gestureView.getController().getSettings()
                .setMaxZoom(MAX_ZOOM)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f);
    }

    private void deactivateGestureView() {
        gestureView.getController().getSettings()
                .setPanEnabled(false)
                .setZoomEnabled(false)
                .setDoubleTapEnabled(false);
    }

    private void initializeActionButtons() {
        Button autoClearButton = findViewById(R.id.auto_clear_button);
        Button manualClearButton = findViewById(R.id.manual_clear_button);
        Button zoomButton = findViewById(R.id.zoom_button);

        autoClearButton.setActivated(false);
        autoClearButton.setOnClickListener((buttonView) -> {
            if (!autoClearButton.isActivated()) {
                drawView.setAction(DrawView.DrawViewAction.AUTO_CLEAR);
                manualClearSettingsLayout.setVisibility(INVISIBLE);
                autoClearButton.setActivated(true);
                manualClearButton.setActivated(false);
                zoomButton.setActivated(false);
                deactivateGestureView();
            }
        });

        manualClearButton.setActivated(true);
        drawView.setAction(DrawView.DrawViewAction.MANUAL_CLEAR);
        manualClearButton.setOnClickListener((buttonView) -> {
            if (!manualClearButton.isActivated()) {
                drawView.setAction(DrawView.DrawViewAction.MANUAL_CLEAR);
                manualClearSettingsLayout.setVisibility(VISIBLE);
                manualClearButton.setActivated(true);
                autoClearButton.setActivated(false);
                zoomButton.setActivated(false);
                deactivateGestureView();
            }

        });

        zoomButton.setActivated(false);
        deactivateGestureView();
        zoomButton.setOnClickListener((buttonView) -> {
            if (!zoomButton.isActivated()) {
                drawView.setAction(DrawView.DrawViewAction.ZOOM);
                manualClearSettingsLayout.setVisibility(INVISIBLE);
                zoomButton.setActivated(true);
                manualClearButton.setActivated(false);
                autoClearButton.setActivated(false);
                activateGestureView();
            }

        });
    }

    private void setUndoRedo() {
        Button undoButton = findViewById(R.id.undo);
        undoButton.setEnabled(false);
        undoButton.setOnClickListener(v -> undo());
        Button redoButton = findViewById(R.id.redo);
        redoButton.setEnabled(false);
        redoButton.setOnClickListener(v -> redo());

        drawView.setButtons(undoButton, redoButton);
    }

    void exitWithError(Exception e) {
        Intent intent = new Intent();
        intent.putExtra(CutOut.CUTOUT_EXTRA_RESULT, e);
        setResult(CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE, intent);
        finish();
    }

    private void setDrawViewBitmap(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            drawView.setBitmap(bitmap);
        } catch (IOException e) {
            exitWithError(e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == Activity.RESULT_OK) {

                startUpload2(requestCode,resultCode,data); //0603추가함

                Timer timer = new Timer();
                TimerTask TT = new TimerTask() {
                    @Override
                    public void run() {
                        if(bitmap2 !=null) {
                            timer.cancel();//타이머 종료
                            drawView.setBitmap(bitmap2);
                        }
                    }
                };
                timer.schedule(TT, 0, 500); //Timer 실행
                //setDrawViewBitmap(result.getUri());


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                exitWithError(result.getError());
            } else {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        } else if (requestCode == INTRO_REQUEST_CODE) {
            SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
            editor.putBoolean(INTRO_SHOWN, true);
            editor.apply();
            start();
        } else {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    exitWithError(e);
                }

                @Override
                public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                    setDrawViewBitmap(Uri.parse(imageFile.toURI().toString()));
                }

                @Override
                public void onCanceled(EasyImage.ImageSource source, int type) {
                    // Cancel handling, removing taken photo if it was canceled
                    if (source == EasyImage.ImageSource.CAMERA) {
                        File photoFile = EasyImage.lastlyTakenButCanceledPhoto(CutOutActivity.this);
                        if (photoFile != null) photoFile.delete();
                    }

                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
        }
    }

    private void undo() {
        drawView.undo();
    }

    private void redo() {
        drawView.redo();
    }

}
package Cutout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.VISIBLE;

class SaveDrawingTask extends AsyncTask<Bitmap, Void, Pair<File, Exception>> {
    private static final String SAVED_IMAGE_FORMAT = ".png"; //원래 그냥 png였음
    private static final String SAVED_IMAGE_NAME = "LookUP_";
    private final WeakReference<CutOutActivity> activityWeakReference;
   // CutOutActivity activity;
    //ProgressDialog dialog; //progress bar

    SaveDrawingTask(CutOutActivity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    protected void onPreExecute() {
        //dialog = new ProgressDialog(activityWeakReference.get()); //progress bar
        //dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progress bar가 동그란 형태
        //dialog.setMessage("사진을 저장중입니다."); //progress bar
        //dialog.show(); //progress bar 보이기

        super.onPreExecute();
        activityWeakReference.get().loadingModal.setVisibility(VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Pair<File, Exception> doInBackground(Bitmap... bitmaps) {
        //try 전까지 내가 추가
        File path = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "LookUP");
        if (!path.exists())
            path.mkdir();
        Log.d("SaveDrawingTaskPath", String.valueOf(path));

        try {
            File file=new File(path+"/"+makeName()+".png");

            try (FileOutputStream out = new FileOutputStream(file)) {
                bitmaps[0].compress(Bitmap.CompressFormat.PNG, 100, out);
                return new Pair<>(file, null);
            }
        } catch (IOException e) { //IOException e 임
            e.printStackTrace();
            return new Pair<>(null, e);
        }
        /*
        try {
            //activityWeakReference.get().getApplicationContext().getCacheDir()
            File file = File.createTempFile(SAVED_IMAGE_NAME, SAVED_IMAGE_FORMAT, path);

            try (FileOutputStream out = new FileOutputStream(file)) {
                bitmaps[0].compress(Bitmap.CompressFormat.PNG, 100, out);
                return new Pair<>(file, null);
            }
        } catch (IOException e) { //IOException e 임
            return new Pair<>(null, e);
        }
        */

    }

    protected void onPostExecute(Pair<File, Exception> result) {
        super.onPostExecute(result);

        Intent resultIntent = new Intent();

        if (result.first != null) {
            Uri uri = Uri.fromFile(result.first);

            resultIntent.putExtra(CutOut.CUTOUT_EXTRA_RESULT, uri);
            activityWeakReference.get().setResult(Activity.RESULT_OK, resultIntent);
            activityWeakReference.get().finish();

        } else {
            activityWeakReference.get().exitWithError(result.second);
        }
        //dialog.dismiss(); //progress bar
    }

    private String makeName(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String time = mFormat.format(date);
        return "LookUP"+time;
    }
}
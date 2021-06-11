package LookBook.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonArray;
import com.squareup.moshi.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.R;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import Cookie.SaveSharedPreference;
import ImageSelect.SelectActivity;
import Login_Main.activity.LoginActivity;
import Login_Main.activity.MainActivity;
import LookBook.GPSTracker;
import LookBook.LookBookResultData.LookBookResultData;
import LookBook.LookBookResultData.LookBookResultResponse;
import LookBook.currentWeatherData.CurrentBodyData;
import LookBook.currentWeatherData.CurrentItem;
import LookBook.currentWeatherData.CurrentItemsData;
import LookBook.currentWeatherData.CurrentResponseData;
import LookBook.currentWeatherData.CurrentWeatherData;
import LookBook.LookBookData.CoordiData;
import LookBook.LookBookData.CoordiFiveData;
import LookBook.weatherData.BodyData;
import LookBook.weatherData.Item;
import LookBook.weatherData.ItemsData;
import LookBook.weatherData.ResponseData;
import LookBook.weatherData.WeatherData;
import LookBook.network.RetrofitWeather;
import LookBook.network.ServiceApi_Weather;
import network.RetrofitClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import network.ServiceApi;
import LookBook.LookBookData.LookBookData;
import LookBook.LookBookData.LookBookResponse;
import styleList.noticeActivity2;

public class LookBookActivity extends AppCompatActivity {

    private GPSTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE=2001;
    private static final int PERMISSIONS_REQUEST_CODE=100;
    String[] REQUIRED_PERMISSIONS={Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    Context context;
    int nx;
    int ny;
    String address;
    String g_lowTemp="0"; //동네예보
    String g_highTemp="0"; //동네예보
    String g_rainfall="0"; //동네예보
    //String g_temp="0"; //동네예보
    //String g_skyState="0"; //동네예보
    String g_fcstTime="0"; //동네예보

    int g_tempConvert=0;  //초단기실황
    String g_fcstTime2="0"; //초단기실황
    String g_currentTemp="0"; //초단기실황
    String g_currentState="0"; //강수형태(PTY) 코드 : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
    //여기서 비/눈은 비와 눈이 섞여 오는 것을 의미 (진눈개비)

    //동네예보
    TextView textView_address;
    TextView textView_lowTemp;
    TextView textView_highTemp;
    TextView textView_rainfall;
    //TextView textView_temp;
    //TextView textView_skyState;

    //초단기실황
    TextView textView_currentTemp;
    TextView textView_currentState;
    ImageView imageView_currentState;
    Drawable weather_sun;
    Drawable weather_cloud;
    Drawable weather_rain;
    Drawable weather_snow;
    Drawable weather_snow_and_rain;
    Drawable weather_shower;

    //ACC radio
    //private RadioGroup mAccGroupView;
    //private RadioButton mAccButtonView;
    //String accResult;

    //외출목적 chip
    ChipGroup mPurposeChipGroup;
    Chip mPurposeChip;
    int purposeResult=-1;

    //Acc chip
    ChipGroup mAccChipGroup;
    Chip mAccChip;
    String accResult="-1";


    private ServiceApi_Weather service_weather;
    private ServiceApi service_lookup;

    public static int TO_GRID = 0;
    public static int TO_GPS = 1;

    private Handler handler = new Handler();

    ArrayList<CoordiFiveData> coordiFiveDataList=new ArrayList<>();
    String id;
    ArrayList<CoordiFiveData> urlsList=new ArrayList<>(); //여러 코디 조합의 각 url들

    ProgressDialog serverDialog; //원형 progress bar

    /*
    final String [] purpose
            = new String[] {"일상","직장/면접","아르바이트","친구 모임/데이트","운동","기타"};

    final String [] acc
            = new String[] {"악세서리 포함O","악세서리 포함X"};
     */
    private long backPressedTime = 0;
    @Override
    public void onBackPressed() {
        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finishAffinity();
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        backPressedTime = System.currentTimeMillis();
        finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.select_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent=new Intent();
        switch (item.getItemId()){
            case R.id.action_go_main:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookbook_activity);
        service_weather = RetrofitWeather.getClient().create(ServiceApi_Weather.class);
        service_lookup= RetrofitClient.getClient().create(ServiceApi.class);

        id= (SaveSharedPreference.getString(getApplicationContext(), "ID"));

        serverDialog = new ProgressDialog(LookBookActivity.this);
        serverDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progress bar가 동그란 형태
        serverDialog.setMessage("잠시만 기다려주세요");


        weather_sun=getApplicationContext().getResources().getDrawable(R.drawable.weather_sun);
        weather_cloud=getApplicationContext().getResources().getDrawable(R.drawable.weather_cloudy);
        weather_rain=getApplicationContext().getResources().getDrawable(R.drawable.weather_rain);
        weather_snow=getApplicationContext().getResources().getDrawable(R.drawable.weather_snow);
        weather_snow_and_rain=getApplicationContext().getResources().getDrawable(R.drawable.weather_snow_and_rain);
        weather_shower=getApplicationContext().getResources().getDrawable(R.drawable.weather_shower);


        if(!checkLocationServicesStatus()){
            showDialogForLocationServiceSetting();
        }
        else{
            checkRunTimePermission();
        }


        /*
        mPurpose = (Button) findViewById(R.id.purpose_btn);
        mPurpose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ListPurposeClick(v);
            }
        });
         */

        /*
        mAcc = (Button) findViewById(R.id.acc_btn);
        mAcc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ListAccClick(v);
            }
        });

        mAccGroupView = (RadioGroup) findViewById(R.id.acc_group);

        //성별 체크하는 radiobutton
        mAccGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mAccButtonView=(RadioButton) findViewById(i);
                Toast.makeText(LookBookActivity.this, mAccButtonView.getText(), Toast.LENGTH_SHORT).show();
                accResult=mAccButtonView.getText().toString();
            }
        });
         */

        //외출목적 choice chip
        mPurposeChipGroup= findViewById(R.id.chipgroup);
        mPurposeChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes int checkedId){
                mPurposeChip=findViewById(checkedId);
                // Toast.makeText(LookBookActivity.this, mPurposeChip.getText(), Toast.LENGTH_SHORT).show();
                if(mPurposeChip.getText().equals("친구 모임/데이트")){
                    purposeResult=1;
                }
                else if(mPurposeChip.getText().equals("일상")){
                    purposeResult=2;
                }
                else if(mPurposeChip.getText().equals("운동")){
                    purposeResult=3;
                }
                else if(mPurposeChip.getText().equals("직장/면접")){
                    purposeResult=4;
                }
                else if(mPurposeChip.getText().equals("아르바이트")){
                    purposeResult=5;
                }
                else if(mPurposeChip.getText().equals("기타")){
                    purposeResult=6;
                }
                Log.e("PURPOSERESULT", String.valueOf(purposeResult));
            }
        });

        //Acc choice chip
        mAccChipGroup= findViewById(R.id.chipgroup2);
        mAccChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes int checkedId){
                mAccChip=findViewById(checkedId);
                //Toast.makeText(LookBookActivity.this, mAccChip.getText(), Toast.LENGTH_SHORT).show();
                if(mAccChip.getText().equals("비니 포함")){
                    accResult="beanie";
                }
                else if(mAccChip.getText().equals("목도리 포함")){
                    accResult="scarf";
                }
                else if(mAccChip.getText().equals("캡모자 포함")){
                    accResult="cap";
                }
                else if(mAccChip.getText().equals("선택 안함")){
                    accResult="x";
                }
                Log.e("ACCRESULT", String.valueOf(accResult));
            }
        });

        textView_address=(TextView) findViewById(R.id.addressView);
        textView_lowTemp=(TextView) findViewById(R.id.lowTempView);
        textView_highTemp=(TextView) findViewById(R.id.highTempView);
        textView_rainfall=(TextView) findViewById(R.id.rainfallView);
        //textView_temp=(TextView) findViewById(R.id.tempView);
        //textView_skyState=(TextView) findViewById(R.id.skyStateView);

        textView_currentTemp=(TextView) findViewById(R.id.currentTempView);
        textView_currentState=(TextView) findViewById(R.id.currentStateView);
        imageView_currentState=(ImageView) findViewById(R.id.currentStateImageView);
        gpsTracker = new GPSTracker(LookBookActivity.this);
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도

        address = getCurrentAddress(latitude, longitude); //대한민국 서울시 종로구 ~~

        textView_address.setText(address); //주소 보여주는 텍스트뷰

        //Toast.makeText(LookBookActivity.this, "현재 위치\n위도"+latitude
        //   +"\n경도"+longitude, Toast.LENGTH_LONG).show();

        //룩북 생성하기 버튼, 이거 누르면 id, purpose, currentTemp가 서버로 전송되고 코디리스트 결과로 옴
        Button lookbook_btn=(Button) findViewById(R.id.lookbook_btn); //룩북 생성 버튼
        lookbook_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!(purposeResult==-1) && !accResult.equals("-1")){
                    Log.e("startLookBook", id + " / " + purposeResult + " / " + g_tempConvert);
                    serverDialog.show();
                    startLookBook(new LookBookData(id , purposeResult, g_tempConvert));
                    //Intent intent = new Intent(getApplicationContext(), MergeActivity3.class);
                    //startActivity(intent);
                }
                else
                    Toast.makeText(LookBookActivity.this, "선택을 모두 해주세요!", Toast.LENGTH_SHORT).show();
                //ListPurposeClick(v);
            }
        });


        LatXLngY tmp = convertGRID_GPS(TO_GRID, latitude, longitude); //위도, 경도를 기상청 api에 맞게 변환해야 함

        nx=(int)tmp.x;
        ny=(int)tmp.y;
        startWeather(nx, ny); //날씨 api로 갖고온 부분, 동네예보
        //startCurrentWeather(nx, ny); //날씨 api로 갖고온 부분, 초단기실황
        startCounting();

        Log.e(">>", "x = " + tmp.x + ", y = " + tmp.y);
    }

/*
    public void ListPurposeClick(View view) {
        new AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert).setTitle("선택").setItems(purpose, new DialogInterface.OnClickListener()
        {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                //Intent intent = new Intent(getApplicationContext(), MakeLookBook.class);
                //startActivity(intent);
                Toast.makeText(LookBookActivity.this, "words : " + purpose[which], Toast.LENGTH_LONG).show();
            } }).setNeutralButton("닫기", null).show();
    }
 */


    //날씨 정보 30분마다 update
    private void startCounting() {
        handler.post(run);
    }
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            startCurrentWeather(nx, ny); //초단기 실황
            handler.postDelayed(this, 1800000);
        }
    };

    //기온에 따라 index
    public void tempConvert(){
        if(Double.parseDouble(g_currentTemp)<5){
            g_tempConvert=7;
        }
        else if(Double.parseDouble(g_currentTemp)>=5 && Double.parseDouble(g_currentTemp)<9){
            g_tempConvert=6;
        }
        else if(Double.parseDouble(g_currentTemp)>=9 && Double.parseDouble(g_currentTemp)<12){
            g_tempConvert=5;
        }
        else if(Double.parseDouble(g_currentTemp)>=12 && Double.parseDouble(g_currentTemp)<16){
            g_tempConvert=4;
        }
        else if(Double.parseDouble(g_currentTemp)>=16 && Double.parseDouble(g_currentTemp)<19){
            g_tempConvert=3;
        }
        else if(Double.parseDouble(g_currentTemp)>=19 && Double.parseDouble(g_currentTemp)<22){
            g_tempConvert=2;
        }
        else if(Double.parseDouble(g_currentTemp)>=22 && Double.parseDouble(g_currentTemp)<26){
            g_tempConvert=1;
        }
        else if(Double.parseDouble(g_currentTemp)>=26){
            g_tempConvert=0;
        }
        /*
        if(Double.parseDouble(g_currentTemp)<=4){
            g_tempConvert=7;
        }
        else if(Double.parseDouble(g_currentTemp)>=5 && Double.parseDouble(g_currentTemp)<=8){
            g_tempConvert=6;
        }
        else if(Double.parseDouble(g_currentTemp)>=9 && Double.parseDouble(g_currentTemp)<=11){
            g_tempConvert=5;
        }
        else if(Double.parseDouble(g_currentTemp)>=12 && Double.parseDouble(g_currentTemp)<=16){
            g_tempConvert=4;
        }
        else if(Double.parseDouble(g_currentTemp)>=17 && Double.parseDouble(g_currentTemp)<=19){
            g_tempConvert=3;
        }
        else if(Double.parseDouble(g_currentTemp)>=20 && Double.parseDouble(g_currentTemp)<=22){
            g_tempConvert=2;
        }
        else if(Double.parseDouble(g_currentTemp)>=23 && Double.parseDouble(g_currentTemp)<=27){
            g_tempConvert=1;
        }
        else if(Double.parseDouble(g_currentTemp)>=28){
            g_tempConvert=0;
        }
         */
    }

    public void startLookBook(LookBookData data) {
        service_lookup.getCoordiList(data).enqueue(new Callback<LookBookResponse>() {
            @Override
            public void onResponse(Call<LookBookResponse> call, Response<LookBookResponse> response) {
                LookBookResponse result = response.body();
                //Toast.makeText(LookBookActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                //showProgress(false);

                if (response.isSuccessful()) {
                    result = response.body();
                    if(result!=null) {
                        Log.e("룩북 StyleList api1", response.toString());
                        List<CoordiData> coordiDataList = result.getStyleList();
                        Log.e("룩북 StyleList api2", coordiDataList.toString());

                        for (int i = 0; i < coordiDataList.size(); i++) {
                            CoordiData coordiData = coordiDataList.get(i);

                            int idnum = coordiData.getIdnum();
                            String styles = coordiData.getStyles();
                            String dress = coordiData.getDress();
                            String top = coordiData.getTop();
                            String bottom = coordiData.getBottom();
                            String outwear = coordiData.getOutwear();

                            int temp = coordiData.getTemp();
                            int weight = coordiData.getWeight();
                            int count = coordiData.getCount();
                            String coordi_literal = coordiData.getCoordi_literal();

                            Log.e("stylist-LookBookAct", idnum + " / " + styles + " / " + temp + " / " + weight + " / " + count);
                            Log.e("stylist-LookBookAct2", top + " / " + bottom + " / " + dress + " / " + outwear + " / " + coordi_literal);
                            coordiFiveDataList.add(new CoordiFiveData(top, bottom, outwear, dress, accResult));
                        }

                        Log.e("coordiFiveDataList0", coordiFiveDataList.get(0).getTop() + coordiFiveDataList.get(0).getBottom());
                        Log.e("coordiFiveDataList1", coordiFiveDataList.get(1).getTop() + coordiFiveDataList.get(1).getBottom());

                        for (int i = 0; i < coordiFiveDataList.size(); i++) {
                            CoordiFiveData coordiFiveData = coordiFiveDataList.get(i);
                            String top = coordiFiveData.getTop();
                            String bottom = coordiFiveData.getBottom();
                            String outer = coordiFiveData.getOuter();
                            String dress = coordiFiveData.getDress();
                            String acc = coordiFiveData.getAcc();
                            startGetUrls(new LookBookResultData(id, top, bottom, outer, dress, acc)); //서버로 category값들 보냄
                        }
                    }

                    if(serverDialog !=null){ //progress bar 닫기
                        serverDialog.dismiss();
                    }
                    //Intent intent=new Intent(getApplicationContext(), LookBookResultActivity.class);

                    //intent.putExtra("coordiFiveDataList", coordiFiveDataList);
                    //startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LookBookResponse> call, Throwable t) {
                Toast.makeText(LookBookActivity.this, "룩북 서버 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("룩북 서버 에러 발생", t.getMessage());
                //showProgress(false);
            }
        });
    }

    public void startGetUrls(LookBookResultData data){
        service_lookup.getUrlsList(data).enqueue(new Callback<LookBookResultResponse>() {
            @Override
            public void onResponse(Call<LookBookResultResponse> call, Response<LookBookResultResponse> response) {
                LookBookResultResponse result = response.body();
                //Toast.makeText(LookBookActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                //showProgress(false);
                if(response.isSuccessful()) {
                    Log.e("룩북 StyleList api1", response.toString());
                    Log.e("Url-top", result.getTop());
                    Log.e("Url-bottom", result.getBottom());
                    Log.e("Url-outer", result.getOuter());
                    Log.e("Url-dress", result.getDress());
                    Log.e("Url-acc", result.getAcc());

                    if (result.getTop().equals("1")) {
                        Toast.makeText(LookBookActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (result.getBottom().equals("1")) {
                        Toast.makeText(LookBookActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (result.getOuter().equals("1")) {
                        Toast.makeText(LookBookActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (result.getDress().equals("1")) {
                        Toast.makeText(LookBookActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (result.getAcc().equals("1")) {
                        Toast.makeText(LookBookActivity.this, "저장된 옷이 부족하여 룩북을 생성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LookBookActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    urlsList.add(new CoordiFiveData(result.getTop(), result.getBottom(), result.getOuter(), result.getDress(), result.getAcc())); //받아온 url들

                    if(urlsList.size()==2){
                        Intent intent=new Intent(getApplicationContext(), LookBookResultActivity.class);

                        intent.putExtra("urlsList", urlsList);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<LookBookResultResponse> call, Throwable t) {
                Toast.makeText(LookBookActivity.this, "룩북 서버 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("룩북 서버 에러 발생", t.getMessage());
                //showProgress(false);
            }
        });
    }


    //@Override
    public void onRequestPermissionResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults){
        if(permsRequestCode==PERMISSIONS_REQUEST_CODE && grandResults.length==REQUIRED_PERMISSIONS.length){
            boolean check_result=true;

            for(int result : grandResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    check_result=false;
                    break;
                }
            }

            if(check_result){
                ;
            }
            else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(LookBookActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(LookBookActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해주세요",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    void checkRunTimePermission(){
        int hasFineLocationPermission= ContextCompat.checkSelfPermission(LookBookActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission=ContextCompat.checkSelfPermission(LookBookActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED
                && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(LookBookActivity.this,
                    REQUIRED_PERMISSIONS[0])){
                Toast.makeText(LookBookActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(LookBookActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
            else{
                ActivityCompat.requestPermissions(LookBookActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }



    //address 갖고오기(대한민국 서울특별시 어쩌구 어쩌동)
    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    100);
        }
        catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            // showDialogForLocationServiceSetting();
            return "지오코더 서비스 사용불가";
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            // showDialogForLocationServiceSetting();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            //  showDialogForLocationServiceSetting();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        //Log.d(address.getAdminArea() + " / " + address.getLocality() + " / " + address.getThoroughfare());
        //return address.getAddressLine(0).toString()+"\n";
        //return address.getAdminArea() + " / " + address.getLocality() + " / " + address.getThoroughfare();
        //return address.getCountryName()+ " / "+address.getAdminArea()+ " / "+ address.getSubLocality() + " / " + address.getThoroughfare();
        //return address.getAdminArea()+ " "+ address.getSubLocality() + " " + address.getThoroughfare();
        return address.getAdminArea()+ " "+ address.getSubLocality();
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LookBookActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?");
        builder.setCancelable(true); builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }



    public boolean checkLocationServicesStatus(){
        LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    //위도, 경도를 기상청 api에 맞게 변환해야 함
    class LatXLngY
    {
        public double lat;
        public double lng;

        public double x;
        public double y;

    }

    //위도, 경도를 기상청 api에 맞게 변환해야 함
    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y )
    {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

        //
        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
        //


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;
    }

    //동네 예보 api
    public void startWeather(int nx, int ny){
        Date todayDate = new Date(); //오늘 날짜
        Date baseDateNotString = new Date(todayDate.getTime()+(1000*60*60*24*-1)); //어제 날짜, 오늘 날짜 하려면 이거 없애기
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dates=format.format(todayDate); //오늘 날짜 yyyyMMdd형태 String
        //System.out.println(format.format(todayDate)); // 20210220

        SimpleDateFormat format2=new SimpleDateFormat("HH"); //몇 시
        SimpleDateFormat format3=new SimpleDateFormat("mm");// 몇 분
        String hours=format2.format(todayDate); //몇시인지 String, (ex 1, 23)
        String minutes=format3.format(todayDate); //몇분인지 String (ex 28)
        int hour=Integer.parseInt(hours); //몇시인지 정수로 변환
        int min=Integer.parseInt(minutes); //몇분인지 정수로 변환

        //예보시간대(fcstTime) 계산하기
        if(hour>=0 && hour<=2) {
            g_fcstTime="0000";
        }
        if(hour>=3 && hour<=5) {
            g_fcstTime="0300";
        }
        else if(hour>=6 && hour<=8) {
            g_fcstTime="0600";
        }
        else if(hour>=9 && hour<=11) {
            g_fcstTime="0900";
        }
        else if(hour>=12 && hour<=14) {
            g_fcstTime="1200";
        }
        else if(hour>=15 && hour<=17) {
            g_fcstTime="1500";
        }
        else if(hour>=18 && hour<=20) {
            g_fcstTime="1800";
        }
        else if(hour>=21 && hour<=23) {
            g_fcstTime="2100";
        }

        //String serviceKey = "*";
        String pageNum="1";
        String s_nx = String.valueOf(nx);	//위도 변환값
        String s_ny = String.valueOf(ny);	//경도 변환값
        String type = "JSON"; //타입 xml, json 등등 ..
        String baseDate=format.format(baseDateNotString); //조회하고 싶은 날짜 (ex 20210219)-어제 날짜로 해야함
        String baseTime="2000"; //조회하고 싶은 시간 (ex 2300)
        String numOfRows = "153"; //한 페이지 결과 수

        //2000 시간대는 오후 8시 지나면 사라지기 때문에 이걸 조정해줌
        if(hour>=20) {
            baseTime="2300";
        }
        if(hour>=23) {
            baseDate=format.format(todayDate); //오늘날짜로 변경
            baseTime="0200";
        }

        service_weather.getWeather(pageNum, numOfRows, type, baseDate, baseTime, s_nx, s_ny).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherData result = response.body();
                if(response.body() != null) {
                    result = response.body();
                }
                else{
                    Log.e("알림", "값이 없습니다.");
                }
                // Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                if(response.isSuccessful()){
                    Log.e("날씨 동네예보 api1", response.toString());
                    ResponseData responseData=result.getResponse();
                    Log.e("날씨 동네예보 api2", responseData.toString());
                    BodyData bodyData=responseData.getBody();
                    Log.e("날씨 동네예보 api3", bodyData.toString());
                    ItemsData itemsData=bodyData.getItems();
                    Log.e("날씨 동네예보 api4", itemsData.toString());
                    Item[] itemList=itemsData.getItem();
                    Log.e("날씨 동네예보 api5", itemList.toString());
                    /*
                    for(Item item:itemList){
                        result_list.add(item);
                    }
                     */

                    for(int i = 0 ; i < itemList.length; i++) {
                        Item weather = itemList[i];
                        String category=weather.getCategory();
                        String fcstDate=weather.getFcstDate();
                        String fcstTime=weather.getFcstTime();
                        String fcstValue=weather.getFcstValue();

                        if(dates.equals(fcstDate)) {//내가 알고싶은 날짜이면
                            if(category.equals("TMN")) {
                                //info = "아침 최저기온";
                                // DataValue = DataValue+" %";
                                g_lowTemp=fcstValue;
                                //System.out.println(info + ": " + lowTemp);
                            }
                            if(category.equals("TMX")) {
                                //info = "낮 최고기온";
                                // DataValue = DataValue+" %";
                                g_highTemp=fcstValue;
                                //System.out.println(info + ": " + highTemp);
                            }
                            if(g_fcstTime.equals(fcstTime)) { //내가 보고싶은 시간대일 때
                                if (category.equals("POP")) {
                                    //System.out.println("시간대: "+(String)fcstTime);
                                    //g_fcstTime=(String)fcstTime;
                                    //info = "강수확률";
                                    // DataValue = DataValue+" %";
                                    g_rainfall=fcstValue;
                                    //System.out.println(info + ": " + rainfall);
                                }
                                /*
                                if (category.equals("T3H")) {
                                    //info = "3시간기온";
                                    //DataValue = DataValue + " ℃";
                                    g_temp=fcstValue;
                                    //System.out.println(info + ": " + temp);
                                }
                                if(category.equals("SKY")) {
                                    //info = "하늘상태";
                                    if(fcstValue.equals("1")) {
                                        //skyState = "맑음";
                                        g_skyState="1";
                                    }else if(fcstValue.equals("2")) {
                                        //skyState = "비";
                                        g_skyState="2";
                                    }else if(fcstValue.equals("3")) {
                                        //skyState = "구름많음";
                                        g_skyState="3";
                                    }else if(fcstValue.equals("4")) {
                                        //skyState = "흐림";
                                        g_skyState="4";
                                    }
                                    //System.out.println(info + ": " + skyState);
                                }
                                 */
                            }
                        }
                    } //for문 끝

                    textView_lowTemp.setText("최저 : "+g_lowTemp+"°");
                    textView_highTemp.setText("최고 : "+g_highTemp+"° / ");
                    textView_rainfall.setText("강수확률: "+g_rainfall+"%");
                    /*
                    textView_temp.setText("현재 기온: "+g_temp);
                    if(g_skyState.equals("1")){
                        textView_skyState.setText("하늘 상태: 맑음");
                    }
                    else if(g_skyState.equals("2")){
                        textView_skyState.setText("하늘 상태: 비");
                    }
                    else if(g_skyState.equals("3")){
                        textView_skyState.setText("하늘 상태: 구름 많음");
                    }
                    else if(g_skyState.equals("4")){
                        textView_skyState.setText("하늘 상태: 흐림");
                    }
                     */

                }
                //showProgress(false);

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Toast.makeText(LookBookActivity.this, "날씨 api 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("날씨 api 에러 발생", t.getMessage());
                //showProgress(false);
            }
        });

    }

    //초단기 실황 api
    public void startCurrentWeather(int nx, int ny){
        Date todayDate = new Date(); //오늘 날짜
        //Date baseDateNotString = new Date(todayDate.getTime()+(1000*60*60*24*-1)); //어제 날짜, 오늘 날짜 하려면 이거 없애기
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dates=format.format(todayDate); //오늘 날짜 yyyyMMdd형태 String
        //System.out.println(format.format(todayDate)); // 20210220

        SimpleDateFormat format2=new SimpleDateFormat("HH"); //몇 시
        SimpleDateFormat format3=new SimpleDateFormat("mm");// 몇 분
        String hours=format2.format(todayDate); //몇시인지 String, (ex 1, 23)
        String minutes=format3.format(todayDate); //몇분인지 String (ex 28)
        int hour=Integer.parseInt(hours); //몇시인지 정수로 변환
        int min=Integer.parseInt(minutes); //몇분인지 정수로 변환


        String serviceKey = "*";
        String pageNum="1";
        String s_nx = String.valueOf(nx);	//위도 변환값
        String s_ny = String.valueOf(ny);	//경도 변환값
        String type = "JSON"; //타입 xml, json 등등 ..
        String baseDate=format.format(todayDate); //조회하고 싶은 날짜->오늘 날짜!(동네예보와 다름)
        String baseTime; //조회하고 싶은 시간 (ex 2300)
        String numOfRows = "153"; //한 페이지 결과 수

        //예보시간대 계산하기
        if(min<30 && hour !=0){
            hour--;
        }
        if(hour==0 && min<30){
            Date baseDateNotString = new Date(todayDate.getTime()+(1000*60*60*24*-1)); //어제 날짜
            baseDate=format.format(baseDateNotString);
            hour=23;
        }

        if(hour<9){
            g_fcstTime2="0"+hour+"00";
        }
        else{
            g_fcstTime2=hour+"00";
        }
        baseTime=g_fcstTime2;

        service_weather.getCurrentWeather(pageNum, numOfRows, type, baseDate, baseTime, s_nx, s_ny).enqueue(new Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                CurrentWeatherData result = response.body();
                if (response.body() != null) {
                    result = response.body();
                } else {
                    Log.e("알림", "값이 없습니다.");
                }
                // Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()) {
                    Log.e("날씨 초단기 실황 api1", response.toString());
                    CurrentResponseData currentResponseData = result.getResponse();
                    Log.e("날씨 초단기 실황 api2", currentResponseData.toString());
                    CurrentBodyData currentBodyData = currentResponseData.getBody();
                    Log.e("날씨 초단기 실황 api3", currentBodyData.toString());
                    CurrentItemsData currentItemsData = currentBodyData.getItems();
                    Log.e("날씨 초단기 실황 api4", currentItemsData.toString());
                    CurrentItem[] currentItemList = currentItemsData.getItem();
                    /*
                    for(Item item:itemList){
                        result_list.add(item);
                    }
                     */

                    for (int i = 0; i < currentItemList.length; i++) {
                        CurrentItem weather = currentItemList[i];
                        String category = weather.getCategory();
                        String baseDate = weather.getBaseDate();
                        String obsrValue = weather.getObsrValue();

                        if (baseDate.equals(baseDate)) {//내가 알고싶은 날짜이면
                            if (category.equals("T1H")) {
                                //info = "아침 최저기온";
                                // DataValue = DataValue+" %";
                                g_currentTemp = obsrValue;
                                //System.out.println(info + ": " + lowTemp);
                            }
                            if (category.equals("PTY")) {
                                //info = "낮 최고기온";
                                // DataValue = DataValue+" %";
                                g_currentState = obsrValue;
                                //System.out.println(info + ": " + highTemp);
                            }
                        }
                    } //for문 끝

                    textView_currentTemp.setText("현재 : "+g_currentTemp+"°");
                    if(g_currentState.equals("0")){
                        textView_currentState.setText("맑음");
                        imageView_currentState.setImageDrawable(weather_sun);

                    }
                    if(g_currentState.equals("1")){
                        textView_currentState.setText("비");
                        imageView_currentState.setImageDrawable(weather_rain);
                    }
                    else if(g_currentState.equals("2")){
                        textView_currentState.setText("비/눈");
                        imageView_currentState.setImageDrawable(weather_snow_and_rain);
                    }
                    else if(g_currentState.equals("3")){
                        textView_currentState.setText("눈");
                        imageView_currentState.setImageDrawable(weather_snow);
                    }
                    else if(g_currentState.equals("4")){
                        textView_currentState.setText("소나기");
                        imageView_currentState.setImageDrawable(weather_shower);
                    }
                    else if(g_currentState.equals("5")){
                        textView_currentState.setText("빗방울");
                        imageView_currentState.setImageDrawable(weather_shower);
                    }
                    else if(g_currentState.equals("6")){
                        textView_currentState.setText("빗방울/눈날림");
                        imageView_currentState.setImageDrawable(weather_snow_and_rain);
                    }
                    else if(g_currentState.equals("7")){
                        textView_currentState.setText("눈날림");
                        imageView_currentState.setImageDrawable(weather_snow);
                    }

                    tempConvert();
                    //showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                Toast.makeText(LookBookActivity.this, "날씨 api 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("날씨 api 에러 발생", t.getMessage());
                //showProgress(false);
            }
        });

    }

    @Override
    public void onDestroy(){
        if(serverDialog !=null && serverDialog.isShowing()){
            serverDialog.dismiss();
        }
        super.onDestroy();
    }
}

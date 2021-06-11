package Login_Main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import Closet.activity.Closet_MainActivity;
//import petrov.kristiyan.colorpicker_sample.R;
import java.R;

import Color.activity.TopSelect123;
import ColorSpuit.ExampleColorMixing;

import Cutout.CutOut_MainActivity;
import Cookie.SaveSharedPreference;
import ImageSelect.ImageSelectActivity;
import ImageSelect.SelectActivity;
import LookBook.activity.LookBookActivity;
import LookBook.activity.MergeActivity2;
import Support.PermissionSupport;
import styleList.RatingActivity;
import styleList.noticeActivity;
import styleList.routeActivity;

import static Login_Main.activity.LoginActivity.popflag;
import static Login_Main.activity.LoginActivity.popnum;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //private PermissionSupport permission;
    Button mLogoutButton;
    Button mTestButton;
    Button mLoginButton;
    Button mCutoutButton;
    Button mTopSelectButton;
    Button mTopSelect123Button;
    Button mClosetButton;
    Button mStyleListButton;
    Button mColorSpuit;
    Button mImageSelect;
    Button mLookBook;
    Button mLookBook_Merge;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private long backBtnTime = 0;

    private Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permissionCheck();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        //로그인 로그아웃 부분
        Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_login).setVisible(false);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        Log.e("MainActivity popnum: ", String.valueOf(popnum));
        if(popnum==0 && popflag==1){
            Intent intent = new Intent(getApplicationContext(), noticeActivity.class);
            startActivity(intent);
        }
        if(popnum==1&& popflag==1){
            Intent intent = new Intent(getApplicationContext(), noticeActivity.class);
            startActivity(intent);
        }

        mLogoutButton = (Button) findViewById(R.id.logout_btn);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSharedPreference.clear(MainActivity.this);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        /*
        //다른 액티비티로 넘어가기 위한 테스트 버튼
        mTestButton = (Button) findViewById(R.id.test_btn);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(intent);
            }
        });*/

        //cutout으로 넘어가기 위한 버튼
        mCutoutButton = (Button) findViewById(R.id.cutout_btn);
        mCutoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), CropActivity.class);
                Intent intent = new Intent(getApplicationContext(), CutOut_MainActivity.class);
                startActivity(intent);
            }
        });


/*
        mTopSelect123Button = (Button) findViewById(R.id.topselect123_btn);
        mTopSelect123Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TopSelect123.class);
                startActivity(intent);
            }
        });*/

        mClosetButton = (Button) findViewById(R.id.closet_btn);
        mClosetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Closet_MainActivity.class);
                startActivity(intent);
            }
        });

        mStyleListButton = (Button) findViewById(R.id.stylelist_btn);
        mStyleListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
                startActivity(intent);
            }
        });

        /*
        mColorSpuit = (Button) findViewById(R.id.colorspuit_btn);
        mColorSpuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExampleColorMixing.class);
                startActivity(intent);
            }
        });*/

        mImageSelect = (Button) findViewById(R.id.imageSelect_btn);
        mImageSelect .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });

        mLookBook = (Button) findViewById(R.id.lookbook_btn);
        mLookBook .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LookBookActivity.class);
                startActivity(intent);
            }
        });

        /*
        mLookBook_Merge = (Button) findViewById(R.id.lookbook_merge_btn);
        mLookBook_Merge .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MergeActivity2.class);
                startActivity(intent);
            }
        });*/

        /*
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSharedPreference.clear(MainActivity.this);
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(intent);
            }
        });

*/
        //만약 쿠키가 없으면=>로그인 상태가 아니면
        if(SaveSharedPreference.getString(getApplicationContext(), "ID").equals("")) {
            // call Login Activity
            intent = new Intent(getApplicationContext(), LoginActivity.class); //로그인 액티비티로
            startActivity(intent);
            this.finish();
        }

        /*
        if(SaveSharedPreference.getString(getApplicationContext(), "ID").equals("")) {
            // call Login Activity
            mLogoutButton.setEnabled(false);
            //intent = new Intent(getApplicationContext(), LoginActivity.class);
            //startActivity(intent);
            //this.finish();
        }
        else if (SaveSharedPreference.getString(getApplicationContext(), "ID").length()>0) {
            // call Login Activity
            mLoginButton.setEnabled(false);
            //intent = new Intent(getApplicationContext(), LoginActivity.class);
            //startActivity(intent);
            //this.finish();
        }
        */

        /*
        else {
            // Call Next Activity
            intent = new Intent(MainActivity.this, MainActivity.class);
            //intent.putExtra("STD_NUM", SaveSharedPreference.getAttribute(this).toString());
            startActivity(intent);
            this.finish();
        }

         */
    }

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


    }


/*
    // 권한 체크
    private void permissionCheck(){

        // SDK 23버전 이하 버전에서는 Permission이 필요하지 않습니다.
        if(Build.VERSION.SDK_INT >= 23){
            // 방금 전 만들었던 클래스 객체 생성
            permission = new PermissionSupport(this, this);

            // 권한 체크한 후에 리턴이 false로 들어온다면
            if (!permission.checkPermission()){
                // 권한 요청을 해줍니다.
                permission.requestPermission();
            }
        }
    }

    // Request Permission에 대한 결과 값을 받아올 수 있습니다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 여기서도 리턴이 false로 들어온다면 (사용자가 권한 허용을 거부하였다면)
        if(!permission.permissionResult(requestCode, permissions, grantResults)){
            // 저의 경우는 여기서 다시 Permission 요청을 걸었습니다.
            permission.requestPermission();
        }
    }

 */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

        switch (menuitem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_closet:
                Intent intent = new Intent(MainActivity.this, Closet_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_add:
                Intent intent1 = new Intent(MainActivity.this, CutOut_MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_stylerate:
                Intent intent2 = new Intent(MainActivity.this, RatingActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_situation:
                Intent intent3 = new Intent(MainActivity.this, ImageSelectActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_lookbook:
                Intent intent4 = new Intent(MainActivity.this, LookBookActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                SaveSharedPreference.clear(MainActivity.this);
                Intent intent5 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent5);
                break;
        }





        drawerLayout.closeDrawer(GravityCompat.START); //메뉴 선택되면 drawer 닫히도록

        return true;
    }
}

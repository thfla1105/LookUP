package Closet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.R;

import Cookie.SaveSharedPreference;
import Cutout.CutOut_MainActivity;
import ImageSelect.ImageSelectActivity;
import Login_Main.activity.MainActivity;
import LookBook.activity.LookBookActivity;
import styleList.RatingActivity;

public class Closet_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button mTopButton;
    private Button mBottomButton;
    private Button mOuterButton;
    private Button mDressButton;
    private Button mAccButton;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_activity_main);

        mTopButton = (Button) findViewById(R.id.top_btn);
        mBottomButton = (Button) findViewById(R.id.bottom_btn);
        mOuterButton = (Button) findViewById(R.id.outer_btn);
        mDressButton = (Button) findViewById(R.id.dress_btn);
        mAccButton = (Button) findViewById(R.id.acc_btn);




        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        //로그인 로그아웃 부분
        Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.nav_logout).setVisible(false);



        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_closet);



        //TopActivity로
        mTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TopActivity.class);
                startActivity(intent);
            }
        });

        mBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomActivity.class);
                startActivity(intent);
            }
        });

        mOuterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OuterActivity.class);
                startActivity(intent);
            }
        });

        mDressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DressActivity.class);
                startActivity(intent);
            }
        });

        mAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccActivity.class);
                startActivity(intent);
            }
        });
    }


    public void onBackPressed(){  //Back 눌렸을 때 어플 꺼지는 거 방지,,

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

        switch (menuitem.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(Closet_MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_closet:
                break;
            case R.id.nav_add:
                Intent intent1 = new Intent(Closet_MainActivity.this, CutOut_MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_stylerate:
                Intent intent2 = new Intent(Closet_MainActivity.this, RatingActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_situation:
                Intent intent3 = new Intent(Closet_MainActivity.this, ImageSelectActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_lookbook:
                Intent intent4 = new Intent(Closet_MainActivity.this, LookBookActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                SaveSharedPreference.clear(Closet_MainActivity.this);
                Intent intent5 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent5);
                break;




        }

        drawerLayout.closeDrawer(GravityCompat.START); //메뉴 선택되면 drawer 닫히도록

        return true;
    }
}

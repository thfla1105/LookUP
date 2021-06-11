package styleList;

import android.app.Activity;
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

import Closet.activity.AccActivity;
import Closet.activity.BottomActivity;
import Closet.activity.Closet_MainActivity;
import Closet.activity.DressActivity;
import Closet.activity.OuterActivity;
import Closet.activity.TopActivity;
import Cookie.SaveSharedPreference;
import Cutout.CutOut_MainActivity;
import ImageSelect.ImageSelectActivity;
import Login_Main.activity.MainActivity;
import LookBook.activity.LookBookActivity;
import styleList.RatingActivity;

public class routeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button mCasual;
    private Button mSporty;
    private Button mFormal;
    private Button mFeminine;



    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stylelist_routeactivity);

        mCasual = (Button) findViewById(R.id.casual_btn);
        mSporty = (Button) findViewById(R.id.sporty_btn);
        mFormal = (Button) findViewById(R.id.formal_btn);
        mFeminine = (Button) findViewById(R.id.feminine_btn);





        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        //로그인 로그아웃 부분
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);



        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_closet);



        //TopActivity로
        mCasual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
                startActivity(intent);
            }
        });


        mSporty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StyleActivity.class);
                startActivity(intent);
            }
        });
/*
        mFormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
                startActivity(intent);
            }
        });

        mFeminine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
                startActivity(intent);
            }
        });
*/

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
                Intent intent = new Intent(routeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_closet:
                break;
            case R.id.nav_add:
                Intent intent1 = new Intent(routeActivity.this, CutOut_MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_stylerate:
                Intent intent2 = new Intent(routeActivity.this, RatingActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_situation:
                Intent intent3 = new Intent(routeActivity.this, ImageSelectActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_lookbook:
                Intent intent4 = new Intent(routeActivity.this, LookBookActivity.class);
                startActivity(intent4);
                break;



        }

        drawerLayout.closeDrawer(GravityCompat.START); //메뉴 선택되면 drawer 닫히도록

        return true;
    }
}

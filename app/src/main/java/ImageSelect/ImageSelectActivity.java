package ImageSelect;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.R;

import Closet.activity.Closet_MainActivity;
import Cookie.SaveSharedPreference;
import Cutout.CutOut_MainActivity;
import Login_Main.activity.MainActivity;
import LookBook.activity.LookBookActivity;
import styleList.RatingActivity;


public class ImageSelectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button mSelectActivity1;
    Button mSelectActivity2;
    Button mSelectActivity3;
    Button mSelectActivity4;
    Button mSelectActivity5;
    Button mSelectActivity6;

    Drawable img1;
    Drawable img2;
    Drawable img3;
    Drawable img4;
    Drawable img5;
    Drawable img6;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    int left, top, right, bottom=20;

    private Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemselect_activity_itemselect);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mSelectActivity1 = (Button) findViewById(R.id.select1_button);
        mSelectActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });

        mSelectActivity2 = (Button) findViewById(R.id.select2_button);
        mSelectActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });

        mSelectActivity3 = (Button) findViewById(R.id.select3_button);
        mSelectActivity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });

        mSelectActivity4 = (Button) findViewById(R.id.select4_button);
        mSelectActivity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });

        mSelectActivity5 = (Button) findViewById(R.id.select5_button);
        mSelectActivity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });

        mSelectActivity6 = (Button) findViewById(R.id.select6_button);
        mSelectActivity6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });

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

        navigationView.setCheckedItem(R.id.nav_situation);




/*
        Drawable img1 = getApplicationContext().getResources().getDrawable(
                R.drawable.purpose_daily);
        img1.setBounds(left, top, right, bottom);
        mSelectActivity1.setCompoundDrawablesRelativeWithIntrinsicBounds(null, img1, null, null);

        Drawable img2 = getApplicationContext().getResources().getDrawable(
                R.drawable.purpose_hangout);
        img2.setBounds(left, top, right, bottom);
        mSelectActivity2.setCompoundDrawablesRelativeWithIntrinsicBounds(null, img2, null, null);

        Drawable img3 = getApplicationContext().getResources().getDrawable(
                R.drawable.purpose_workout);
        img3.setBounds(left, top, right, bottom);
        mSelectActivity3.setCompoundDrawables(null, img3, null, null);

        Drawable img4 = getApplicationContext().getResources().getDrawable(
                R.drawable.purpose_formal);
        img4.setBounds(left, top, right, bottom);
        mSelectActivity4.setCompoundDrawables(null, img4, null, null);

        Drawable img5 = getApplicationContext().getResources().getDrawable(
                R.drawable.purpose_parttime);
        img5.setBounds(left, top, right, bottom);
        mSelectActivity5.setCompoundDrawables(null, img5, null, null);

        Drawable img6 = getApplicationContext().getResources().getDrawable(
                R.drawable.purpose_etc);
        img6.setBounds(left, top, right, bottom);
        mSelectActivity6.setCompoundDrawables(null, img6, null, null);

 */


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {

        switch (menuitem.getItemId()){
            case R.id.nav_home:
                Intent intent5 = new Intent(ImageSelectActivity.this, MainActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_closet:
                Intent intent = new Intent(ImageSelectActivity.this, Closet_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_add:
                Intent intent1 = new Intent(ImageSelectActivity.this, CutOut_MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_stylerate:
                Intent intent2 = new Intent(ImageSelectActivity.this, RatingActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_situation:
                Intent intent3 = new Intent(ImageSelectActivity.this, ImageSelectActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_lookbook:
                Intent intent4 = new Intent(ImageSelectActivity.this, LookBookActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                SaveSharedPreference.clear(ImageSelectActivity.this);
                Intent intent6= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent6);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START); //메뉴 선택되면 drawer 닫히도록

        return true;
    }


}
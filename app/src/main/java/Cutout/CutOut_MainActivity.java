package Cutout;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

//import com.github.gabrielbb.cutout.CutOut;
import java.io.File;

import java.R;

import Closet.activity.Closet_MainActivity;
import ImageSelect.ImageSelectActivity;
import Login_Main.activity.MainActivity;
import LookBook.activity.LookBookActivity;
import styleList.RatingActivity;
//import petrov.kristiyan.colorpicker_sample.R;

public class CutOut_MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final int REQUEST_GET_SINGLE_FILE = 0;
    private ImageView imageView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cutout_activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //메뉴 시작
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //로그인 로그아웃 부분
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_add);
        //메뉴 끝

        imageView = findViewById(R.id.imageView);

        final Uri imageIconUri = getUriFromDrawable(R.drawable.buttonbg);
        imageView.setImageURI(imageIconUri);
        imageView.setTag(imageIconUri);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.add_icon);
/*
        fab.setOnClickListener(view -> {
            final Uri testImageUri = getUriFromDrawable(R.drawable.test_image);

            CutOut.activity()
                    .src(testImageUri)
                    .bordered()
                    .noCrop()
                    .start(this);
        });
*/
        //내가 추가함
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");

            startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_GET_SINGLE_FILE);



        });
        //여기까지

    }

    //내가 추가함 start
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                    }
                    // Set the image in ImageView
                    //ImageView((ImageView) findViewById(R.id.imageView)).setImageURI(selectedImageUri);

                    //imageView.setImageURI(selectedImageUri);  //작동됨! 임시 주석

                    CutOut.activity()
                            .src(selectedImageUri)
                            .bordered()
                            .noCrop()
                            .start(this);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    //여기까지




/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE) {

            switch (resultCode) {
                case Activity.RESULT_OK:
                    Uri imageUri = CutOut.getUri(data);
                    // Save the image using the returned Uri here
                    imageView.setImageURI(imageUri);
                    imageView.setTag(imageUri);
                    break;
                case CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE:
                    Exception ex = CutOut.getError(data);
                    break;
                default:
                    System.out.print("User cancelled the CutOut screen");
            }
        }
    }*/

    public Uri getUriFromDrawable(int drawableId) {
        return Uri.parse("android.resource://" + getPackageName() + "/drawable/" + getApplicationContext().getResources().getResourceEntryName(drawableId));
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

        switch (menuitem.getItemId()) {
            case R.id.nav_home:
                Intent intent1 = new Intent(CutOut_MainActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_closet:
                Intent intent = new Intent(CutOut_MainActivity.this, Closet_MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_add:
                break;
            case R.id.nav_stylerate:
                Intent intent2 = new Intent(CutOut_MainActivity.this, RatingActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_situation:
                Intent intent3 = new Intent(CutOut_MainActivity.this, ImageSelectActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_lookbook:
                Intent intent4 = new Intent(CutOut_MainActivity.this, LookBookActivity.class);
                startActivity(intent4);
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START); //메뉴 선택되면 drawer 닫히도록

        return true;
    }
}
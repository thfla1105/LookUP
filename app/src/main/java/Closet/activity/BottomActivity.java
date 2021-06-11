package Closet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.R;
import java.util.ArrayList;

import Closet.Data_Type;
import Closet.ViewPagerAdapter.ViewPagerAdapter_Bottom;

public class BottomActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager2 viewPager2; //뷰페이저
    ViewPagerAdapter_Bottom viewPagerAdapter; //뷰페이저 어뎁터
    TabLayout tabLayout; //텝 레이아웃
    ArrayList<Data_Type> mdata; //데이터 모델
    Context context;
    public static BottomActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_activity_bottom);
        activity=this;
        context = this;
        mdata = new ArrayList<>();

        viewPager2 = findViewById(R.id.notification_list_container);
        tabLayout = findViewById(R.id.notification_list_tabs);

//        tabLayout.addTab(tabLayout.newTab().setText("A")); //텝 레이아웃 아이템 추가
//        tabLayout.addTab(tabLayout.newTab().setText("B"));
//        tabLayout.addTab(tabLayout.newTab().setText("C"));

        mdata.add(new Data_Type(1));
        mdata.add(new Data_Type(2));
        mdata.add(new Data_Type(3));
        mdata.add(new Data_Type(4));
        mdata.add(new Data_Type(5));
        mdata.add(new Data_Type(6));
        mdata.add(new Data_Type(7));



        viewPagerAdapter = new ViewPagerAdapter_Bottom(context ,mdata); //뷰페이저 어뎁터 생성
        viewPager2.setAdapter(viewPagerAdapter);//어뎁터 연결
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL); //스크롤방향


        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {// 텝 레이아웃이랑 연결
                switch (position){
                    case 0:{
                        tab.setText("데님"); //텝레이아웃 상단 타이틀 제목 설정
                        break;
                    }
                    case 1:{
                        tab.setText("롱 스커트");
                        break;
                    }
                    case 2:{
                        tab.setText("반바지");
                        break;
                    }
                    case 3:{
                        tab.setText("미니 스커트");
                        break;
                    }
                    case 4:{
                        tab.setText("슬랙스");
                        break;
                    }
                    case 5:{
                        tab.setText("롱 트레이닝팬츠");
                        break;
                    }
                    case 6:{
                        tab.setText("숏 트레이닝팬츠");
                        break;
                    }
                }

            }
        });

        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { //뷰페이저 해당 포지션 위치
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position){
                    case 0://A
                    {
                       // Toast.makeText(context, "A", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 1://B
                    {
                       // Toast.makeText(context, "B", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2://C
                    {
                       // Toast.makeText(context, "C", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 3://C
                    {
                        //Toast.makeText(context, "D", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 4://C
                    {
                        //Toast.makeText(context, "E", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 5://C
                    {
                        //Toast.makeText(context, "F", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 6://C
                    {
                       // Toast.makeText(context, "G", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.appbar){
            finish();
            Intent intent = new Intent(getApplicationContext(), Closet_MainActivity.class);
            startActivity(intent);
        }
    }

}
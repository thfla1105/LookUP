package Color.activity;
 /////////////0427 색상 123 한꺼번에 선택하도록 만든 것

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Color.data.ColorData;
import Color.data.ColorResponse;
import network.RetrofitClient;
import network.ServiceApi;

import petrov.kristiyan.colorpicker.ColorPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.R;


public class TopSelect123 extends AppCompatActivity {

    static final int top1 = -1;
    static final int top2 = -1;
    static final int top3 = -1;

    Boolean backgroundChanged = false;
    private boolean colFlag = false;
    private boolean colCnt = false;
    private ServiceApi service;

    private Button top_b1;
    private Button top_b2;
    private Button top_b3;
    private RelativeLayout layout;

    private int mcolor1; //0426
    private int mcolor2;
    private int mcolor3;


//    private ServiceApi service;


    private TextView topText;

    public int top_color_type[] = {0, 0, 0};  //1:파스텔 2:비비드 3:딥 4:내추럴 5:모노톤

    //0416 임시 주석 public static Color.activity.TopSelect context_main;   //0409 추가
    public static int top_position1=-1;
    public static int top_position2=-1;
    public static int top_position3=-1;

    public Button next1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_top_select123);

        top_b1 = (Button) findViewById(R.id.top_b1);
        top_b2 = (Button) findViewById(R.id.top_b2);
        top_b3 = (Button) findViewById(R.id.top_b3);

        layout = (RelativeLayout) findViewById(R.id.layout);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        top_b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker1_top();
            }
        });
        top_b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker2_top();
            }
        });
        top_b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker3_top();
            }
        });

        next1 = (Button) findViewById(R.id.next1);
        next1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), code.color_select.activity.tone_in_on.class);
//                //원래 이건데 내가 지운거 tone_in_on.class);
//                startActivity(intent);

                Intent intent = new Intent(getApplicationContext(), ToneSelect1.class);
                startActivity(intent);
                /*
                Intent i = new Intent(TopSelect123.this, ToneSelect1.class);
                startActivity(i); */
                attemptSendColor123();
                Log.d("attemptSendColor123", "완료");

//
//                int top1 = top_position1;
//                int top2 = top_position2;
//                int top3 = top_position3;

//                sendTop123(new TopData(top1,top2,top3));
//                showProgress(true);


            }
        });
    }

    public void openColorPicker1_top() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        //colors.add(Color.rgb(0, 162, 179));

        colors.add("#EAA2B3");  //파스텔 핑크
        colors.add("#F8F4B1");  //파스텔 옐로우
        colors.add("#91C0BD");  //파스텔 그린
        colors.add("#ABD6F3");  //파스텔 블루
        colors.add("#D1B7DB");  //파스텔 퍼플

        colors.add("#E13F00");  //비비드 빨
        colors.add("#F06700");  //비비드 주
        colors.add("#F4CD0B");  //비비드 노
        colors.add("#CBDB08");  //비비드 연두
        colors.add("#40901A");  //비비드 초록
        colors.add("#218D7B");  //비비드 딥그린
        colors.add("#069DE4");  //비비드 블루
        colors.add("#394AA4");  //비비드 네이비
        colors.add("#793991");  //비비드 퍼플
        colors.add("#D44194");  //비비드 핑크

        colors.add("#90343F");  //딥
        colors.add("#602C04");  //딥
        colors.add("#C89704");  //딥
        colors.add("#42510F");  //딥
        colors.add("#2D2267");  //딥

        colors.add("#EED079");  //내추럴
        colors.add("#9B4D02");  //내추럴
        colors.add("#CA8102");  //내추럴
        colors.add("#BF890A");  //내추럴
        colors.add("#61490E");  //내추럴

        colors.add("#ffffff");  //흰
        colors.add("#000000");  //검
        colors.add("#B8B8B8");  //회


        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
//                      layout.setBackgroundColor(color);  // OK 버튼 클릭 시 이벤트
                        top_b1.setBackgroundColor(color);
                        top_position1 = position; //0409 추가
                        backgroundChanged = true;



                        //0416sendTop123(new ColorData(top_position1, top_position2, top_position3));

                        Log.d("position", "" + position);

                        if (position >= 0 && position < 5) {
                            top_color_type[0] = 1;
                            mcolor1 =1;
                            Log.d("mcolor1 :", "1완료");
                            Log.d("선택된 색 ", "1파스텔톤");
                        }
                        if (position >= 5 && position < 15) {
                            top_color_type[0] = 2;
                            mcolor1 =2;
                            Log.d("선택된 색 ", "2비비드톤");
                        }
                        if (position >= 15 && position < 20) {
                            top_color_type[0] = 3;
                            mcolor1 =3;
                            Log.d("선택된 색 ", "3딥톤");
                        }
                        if (position >= 20 && position < 25) {
                            top_color_type[0] = 4;
                            mcolor1 =4;
                            Log.d("선택된 색 ", "4내추럴톤");
                        }
                        if (position >= 25 && position < 28) {
                            top_color_type[0] = 5;
                            mcolor1 =5;
                            Log.d("선택된 색 ", "5모노톤");
                        }


                    }

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성


    }

    public void openColorPicker2_top() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list


        colors.add("#EAA2B3");  //파스텔 핑크
        colors.add("#F8F4B1");  //파스텔 옐로우
        colors.add("#91C0BD");  //파스텔 그린
        colors.add("#ABD6F3");  //파스텔 블루
        colors.add("#D1B7DB");  //파스텔 퍼플

        colors.add("#E13F00");  //비비드 빨
        colors.add("#F06700");  //비비드 주
        colors.add("#F4CD0B");  //비비드 노
        colors.add("#CBDB08");  //비비드 연두
        colors.add("#40901A");  //비비드 초록
        colors.add("#218D7B");  //비비드 딥그린
        colors.add("#069DE4");  //비비드 블루
        colors.add("#394AA4");  //비비드 네이비
        colors.add("#793991");  //비비드 퍼플
        colors.add("#D44194");  //비비드 핑크

        colors.add("#90343F");  //딥
        colors.add("#602C04");  //딥
        colors.add("#C89704");  //딥
        colors.add("#42510F");  //딥
        colors.add("#2D2267");  //딥

        colors.add("#EED079");  //내추럴
        colors.add("#9B4D02");  //내추럴
        colors.add("#CA8102");  //내추럴
        colors.add("#BF890A");  //내추럴
        colors.add("#61490E");  //내추럴

        colors.add("#ffffff");  //흰
        colors.add("#000000");  //검
        colors.add("#B8B8B8");  //회


        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
//                      layout.setBackgroundColor(color);  // OK 버튼 클릭 시 이벤트
                        top_b2.setBackgroundColor(color);
                        top_position2 = position; //0409 추가
                        backgroundChanged = true;



                        Log.d("position", "" + position);

                        if (position >= 0 && position < 5) {
                            top_color_type[1] = 1;
                            mcolor2 =1;
                            Log.d("mcolor2 :", "1완료");
                            Log.d("선택된 색 ", "1파스텔톤");
                        }
                        if (position >= 5 && position < 15) {
                            top_color_type[1] = 2;
                            mcolor2 =2;
                            Log.d("mcolor2 :", "2완료");
                            Log.d("선택된 색 ", "2비비드톤");
                        }
                        if (position >= 15 && position < 20) {
                            top_color_type[1] = 3;
                            mcolor2 =3;
                            Log.d("mcolor2 :", "3완료");
                            Log.d("선택된 색 ", "3딥톤");
                        }
                        if (position >= 20 && position < 25) {
                            top_color_type[1] = 4;
                            mcolor2 =4;
                            Log.d("mcolor2 :", "4완료");
                            Log.d("선택된 색 ", "4내추럴톤");
                        }
                        if (position >= 25 && position < 28) {
                            top_color_type[1] = 5;
                            mcolor2 =5;
                            Log.d("mcolor2 :", "5완료");
                            Log.d("선택된 색 ", "5모노톤");
                        }




                    }


                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성


    }

    public void openColorPicker3_top() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list


        colors.add("#EAA2B3");  //파스텔 핑크
        colors.add("#F8F4B1");  //파스텔 옐로우
        colors.add("#91C0BD");  //파스텔 그린
        colors.add("#ABD6F3");  //파스텔 블루
        colors.add("#D1B7DB");  //파스텔 퍼플

        colors.add("#E13F00");  //비비드 빨
        colors.add("#F06700");  //비비드 주
        colors.add("#F4CD0B");  //비비드 노
        colors.add("#CBDB08");  //비비드 연두
        colors.add("#40901A");  //비비드 초록
        colors.add("#218D7B");  //비비드 딥그린
        colors.add("#069DE4");  //비비드 블루
        colors.add("#394AA4");  //비비드 네이비
        colors.add("#793991");  //비비드 퍼플
        colors.add("#D44194");  //비비드 핑크

        colors.add("#90343F");  //딥
        colors.add("#602C04");  //딥
        colors.add("#C89704");  //딥
        colors.add("#42510F");  //딥
        colors.add("#2D2267");  //딥

        colors.add("#EED079");  //내추럴
        colors.add("#9B4D02");  //내추럴
        colors.add("#CA8102");  //내추럴
        colors.add("#BF890A");  //내추럴
        colors.add("#61490E");  //내추럴

        colors.add("#ffffff");  //흰
        colors.add("#000000");  //검
        colors.add("#B8B8B8");  //회


        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
//                      layout.setBackgroundColor(color);  // OK 버튼 클릭 시 이벤트
                        top_b3.setBackgroundColor(color);
                        top_position3 = position; //0409 추가
                        backgroundChanged = true;


                        Log.d("position", "" + position);

                        if (position >= 0 && position < 5) {
                            top_color_type[2] = 1;
                            mcolor3 =1;
                            Log.d("mcolor3 :", "1완료");
                            Log.d("선택된 색 ", "1파스텔톤");
                        }
                        if (position >= 5 && position < 15) {
                            top_color_type[2] = 2;
                            mcolor3 =2;
                            Log.d("mcolor3 :", "2완료");
                            Log.d("선택된 색 ", "2비비드톤");
                        }
                        if (position >= 15 && position < 20) {
                            top_color_type[2] = 3;
                            mcolor3 =3;
                            Log.d("mcolor3 :", "3완료");
                            Log.d("선택된 색 ", "3딥톤");
                        }
                        if (position >= 20 && position < 25) {
                            top_color_type[2] = 4;
                            mcolor3 =4;
                            Log.d("mcolor3 :", "4완료");
                            Log.d("선택된 색 ", "4내추럴톤");
                        }
                        if (position >= 25 && position < 28) {
                            top_color_type[2] = 5;
                            mcolor3 =5;
                            Log.d("mcolor3 :", "5완료");
                            Log.d("선택된 색 ", "5모노톤");
                        }


                    }


                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }


                }).show();

    }


    private void attemptSendColor123(){
        int color111 = mcolor1;
        int color222 = mcolor2;
        int color333 = mcolor3;
        Log.d("color111222333 :", "완료");

        if (color111>0 && color222>0 && color333>0) {
            sendColor123(new ColorData(color111, color222, color333));

            Log.d("attemptsendcolor123 :", "완료");
            showProgress(true);
            Log.d("showprogress :", "완료");
        }

    }

    private void sendColor123(ColorData data) {
        service.userCheckColor123(data).enqueue(new Callback<ColorResponse>() {
            @Override
            public void onResponse(Call<ColorResponse> call, Response<ColorResponse> response) {
                ColorResponse result = response.body();
                Toast.makeText(TopSelect123.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                if (result.getCode() == 200) {
                    finish();
                }
                Log.d("enqueue :", "완료");
            }

            @Override
            public void onFailure(Call<ColorResponse> call, Throwable t) {
                Toast.makeText(TopSelect123.this, "color 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }
    private void showProgress(boolean show) {
        // mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }



}


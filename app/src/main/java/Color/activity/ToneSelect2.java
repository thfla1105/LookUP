package Color.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import network.RetrofitClient;
import network.ServiceApi;

import java.R;

public class ToneSelect2 extends AppCompatActivity {
    private TopSelect123 top123;



    ImageView image;
    private ImageView ToneInTone, ToneOnTone;

    public int Tone_In_On1 = 0; //1이면 IN ,, 2이면 ON
    public int Tone_In_On2 = 0; //1이면 IN ,, 2이면 ON
    public int Tone_In_On3 = 0; //1이면 IN ,, 2이면 ON

    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;

    public Button next222;

    private ServiceApi service;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        Log.d("top_position1 받아오기전","완료");
        // int position1 = getIntent().getIntExtra("top_position1", -1);
        int position2 = top123.top_position2;
        Log.d("top_position1 받아옴","완료");



        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_tone_select2);


        radioGroup = findViewById(R.id.radioGroup);


        textView = findViewById(R.id.text_view_selected);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        next222 = (Button) findViewById(R.id.next222);
        next222.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent i = new Intent(ToneSelect2.this, ToneSelect3.class);
                startActivity(i);


                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                textView.setText("User Choice " + radioButton.getText());


            }});



        Button buttonApply = findViewById(R.id.button_apply);
        buttonApply.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {




                if (radioButton.equals("result_ToneInTone")) {
                    Log.d("선호도 :", "톤인톤");
                    Tone_In_On2 = 1;

                   // sendTone1(new ToneData(Tone_In_On1, Tone_In_On2, Tone_In_On3));
                }
                else{
                    // if (radioButton.getText().equals("result_ToneOnTone")) {
                    Log.d("선호도 :", "톤온톤");
                    Tone_In_On2 = 2;
                //    sendTone1(new ToneData(Tone_In_On1, Tone_In_On2, Tone_In_On3));
                }
                /*int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                textView.setText("User Choice " + radioButton.getText());
                if (radioButton.getText().equals("Tone In Tone")) {
                    Log.d("선호도 :", "톤인톤");
                    Tone_In_On = 1;
                }
                if (radioButton.getText().equals("Tone On Tone")) {
                    Log.d("선호도 :", "톤온톤");
                    Tone_In_On = 2;
                }
*/

//            radioButton = findViewById(radioId);
            }
        });


        ToneInTone = (ImageView) findViewById(R.id.ToneInTone);
        ToneOnTone = (ImageView) findViewById(R.id.ToneOnTone);


        //top1으로 고른 색상 -> 톤온톤?톤인톤? 뭘 선호하는지
        switch (position2) {
            case 0:
                ToneInTone.setImageResource(R.drawable.pastel_1_in);
                ToneOnTone.setImageResource(R.drawable.pastel_1_on);
                break;
            case 1:
                ToneInTone.setImageResource(R.drawable.pastel_2_in);
                ToneOnTone.setImageResource(R.drawable.pastel_2_on);
                break;
            case 2:
                ToneInTone.setImageResource(R.drawable.pastel_3_in);
                ToneOnTone.setImageResource(R.drawable.pastel_3_on);
                break;
            case 3:
                ToneInTone.setImageResource(R.drawable.pastel_4_in);
                ToneOnTone.setImageResource(R.drawable.pastel_4_on);
                break;
            case 4:
                ToneInTone.setImageResource(R.drawable.pastel_5_in);
                ToneOnTone.setImageResource(R.drawable.pastel_5_on);
                break;
            case 5:
                ToneInTone.setImageResource(R.drawable.vivid_1_in);
                ToneOnTone.setImageResource(R.drawable.vivid_1_on);
                break;
            case 6:
                ToneInTone.setImageResource(R.drawable.vivid_2_in);
                ToneOnTone.setImageResource(R.drawable.vivid_2_on);
                break;
            case 7:
                ToneInTone.setImageResource(R.drawable.vivid_3_in);
                ToneOnTone.setImageResource(R.drawable.vivid_3_on);
                break;
            case 8:
                ToneInTone.setImageResource(R.drawable.vivid_4_in);
                ToneOnTone.setImageResource(R.drawable.vivid_4_on);
                break;
            case 9:
                ToneInTone.setImageResource(R.drawable.vivid_5_in);
                ToneOnTone.setImageResource(R.drawable.vivid_5_on);
                break;
            case 10:
                ToneInTone.setImageResource(R.drawable.vivid_6_in);
                ToneOnTone.setImageResource(R.drawable.vivid_6_on);
                break;
            case 11:
                ToneInTone.setImageResource(R.drawable.vivid_7_in);
                ToneOnTone.setImageResource(R.drawable.vivid_7_on);
                break;
            case 12:
                ToneInTone.setImageResource(R.drawable.vivid_8_in);
                ToneOnTone.setImageResource(R.drawable.vivid_8_on);
                break;
            case 13:
                ToneInTone.setImageResource(R.drawable.vivid_9_in);
                ToneOnTone.setImageResource(R.drawable.vivid_9_on);
                break;
            case 14:
                ToneInTone.setImageResource(R.drawable.vivid_10_in);
                ToneOnTone.setImageResource(R.drawable.vivid_10_on);
                break;
            case 15:
                ToneInTone.setImageResource(R.drawable.deep_1_in);
                ToneOnTone.setImageResource(R.drawable.deep_1_on);
                break;
            case 16:
                ToneInTone.setImageResource(R.drawable.deep_2_in);
                ToneOnTone.setImageResource(R.drawable.deep_2_on);
                break;
            case 17:
                ToneInTone.setImageResource(R.drawable.deep_3_in);
                ToneOnTone.setImageResource(R.drawable.deep_3_on);
                break;
            case 18:
                ToneInTone.setImageResource(R.drawable.deep_4_in);
                ToneOnTone.setImageResource(R.drawable.deep_4_on);
                break;
            case 19:
                ToneInTone.setImageResource(R.drawable.deep_5_in);
                ToneOnTone.setImageResource(R.drawable.deep_5_on);
                break;
            case 20:
                ToneInTone.setImageResource(R.drawable.natural_1_in);
                ToneOnTone.setImageResource(R.drawable.natural_1_on);
                break;
            case 21:
                ToneInTone.setImageResource(R.drawable.natural_2_in);
                ToneOnTone.setImageResource(R.drawable.natural_2_on);
                break;
            case 22:
                ToneInTone.setImageResource(R.drawable.natural_3_in);
                ToneOnTone.setImageResource(R.drawable.natural_3_on);
                break;
            case 23:
                ToneInTone.setImageResource(R.drawable.natural_4_in);
                ToneOnTone.setImageResource(R.drawable.natural_4_on);
                break;
            case 24:
                ToneInTone.setImageResource(R.drawable.natural_5_in);
                ToneOnTone.setImageResource(R.drawable.natural_5_on);
                break;
        }



//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);


        // Toast.makeText(this,"Selected "+radioButton, Toast.LENGTH_SHORT).show();
    }

/*
    private void sendTone1(ToneData data) {
        service.userCheckTone1(data).enqueue(new Callback<ToneResponse>() {
            @Override
            public void onResponse(Call<ToneResponse> call, Response<ToneResponse> response) {
                ToneResponse result = response.body();

                if(response.body() != null) {
                    result = response.body();
                }
                else{
                    Log.v("알림", "값이 없습니다.");
                }


                if(result.getCode()==200){

                }
                showProgress(false);

//                Toast.makeText(tone_in_on.this, result.getMessage(), Toast.LENGTH_SHORT).show();
//                showProgress(false);


            }

            @Override
            public void onFailure(Call<ToneResponse> call, Throwable t) {
                Toast.makeText(ToneSelect2.this, "에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }
    private void showProgress(boolean show) {
        // mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

*/


}

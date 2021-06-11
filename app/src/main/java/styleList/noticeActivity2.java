package styleList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.R;

import ImageSelect.SelectActivity;

public class noticeActivity2 extends Activity {

    TextView txtText;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stylelist_noticeactivity);

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.txtText);
        imgview =(ImageView)findViewById(R.id.imgview);
        //데이터 가져오기
        //Intent intent = getIntent();
        //String data = intent.getStringExtra("data");
        txtText.setText("외출 목적 별 선호하는 스타일을 선택해주세요!\n 3개 이상 선택");
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        //Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
        Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
        startActivity(intent);
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }



}

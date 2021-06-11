package styleList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.R;

import ImageSelect.ImageSelectActivity;
import ImageSelect.SelectActivity;
import Login_Main.activity.MainActivity;

import static Login_Main.activity.LoginActivity.popnum;


public class noticeActivity extends Activity {

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
        txtText.setText("룩북 생성을 위해 필요한 3 Steps! \n 1. 외출 목적별 선호 스타일 선택 \n " +
                "2. 스타일 별 선호 옷 조합 Rating \n 3. 가상 옷장에 내 옷 추가!");
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Log.e("NoticeActivity popnum: ", String.valueOf(popnum));
            Intent intent=new Intent();
            if(popnum==0){
                intent=new Intent(getApplicationContext(), RatingActivity.class);
                //intent = new Intent(getApplicationContext(), SelectActivity.class);
            }
            else{
                intent = new Intent(getApplicationContext(), SelectActivity.class);
                //intent=new Intent(getApplicationContext(), RatingActivity.class);
            }
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

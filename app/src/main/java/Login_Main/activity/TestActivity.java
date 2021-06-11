package Login_Main.activity;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import petrov.kristiyan.colorpicker_sample.R;
import java.R;
import Cookie.SaveSharedPreference;


public class TestActivity extends AppCompatActivity {
    Button TestButton;
    Button TestButton2;
    private Intent intent;
    private Context mcontext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mcontext=this;
        
        //로그아웃 버튼
        TestButton = (Button) findViewById(R.id.test_btn2);
        TestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSharedPreference.clear(mcontext);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        
        //메인 액티비티로 가는 버튼
        TestButton2 = (Button) findViewById(R.id.test_btn3);
        TestButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

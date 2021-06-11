package Login_Main.activity;

import android.os.Bundle;
//import android.support.annotation.Nullable;
import androidx.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.R;
//import petrov.kristiyan.colorpicker_sample.R;
import Login_Main.data.DupCheckData;
import Login_Main.data.DupCheckResponse;
import network.ServiceApi;
import network.RetrofitClient;
import Login_Main.data.JoinResponse;
import Login_Main.data.JoinData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JoinActivity extends AppCompatActivity {
   // private AutoCompleteTextView mEmailView;
    private EditText mIdView;
    private EditText mPwdView;

    private EditText mNameView;
    private RadioGroup mGenderGroupView;
    private RadioButton mGenderButtonView;
    //private RadioButton mGenderFemaleView;
    //private RadioButton mGenderMaleView;
    private EditText mAgeView;
    private EditText mEmailView;
    private EditText mPhoneView;

    private Button mJoinButton;
    private Button mDupButton;
    private boolean dupFlag = false;
    private boolean dupCnt=false;

    //private ProgressBar mProgressView;
    private ServiceApi service;
    String RadioResult;

    public int pcheck=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_join);

        mIdView = (EditText) findViewById(R.id.join_id);
        //mEmailView = (AutoCompleteTextView) findViewById(R.id.join_email);
        mPwdView = (EditText) findViewById(R.id.join_pwd);

        mNameView = (EditText) findViewById(R.id.join_name);
        mGenderGroupView = (RadioGroup) findViewById(R.id.join_gender);
        //mGenderFemaleView=(RadioButton) findViewById(R.id.btn_female);
        //mGenderMaleView=(RadioButton) findViewById(R.id.btn_male);

        mAgeView=(EditText) findViewById(R.id.join_age);
        mEmailView = (EditText) findViewById(R.id.join_email);
        mPhoneView = (EditText) findViewById(R.id.join_phone);
        mJoinButton = (Button) findViewById(R.id.join_button);
        mDupButton = (Button) findViewById(R.id.dup_btn);
        //mProgressView = (ProgressBar) findViewById(R.id.join_progress);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        //성별 체크하는 radiobutton
        mGenderGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mGenderButtonView=(RadioButton) findViewById(i);
                Toast.makeText(JoinActivity.this, mGenderButtonView.getText(), Toast.LENGTH_SHORT).show();
                RadioResult=mGenderButtonView.getText().toString();
                /*
                if(i==R.id.btn_female){
                    Toast.makeText(getApplicationContext(), "여성", Toast.LENGTH_SHORT).show();
                }
                else if(i==R.id.btn_male){
                    Toast.makeText(getApplicationContext(), "남성", Toast.LENGTH_SHORT).show();
                }
                */
            }
        }
        );

        //ID 중복확인
        mDupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dupCnt=true;
                attempDup();
            }
        });

        //회원가입 버튼
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RadioResult ==null){
                    Toast.makeText(JoinActivity.this, "성별을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    attemptJoin();
                }
            }
        });
    }

    private void attemptJoin() {
        mIdView.setError(null);
        mPwdView.setError(null);

        mNameView.setError(null);
       // mGenderButttonView.setError(null);
        mAgeView.setError(null);
        mEmailView.setError(null);
        mPhoneView.setError(null);
      //  mJoinButton.setError(null);

        String id=mIdView.getText().toString();
        String pwd=mPwdView.getText().toString();

        String name = mNameView.getText().toString();
        String gender=RadioResult;
                //mGenderGroupView.getText().toString();
        //String gender=mGenderButtonView.getText().toString();
        String age=mAgeView.getText().toString();
        String email = mEmailView.getText().toString();
        String phone=mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 아이디의 유효성 검사
        if (id.isEmpty()) {
            mIdView.setError("id를 입력해주세요.");
            focusView = mIdView;
            cancel = true;
        }

        // 나이의 유효성 검사
        if (age.isEmpty()) {
            mAgeView.setError("나이를 입력해주세요.");
            focusView = mAgeView;
            cancel = true;
        }


        // 패스워드의 유효성 검사
        if (pwd.isEmpty()) {
            mPwdView.setError("비밀번호를 입력해주세요.");
            focusView = mPwdView;
            cancel = true;
        } else if (!isPasswordValid(pwd)) {
            mPwdView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPwdView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            mEmailView.setError("이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (name.isEmpty()) {
            mNameView.setError("이름을 입력해주세요.");
            focusView = mNameView;
            cancel = true;
        }

        // 핸드폰 번호의 유효성 검사
        if (phone.isEmpty()) {
            mPhoneView.setError("핸드폰 번호를 입력해주세요.");
            focusView = mPhoneView;
            cancel = true;
        }else if (!isPhoneValid(phone)) {
            mPhoneView.setError("-를 포함한 핸드폰 번호를 입력해주세요.");
            focusView = mPhoneView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        else if(dupFlag && dupCnt) {
            startJoin(new JoinData(id, pwd, name, gender, age, email, phone));
            showProgress(true);
        }
        else if(!dupCnt || !dupFlag) {
            Toast.makeText(JoinActivity.this, "ID를 체크하세요!", Toast.LENGTH_SHORT).show();
        }
        /*
        else if(!dupFlag) {
            Toast.makeText(JoinActivity.this, "ID가 중복됩니다! 새로운 ID를 입력해주세요", Toast.LENGTH_SHORT).show();
            //dupCnt=false;
        }
         */
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);
                pcheck=0;
                if (result.getCode() == 200) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

    private void attempDup(){
        mIdView.setError(null);
        String id=mIdView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        // 아이디의 유효성 검사
        if (id.isEmpty()) {
            mIdView.setError("id를 입력해주세요.");
            focusView = mIdView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        else {
            CheckDup(new DupCheckData(id));
        }
    }
    private void CheckDup(DupCheckData data) {
        service.userCheckDup(data).enqueue(new Callback<DupCheckResponse>() {
            @Override
            public void onResponse(Call<DupCheckResponse> call, Response<DupCheckResponse> response) {
                DupCheckResponse result = response.body();
                Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                showProgress(false);

                if (result.getCode() == 1) {
                    dupFlag=true;
                }
                else{
                    dupFlag=false;
                    dupCnt=false;
                }
            }

            @Override
            public void onFailure(Call<DupCheckResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }



    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private boolean isPhoneValid(String phone){return phone.contains("-");}

    private void showProgress(boolean show) {
       // mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}

package Login_Main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

//import petrov.kristiyan.colorpicker_sample.R;
import java.R;
import Cookie.SaveSharedPreference;
import network.ServiceApi;
import network.RetrofitClient;
import Login_Main.data.LoginResponse;
import Login_Main.data.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import styleList.RatingActivity;
import styleList.noticeActivity;

public class LoginActivity extends AppCompatActivity {

    //private AutoCompleteTextView mEmailView;
   // private EditText mEmailView;
    private EditText mIdView;
    private EditText mPwdView;
    private Button mEmailLoginButton;
    private Button mJoinButton;

    private ProgressBar mProgressView;
    private ServiceApi service;
    private static String userId;

    //public static int popnum=0;
    public static int popnum;
    public static int popflag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);

        popnum=5;

        mIdView=(EditText) findViewById(R.id.login_id);
       // mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPwdView = (EditText) findViewById(R.id.login_pwd);
        mEmailLoginButton = (Button) findViewById(R.id.login_button);
        mJoinButton = (Button) findViewById(R.id.join_button);
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);



        service = RetrofitClient.getClient().create(ServiceApi.class);

        mEmailLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mJoinButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
            }
        });
    }

//원래 private이었는데 public으로 변경함
    private void attemptLogin() {
        mIdView.setError(null);
        mPwdView.setError(null);

        String id = mIdView.getText().toString();
        String pwd = mPwdView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 패스워드의 유효성 검사
        if (pwd.isEmpty()) {
            mPwdView.setError("비밀번호를 입력해주세요.");
            focusView = mPwdView;
            cancel = true;
        } else if (!isPwdValid(pwd)) {
            mPwdView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPwdView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (id.isEmpty()) {
            mIdView.setError("아이디를 입력해주세요.");
            focusView = mIdView;
            cancel = true;
        }
            /*
        } else if (!isEmailValid(email)) {
            mEmailView.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }
        */
        if (cancel) {
            focusView.requestFocus();
        } else {
            startLogin(new LoginData(id, pwd));
            showProgress(true);
        }
    }

    private void startLogin(LoginData data) {
        service.userLogin(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                if(response.body() != null) {
                    result = response.body();
                }
                else{
                    Log.v("알림", "값이 없습니다.");
                }
               // Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                //로그인 성공하면 resultcode=200, 쿠키 저장하여 로그인 유지
                if(result.getCode()==200){
                    setmId(result.getUserId());
                    SaveSharedPreference.setString(getApplicationContext(), "ID", result.getUserId());
                    Toast.makeText(getApplicationContext(), SaveSharedPreference.getString(getApplicationContext(), "ID")+"님 자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    popnum=0;
                    popflag=1;
                    Log.e("LoginActivity popnum: ", String.valueOf(popnum));
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                }
                showProgress(false);

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
                showProgress(false);
            }
        });
    }

        /*
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

         */
    private boolean isPwdValid(String pwd){
        return pwd.length()>=6;
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setmId(String userId){
        this.userId=userId;
    }

    public static String getmId(){
        return userId;
    }


}


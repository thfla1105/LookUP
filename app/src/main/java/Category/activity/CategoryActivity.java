package Category.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.R;
import java.io.File;

import Category.data.CategoryData;
import Category.data.CategoryResponse;
import Category.data.SaveCategoryData;
import Category.data.SaveCategoryResponse;
import Cookie.SaveSharedPreference;
import Login_Main.activity.MainActivity;
import network.RetrofitClient;
import network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    //private AutoCompleteTextView mEmailView;
    // private EditText mEmailView;
    private Activity activity;
    private View view;
    private Button catBtn;
    private ImageView imgView;
    private ServiceApi service;
    private int msgCode=-1;
    private String category="";
    private int g_which=100;
    AlertDialog.Builder alertdialog; // 다이얼로그 바디
    private final String[] words=new String[] {"상의", "하의", "아우터", "원피스", "악세서리"};
    private final String[] topList=new String[]{"반팔", "긴팔", "반팔 셔츠", "긴팔 셔츠", "니트", "후드", "반팔 블라우스", "긴팔 블라우스", "민소매", "조끼"};
    private final String[] bottomList=new String[]{"데님", "롱 스커트", "반바지", "미니 스커트", "슬랙스", "롱 트레이닝팬츠", "숏 트레이닝팬츠"};
    private final String[] outerList=new String[]{"가디건", "후리스", "레더 자켓", "데님 자켓", "정장 자켓", "코트","패딩"};
    private final String[] accList=new String[]{"목도리", "캡", "비니"};

    ProgressDialog serverDialog; //원형 progress bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        activity=this;

        serverDialog = new ProgressDialog(CategoryActivity.this);
        serverDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //progress bar가 동그란 형태
        serverDialog.setMessage("옷의 종류를 파악중입니다.");

        String id= (SaveSharedPreference.getString(getApplicationContext(), "ID"));

        Intent intent = getIntent(); /*데이터 수신*/
        String imgName = intent.getExtras().getString("imgName"); /*String형*/
        String imgPath= intent.getExtras().getString("imgFile"); /*String형*/
        File imgFile = new  File(imgPath);

        catBtn= (Button) findViewById(R.id.catBtn);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        if(imgFile.exists()){
            Log.d("file_", String.valueOf(imgFile));
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.imageView);
            myImage.setImageBitmap(myBitmap);
        }

        // 다이얼로그 바디
        alertdialog = new AlertDialog.Builder(activity);
        // 다이얼로그 메세지
        //alertdialog.setMessage("기본 다이얼로그 입니다.");

        // 확인버튼
        alertdialog.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "'예'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                startSaveCategory(new SaveCategoryData(id, imgName, category));
            }
        });

        // 취소버튼
        alertdialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "'아니요'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                ListClick(view, id, imgName); //틀리면 어떤 카테고리인지 선택하게 함
            }
        });

        catBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverDialog.show();
                startCategory(new CategoryData(id, imgName));
            }
        });
    }

    private void startCategory(CategoryData data) {
        int aResult=0;
        msgCode=0;
        service.findCategory(data).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                CategoryResponse result = response.body();
                if(response.body() != null) {
                    result = response.body();
                }
                else{
                    Log.v("알림", "값이 없습니다.");
                }
                // Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                //값 가져오는 거 성공하면 사용자가 예상한 결과값과 같은 지 확인하기
                if(result.getCode()==200){
                    if(serverDialog !=null){ //progress bar 닫기
                        serverDialog.dismiss();
                    }
                    category=result.getCategoryResult();
                  //  Toast.makeText(getApplicationContext(), "category: "+result.getCategoryResult(), Toast.LENGTH_SHORT).show();
                    alertdialog.setMessage(category+" 가 맞습니까?");
                    // 메인 다이얼로그 생성
                    AlertDialog alert = alertdialog.create();
                    // 아이콘 설정
                    //alert.setIcon(R.drawable.ic_launcher);
                    // 타이틀
                    alert.setTitle("Category 확인");
                    // 다이얼로그 보기
                    alert.show();
                    msgCode=200;
                }

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                if(serverDialog !=null){ //progress bar 닫기
                    serverDialog.dismiss();
                }
                Toast.makeText(CategoryActivity.this, "카테고리 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("카테고리 에러 발생", t.getMessage());
            }
        });
        //if(msgCode==200) aResult=1;
        //return aResult;
    }


    private void startSaveCategory(SaveCategoryData data) {
        service.saveCategory(data).enqueue(new Callback<SaveCategoryResponse>() {
            @Override
            public void onResponse(Call<SaveCategoryResponse> call, Response<SaveCategoryResponse> response) {
                SaveCategoryResponse result = response.body();
                if(response.body() != null) {
                    result = response.body();
                }
                else{
                    Log.v("알림", "값이 없습니다.");
                }

                if(result.getCode()==200){
                    Toast.makeText(getApplicationContext(), "사진과 옷의 정보를 저장하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<SaveCategoryResponse> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "카테고리 저장 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("카테고리 저장 에러 발생", t.getMessage());
            }
        });
    }

    public void ListClick(View view, String id, String imgName){
        new AlertDialog.Builder(this).setTitle("선택").setItems(words, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CategoryActivity.this, "words : " + words[which], Toast.LENGTH_LONG).show();
                switch(which){
                    case 0: RadioTopClick(view, id, imgName); break;
                    case 1: RadioBottomClick(view, id, imgName); break;
                    case 2: RadioOuterClick(view, id, imgName); break;
                    case 3: category="dress"; startSaveCategory(new SaveCategoryData(id, imgName, category)); break;
                    case 4: RadioAccClick(view, id, imgName); break;
                }
            } }).setNeutralButton("닫기", null).setPositiveButton("확인", null).show();

    }

    public void RadioTopClick(View view, String id, String imgName) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(topList, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                g_which=which;
                Toast.makeText(CategoryActivity.this, "words : " + topList[which], Toast.LENGTH_SHORT).show();
                switch(which){
                    case 0: category="shortsleeve"; break;
                    case 1: category="longsleeve"; break;
                    case 2: category="shortshirt"; break;
                    case 3: category="longshirt"; break;
                    case 4: category="sweater"; break;
                    case 5: category="hoodie"; break;
                    case 6: category="shortblouse"; break;
                    case 7: category="longblouse"; break;
                    case 8: category="sleeveless"; break;
                    case 9: category="vest"; break;
                }
            }
        });
        radioBuilder.setNeutralButton("closed",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"neutral click", Toast.LENGTH_LONG).show();
            }
        });
        radioBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if(g_which == 100){
                    Toast.makeText(getApplicationContext(),"카테고리를 선택해주세요", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Yeah!!", Toast.LENGTH_LONG).show();
                    startSaveCategory(new SaveCategoryData(id, imgName, category));
                }
            }
        });
        radioBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"negative click", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = radioBuilder.create();
        alertDialog.show();
    }

    public void RadioBottomClick(View view, String id, String imgName) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(bottomList, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                g_which=which;
                Toast.makeText(CategoryActivity.this, "words : " + bottomList[which], Toast.LENGTH_SHORT).show();
                switch(which){
                    case 0: category="jeans"; break;
                    case 1: category="longskirts"; break;
                    case 2: category="shorts"; break;
                    case 3: category="shortskirts"; break;
                    case 4: category="slacks"; break;
                    case 5: category="trainingpants"; break;
                    case 6: category="trainingshorts"; break;
                }
            }
        });
        radioBuilder.setNeutralButton("closed",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"neutral click", Toast.LENGTH_LONG).show();
            }
        });
        radioBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if(g_which == 100){
                    Toast.makeText(getApplicationContext(),"카테고리를 선택해주세요", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Yeah!!", Toast.LENGTH_LONG).show();
                    startSaveCategory(new SaveCategoryData(id, imgName, category));
                }
            }
        });
        radioBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"negative click", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = radioBuilder.create();
        alertDialog.show();
    }


    public void RadioOuterClick(View view, String id, String imgName) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(outerList, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                g_which=which;
                Toast.makeText(CategoryActivity.this, "words : " + outerList[which], Toast.LENGTH_SHORT).show();
                switch(which){
                    case 0: category="cardigan"; break;
                    case 1: category="fleece"; break;
                    case 2: category="biker"; break;
                    case 3: category="denim"; break;
                    case 4: category="blazer"; break;
                    case 5: category="coat"; break;
                    case 6: category="parka"; break;
                }
            }
        });
        radioBuilder.setNeutralButton("closed",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"neutral click", Toast.LENGTH_LONG).show();
            }
        });
        radioBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if(g_which == 100){
                    Toast.makeText(getApplicationContext(),"카테고리를 선택해주세요", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Yeah!!", Toast.LENGTH_LONG).show();
                    startSaveCategory(new SaveCategoryData(id, imgName, category));
                }
            }
        });
        radioBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"negative click", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = radioBuilder.create();
        alertDialog.show();
    }


    public void RadioAccClick(View view, String id, String imgName) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(accList, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                g_which=which;
                Toast.makeText(CategoryActivity.this, "words : " + accList[which], Toast.LENGTH_SHORT).show();
                switch(which){
                    case 0: category="scarf"; break;
                    case 1: category="cap"; break;
                    case 2: category="beanie"; break;
                }
            }
        });
        radioBuilder.setNeutralButton("closed",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"neutral click", Toast.LENGTH_LONG).show();
            }
        });
        radioBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                if(g_which == 100){
                    Toast.makeText(getApplicationContext(),"카테고리를 선택해주세요", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Yeah!!", Toast.LENGTH_LONG).show();
                    startSaveCategory(new SaveCategoryData(id, imgName, category));
                }
            }
        });
        radioBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"negative click", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = radioBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onDestroy(){
        if(serverDialog !=null && serverDialog.isShowing()){
            serverDialog.dismiss();
        }
        super.onDestroy();
    }

       /*
    public void ListClick(View view, String id, String imgName){
        AlertDialog.Builder listBuilder=new AlertDialog.Builder(this);
        listBuilder.setTitle("선택");
        listBuilder.setSingleChoiceItems(words, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CategoryActivity.this, "words : " + words[which], Toast.LENGTH_SHORT).show();
            }
        });
        listBuilder.setNeutralButton("closed",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"neutral click", Toast.LENGTH_LONG).show();
            }
        });
        listBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0: RadioTopClick(view, id, imgName); break;
                    case 1: RadioBottomClick(view, id, imgName); break;
                    case 2: RadioOuterClick(view, id, imgName); break;
                    case 3: category="dress"; break;
                    case 4: RadioAccClick(view, id, imgName); break;
                }
                Toast.makeText(getApplicationContext(),"Yeah!!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = listBuilder.create();
        alertDialog.show();
    }
     */

    /*
    public void ListClick(View view, String id, String imgName) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(words, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CategoryActivity.this, "words : " + words[which], Toast.LENGTH_SHORT).show();
            }
        });
        radioBuilder.setNeutralButton("closed",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"neutral click", Toast.LENGTH_LONG).show();
            }
        });
        radioBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Yeah!!", Toast.LENGTH_LONG).show();
                switch(which){
                    case 0: RadioTopClick(view, id, imgName); break;
                    case 1: RadioBottomClick(view, id, imgName); break;
                    case 2: RadioOuterClick(view, id, imgName); break;
                    case 3: category="dress"; break;
                    case 4: RadioAccClick(view, id, imgName); break;
                }
            }
        });
    }
     */


       /*
    new AsyncTask<Void, Void, String>(){
        @Override
        protected String doInBackground(Void... params) {
            GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
            Call<List<Contributor>> call = gitHubService.repoContributors("square", "retrofit");

            try {
                return call.execute().body().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(s);
        }
    }.execute();
 */
}

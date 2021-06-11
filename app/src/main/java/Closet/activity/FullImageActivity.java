package Closet.activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.R;

import Category.activity.CategoryActivity;
import Category.data.CategoryData;
import Category.data.SaveCategoryData;
import Closet.data.DeleteData;
import Closet.data.DeleteResponse;
import Closet.data.ModifyData;
import Closet.data.ModifyResponse;
import Cookie.SaveSharedPreference;
import Login_Main.activity.MainActivity;
import network.RetrofitClient;
import network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import Closet.ImageAdapter;


public class FullImageActivity extends AppCompatActivity {
    Button modifyBtn;
    Button deleteBtn;
    private Activity activity;
    private String category="";
    private String imgUrl="";
    private String id="";
    private int g_which=100;
    AlertDialog.Builder alertDeletedialog; // 다이얼로그 바디
    private final String[] words=new String[] {"상의", "하의", "아우터", "원피스", "악세서리"};
    private final String[] topList=new String[]{"반팔", "긴팔", "반팔 셔츠", "긴팔 셔츠", "니트", "후드", "반팔 블라우스", "긴팔 블라우스", "민소매", "조끼"};
    private final String[] bottomList=new String[]{"데님", "롱 스커트", "반바지", "미니 스커트", "슬랙스", "롱 트레이닝팬츠", "숏 트레이닝팬츠"};
    private final String[] outerList=new String[]{"가디건", "후리스", "레더 자켓", "데님 자켓", "정장 자켓", "코트","패딩"};
    private final String[] accList=new String[]{"목도리", "캡", "비니"};
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_image_details);
        activity=this;
        // Get intent data
        Intent i = getIntent();
        // Get Selected Image Id
        imgUrl = i.getExtras().getString("url");
        Log.d("url:::::: ", imgUrl);

        id= (SaveSharedPreference.getString(getApplicationContext(), "ID"));

        deleteBtn= (Button) findViewById(R.id.deleteBtn);
        modifyBtn= (Button) findViewById(R.id.modifyBtn);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        //ImageAdapter imageAdapter = new ImageAdapter(this);
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        if(imgUrl.length()!=0) {

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(getApplicationContext()).load(imgUrl).apply(options).into(imageView);
        }
        //imageView.setImageResource(imageAdapter.thumbImages[position]);

        // 다이얼로그 바디
        alertDeletedialog = new AlertDialog.Builder(activity);
        // 다이얼로그 메세지
        alertDeletedialog.setMessage("정말 삭제하시겠습니까?");

        // 확인버튼
        alertDeletedialog.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "'예'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                startDeleteCategory(new DeleteData(id, imgUrl));
            }
        });

        // 취소버튼
        alertDeletedialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "'아니요'버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = alertDeletedialog.create();
                alert.setTitle("삭제 확인");
                alert.show();
            }
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListClick(view);
                //startModify(new CategoryData(id, imgName));
            }
        });
    }

    //상의, 하의, 아우터, 원피스, 악세서리 선택하게 함
    public void ListClick(View view){
        new AlertDialog.Builder(this).setTitle("선택").setItems(words, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FullImageActivity.this, "words : " + words[which], Toast.LENGTH_LONG).show();
                switch(which){
                    case 0: RadioTopClick(view); break;
                    case 1: RadioBottomClick(view); break;
                    case 2: RadioOuterClick(view); break;
                    case 3: category="dress"; startModifyCategory(new ModifyData(id, imgUrl, category)); break;
                    case 4: RadioAccClick(view); break;
                }
            } }).setNeutralButton("닫기", null).setPositiveButton("확인", null).show();

    }

    public void RadioTopClick(View view) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(topList, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                g_which=which;
                Toast.makeText(FullImageActivity.this, "words : " + topList[which], Toast.LENGTH_SHORT).show();
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
                else{
                    Toast.makeText(getApplicationContext(),"Yeah!!", Toast.LENGTH_LONG).show();
                    startModifyCategory(new ModifyData(id, imgUrl, category));
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

    public void RadioBottomClick(View view) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(bottomList, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                g_which=which;
                Toast.makeText(FullImageActivity.this, "words : " + bottomList[which], Toast.LENGTH_SHORT).show();
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
                else{
                    Toast.makeText(getApplicationContext(),"Yeah!!", Toast.LENGTH_LONG).show();
                    startModifyCategory(new ModifyData(id, imgUrl, category));
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


    public void RadioOuterClick(View view) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(outerList, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                g_which=which;
                Toast.makeText(FullImageActivity.this, "words : " + outerList[which], Toast.LENGTH_SHORT).show();
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
                else{
                    Toast.makeText(getApplicationContext(),"Yeah!!", Toast.LENGTH_LONG).show();
                    startModifyCategory(new ModifyData(id, imgUrl, category));
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


    public void RadioAccClick(View view) {
        AlertDialog.Builder radioBuilder=new AlertDialog.Builder(this);
        radioBuilder.setTitle("선택");
        radioBuilder.setSingleChoiceItems(accList, -1, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                g_which=which;
                Toast.makeText(FullImageActivity.this, "words : " + accList[which], Toast.LENGTH_SHORT).show();
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
                else{
                    Toast.makeText(getApplicationContext(),"Yeah!!", Toast.LENGTH_LONG).show();
                    startModifyCategory(new ModifyData(id, imgUrl, category));
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
        super.onDestroy();
    }


    private void startDeleteCategory(DeleteData data) {
        service.deleteImage(data).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                DeleteResponse result = response.body();

                if (result.getCode() == 200) {
                    Toast.makeText(FullImageActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Closet_MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(FullImageActivity.this, "옷 삭제 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("옷 삭제 에러 발생", t.getMessage());
            }
        });
    }

    private void startModifyCategory(ModifyData data) {
        service.modifyImage(data).enqueue(new Callback<ModifyResponse>() {
            @Override
            public void onResponse(Call<ModifyResponse> call, Response<ModifyResponse> response) {
                ModifyResponse result = response.body();
                if (result.getCode() == 200) {
                    Toast.makeText(FullImageActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Closet_MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ModifyResponse> call, Throwable t) {
                Toast.makeText(FullImageActivity.this, "옷 수정 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("옷 수정 에러 발생", t.getMessage());
            }
        });
    }
}
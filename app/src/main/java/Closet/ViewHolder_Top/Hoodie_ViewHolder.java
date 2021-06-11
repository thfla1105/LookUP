package Closet.ViewHolder_Top;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.R;
import java.util.List;

//import Closet.ImageAdapter;
import Closet.ImageAdapter;
import Closet.ImageAdapter2;
import Closet.activity.FullImageActivity;
import Closet.activity.TopActivity;
import Closet.data.ImageData;
import Closet.data.ImageResponse;
import Cookie.SaveSharedPreference;
import network.RetrofitClient;
import network.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hoodie_ViewHolder extends RecyclerView.ViewHolder{
    int a;
    int mThumbIds;
    TextView textView;
    GridView gridView;
    private ServiceApi service;
    Context context;
    String id;
    Call<ImageResponse> call;
    public ImageAdapter2 imageAdapter;
    String category="hoodie";

    public Hoodie_ViewHolder(@NonNull View itemView , int a) { //뷰홀더에서 작업들 실행
        super(itemView);
        context = itemView.getContext();
        this.a = a;
        //textView = itemView.findViewById(R.id.a_textview);
        gridView = itemView.findViewById(R.id.GridViewLayout);
        //textView.setText(String.valueOf(a));
        service = RetrofitClient.getClient().create(ServiceApi.class);
        context = itemView.getContext();
        id = (SaveSharedPreference.getString(context, "ID"));

        call=service.getClosetImages(new ImageData(id, category));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgUrl = imageAdapter.itemList.get(position).getUrl();
                Log.d("url:::::: ", imgUrl);
                Intent intent = new Intent(context, FullImageActivity.class);
                intent.putExtra("url", imgUrl);
                context.startActivity(intent);
            }
        });

        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse result=response.body();
                if(response.isSuccessful()) {
                    result=response.body();
                }
                else{
                    Log.v("알림", "값이 없습니다.");
                }

                if(result.getCode()==200){
                    //Toast.makeText(context, "옷장 사진 가져오기 성공", Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    //startActivityForResult(intent, 1);
                    //startActivity(intent);
                }

                imageAdapter=new ImageAdapter2(context);
                int[] indexes=result.getIndexes();
                String[] urls=result.getUrls();
                for(int i=0;i<urls.length;i++){
                    imageAdapter.addList(indexes[i], urls[i], category);
                }

                imageAdapter.notifyDataSetChanged();
                TopActivity.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setAdapter(imageAdapter);
                        // Stuff that updates the UI

                    }
                });
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Toast.makeText(context, "옷장 사진 가져오기 오류", Toast.LENGTH_SHORT).show();
                Log.e("옷장 사진들 가져오기 에러 발생", t.getMessage());
            }
        });
    }

    /*
    public void onCreateViewHolder(ViewGroup viewGroup, int viewType){
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse result = response.body();
                if(response.body() != null) {
                    result = response.body();
                }
                else{
                    Log.v("알림", "값이 없습니다.");
                }

                if(result.getCode()==200){
                    //Toast.makeText(getApplicationContext(), SaveSharedPreference.getString(getApplicationContext(), "ID")+"님 자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    //startActivityForResult(intent, 1);
                    //startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.e("옷장 사진들 가져오기 에러 발생", t.getMessage());
            }
        });
    }
     */



    /*
    private void getUrls(ImageData data) {
        service.getClosetImages(data).enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                ImageResponse result = response.body();
                if(response.body() != null) {
                    result = response.body();
                }
                else{
                    Log.v("알림", "값이 없습니다.");
                }
                // Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();

                //가져오는 거 성공
                if(result.getCode()==200){
                    //Toast.makeText(getApplicationContext(), SaveSharedPreference.getString(getApplicationContext(), "ID")+"님 자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    //startActivityForResult(intent, 1);
                    //startActivity(intent);
                }
                //showProgress(false);

            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("옷장 사진들 가져오기 에러 발생", t.getMessage());
                //showProgress(false);
            }
        });
    }
    */
}
package LookBook.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitWeather {
    private final static String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService/"; //http://54.180.195.102/57931
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private RetrofitWeather() {
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

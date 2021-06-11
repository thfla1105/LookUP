package LookBook.network;

import LookBook.currentWeatherData.CurrentWeatherData;
import LookBook.weatherData.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ServiceApi_Weather {

    @GET("*")
    Call<WeatherData> getWeather(
            @Query("pageNo") String pageNum,
            @Query("numOfRows") String numOfRows,
            @Query("dataType") String type,
            @Query("base_date") String baseDate,
            @Query("base_time") String baseTime,
            @Query("nx") String s_nx,
            @Query("ny") String s_ny
    );

    @GET("*")
    Call<CurrentWeatherData> getCurrentWeather(
            @Query("pageNo") String pageNum,
            @Query("numOfRows") String numOfRows,
            @Query("dataType") String type,
            @Query("base_date") String baseDate,
            @Query("base_time") String baseTime,
            @Query("nx") String s_nx,
            @Query("ny") String s_ny
    );

}

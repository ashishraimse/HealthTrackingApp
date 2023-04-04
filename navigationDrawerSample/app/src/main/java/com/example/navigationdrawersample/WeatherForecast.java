package com.example.navigationdrawersample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import okhttp3.OkHttpClient;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        /*OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://weatherbit-v1-mashape.p.rapidapi.com/forecast/3hourly?lat=28.57&lon=77.32")
                .get()
                .addHeader("X-RapidAPI-Key", "8d53397c0dmshdeb03fa330e907dp1b01d2jsnc80e54c980a7")
                .addHeader("X-RapidAPI-Host", "weatherbit-v1-mashape.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();*/

        RequestQueue req = Volley.newRequestQueue(this);
//        Request client = new OkHttpClient();

//        Request request = new Request.Builder()
//                .url("https://weatherbit-v1-mashape.p.rapidapi.com/forecast/3hourly?lat=28.57&lon=77.32")
//                .get()
//                .addHeader("X-RapidAPI-Key", "8d53397c0dmshdeb03fa330e907dp1b01d2jsnc80e54c980a7")
//                .addHeader("X-RapidAPI-Host", "weatherbit-v1-mashape.p.rapidapi.com")
//                .build();
//
//        Response response = client.newCall(request).execute();
    }
}
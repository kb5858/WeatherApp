package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
EditText et;
TextView tv;
String url= "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
String key="314b82275adbfe16150c28814985afbd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);
    }

        public void getweather(View view) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()).build();
            weatherapi myapi = retrofit.create(weatherapi.class);
            Call<Example> examplecall = myapi.getweathr(et.getText().toString().trim(), key);
            examplecall.enqueue(new Callback<Example>() {
                                    @Override
                                    public void onResponse(Call<Example> call, Response<Example> response) {
                                        if (response.code() == 404) {
                                            Toast.makeText(MainActivity.this, "please enter a valid city", Toast.LENGTH_SHORT).show();
                                        } else if (!(response.isSuccessful())) {
                                            Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                        Example mydata = response.body();
                                        Main main = mydata.getMain();
                                        Double temp = main.getTemp()-273.15;
                                        //Integer temperature = (int) (temp - 273.15);
                                        Double mintemp=main.getTempMin()-273.15;
                                        Double maxtemp=main.getTempMax()-273.15;

                                        tv.setText("Current temperature is "+String.valueOf(temp) + "C and min and max temperature is "+String.valueOf(mintemp)+" "+String.valueOf(maxtemp)+" respectively");
                                    }

                                    public void onFailure(Call<Example> CALL, Throwable t) {
                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
            );
        }}









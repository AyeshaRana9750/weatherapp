package com.example.weather_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button btnget;
    EditText edCity,edCountry;
    TextView citytext,weather,tvResult1,tvResult2,tvResult3,tvResult4,tvResult5;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df=new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        edCity=findViewById(R.id.et1);
        edCountry=findViewById(R.id.et2);
        btnget=findViewById(R.id.getbtn);
        citytext=findViewById(R.id.cityText);
        weather=findViewById(R.id.tv_result);
        tvResult1=findViewById(R.id.txt);
        tvResult2=findViewById(R.id.thmrtext);
        tvResult3=findViewById(R.id.txtwind);
        tvResult4=findViewById(R.id.txtcloud);


    }



    public void getWeather(View view) {

        String tempUrl = "";
        String city = edCity.getText().toString().trim();
        String country = edCountry.getText().toString().trim();

        if (city.equals("")) {
            tvResult1.setText("City field can not be empty!");
        } else {
            if (!country.equals("")) {
                tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
            } else {
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }
            StringRequest  stringRequest=new StringRequest(Request.Method.POST,
                    tempUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //  Log.d("response",response);
                            String outputC="";
                            String outputW="";
                            String output1="";
                            String output2="";
                            String output3="";
                            String output4="";
                            String output5="";


                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                JSONArray jsonArray=jsonObject.getJSONArray("weather");
                                JSONObject jsonObjectweather=jsonArray.getJSONObject(0);
                                String description=jsonObjectweather.getString("description");
                                JSONObject jsonObjectMain=jsonObject.getJSONObject("main");
                                double temp= jsonObjectMain.getDouble("temp")-273.15;
                                double feelsLike=jsonObjectMain.getDouble("feels_like")-273.15;
                                float pressure=jsonObjectMain.getInt("pressure");
                                int humidity=jsonObjectMain.getInt("humidity");
                                JSONObject jsonObjectWind=jsonObject.getJSONObject("wind");
                                String wind=jsonObjectWind.getString("speed");
                                JSONObject jsonObjectCloud=jsonObject.getJSONObject("clouds");
                                String clouds=jsonObjectCloud.getString("all");
                                JSONObject jsonObjectSys=jsonObject.getJSONObject("sys");
                                String CountryName=jsonObjectSys.getString("country");
                                String cityName=jsonObject.getString("name");

                                outputC=cityName;
                                outputW=df.format(temp)+  ""+ " °C";
                                output1=df.format(temp)+   "°C";
                                output2= + humidity  +"%";
                                output3= wind + "m/s (meters per second)";
                                output4= clouds +  "%";
                                //  output5="Pressure: " + pressure + "\n hPa";


                                citytext.setText(outputC);
                                weather.setText(outputW);
                                tvResult1.setText(output1);
                                tvResult2.setText(output2);
                                tvResult3.setText(output3);
                                tvResult4.setText(output4);
                                //  tvResult5.setText(output5);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    });
RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
requestQueue.add(stringRequest);
        }

    }
        }}
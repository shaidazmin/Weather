package com.example.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView t1_date, t2_city, t3_tem, t4_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1_date = findViewById(R.id.dateTextView);
        t2_city = findViewById(R.id.cityTextView);
        t3_tem = findViewById(R.id.tempTextView);
        t4_description = findViewById(R.id.descriptionTextView);
        setWeather();
    }

    private void setWeather() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=dhaka&appid=c7b5d1c7efdd4293364f5579edee989a";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mainObject = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String tmp = String.valueOf(mainObject.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                    // set Text ...

                    t2_city.setText("City : " + city);
                    t4_description.setText("Current Status : " + description);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE - MM - dd");
                    String formated_date = sdf.format(calendar.getTime());
                    t1_date.setText("Date : "+formated_date);

                    // kelvin theke celcious a nite hobe ...

                    double conver = Double.parseDouble(tmp);
                    double value = conver - 273.15;
                    int i = (int) value;
                    t3_tem.setText(String.valueOf(i));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }
}

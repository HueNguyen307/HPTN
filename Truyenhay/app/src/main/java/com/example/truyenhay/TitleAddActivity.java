package com.example.truyenhay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.model.Genre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitleAddActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name, des, author;
    Button btnAddTitle;
    Spinner sp;
    Genre genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_add);
        name=findViewById(R.id.add_title_name);
        des=findViewById(R.id.add_title_des);
        author=findViewById(R.id.add_title_author);
        btnAddTitle=findViewById(R.id.btnAddTitle);


        Intent intent=getIntent();
        genre =(Genre)intent.getSerializableExtra("genre");


        btnAddTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gid=genre.getId();
                String n=name.getText().toString();
                String a=author.getText().toString();
                String d=des.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(TitleAddActivity.this);

                String url = "http://192.168.1.14:8000/api/add_title/";

                Map<String,String> body = new HashMap<>();

                body.put("name", n);
                body.put("author", a);
                body.put("description", d);
                body.put("genreid", gid);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(TitleAddActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String,String> getParams(){
                        return body;
                    }

                };
                queue.add(stringRequest);
                Toast.makeText(TitleAddActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}
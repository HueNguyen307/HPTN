package com.example.truyenhay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class GenreUpdateActivity extends AppCompatActivity {
    EditText   name, description;

    Button btnupdate, btndelete;
    Genre genre;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_update);
        name=findViewById(R.id.edtGenreName);
        description=findViewById(R.id.edtGenreDes);
        btndelete=findViewById(R.id.GenreDelete);
        btnupdate=findViewById(R.id.GenreUpdate);
        intent=getIntent();
        genre = (Genre) intent.getSerializableExtra("item");
        name.setText(genre.getName());
        description.setText(genre.getDescription());
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = genre.getId().toString().trim();

                String url = "http://192.168.1.14:8000/"+"api/delete_genre/";
                RequestQueue queue = Volley.newRequestQueue(GenreUpdateActivity.this);
                Map<String,String> body = new HashMap<>();

                url+="?genreid="+id;
                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String, String> getParams(){
                        return body;
                    }

                };
                queue.add(stringRequest);
                Toast.makeText(GenreUpdateActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n=name.getText().toString();
                String d=description.getText().toString();
                //update genre


                RequestQueue queue = Volley.newRequestQueue(GenreUpdateActivity.this);

                String url = "http://192.168.1.14:8000/"+"api/update_genre/";

                Map<String,String> body = new HashMap<>();

                body.put("genreid", genre.getId().toString());
                body.put("name",n);
                body.put("description",d);

                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(GenreUpdateActivity.this, "Update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(GenreUpdateActivity.this,jsonObject.getString("error"),Toast.LENGTH_LONG);
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
                finish();
            }
        });
    }

    private void initview() {

    }
}
package com.example.truyenhay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.adapter.RecycleViewAdapterGenreList;
import com.example.truyenhay.model.Genre;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreManageActivity extends AppCompatActivity implements RecycleViewAdapterGenreList.ItemListener{
    RecyclerView recycleView;
    RecycleViewAdapterGenreList adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_manage);
        initview();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GenreManageActivity.this, "Add genre thanh cong", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), GenreAddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initview() {
        recycleView=findViewById(R.id.genre_recycleView);
        fab=findViewById(R.id.addgenre);

        adapter=new RecycleViewAdapterGenreList();
        List<Genre> list=new ArrayList<>();



        adapter.setItemListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Genre genre=adapter.getItem(position);
        Intent intent=new Intent(getApplicationContext(), GenreUpdateActivity.class);
        intent.putExtra("item", genre);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Genre> list=new ArrayList<>();
        String url = "http://192.168.1.14:8000/api/genre/";

        Map<String,String> body = new HashMap<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(object.getString("data"));

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Genre g = new Genre(jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("description")
                        );
                        list.add(g);
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
//                    System.out.println(jsonObject.get("name"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
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
            protected Map<String,String> getParams(){
                return body;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        adapter.setList(list);
    }
}
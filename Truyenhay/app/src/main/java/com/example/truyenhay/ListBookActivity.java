package com.example.truyenhay;

import android.content.Context;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.adapter.RecycleViewAdapter;
import com.example.truyenhay.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBookActivity extends AppCompatActivity implements RecycleViewAdapter.ItemListener{
    private RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);
        recyclerView=findViewById(R.id.recycleView);

        List<Book> list=new ArrayList<>();
        adapter=new RecycleViewAdapter();
        adapter.setList(list);

        Intent intent = getIntent();
        String id =intent.getStringExtra("genreid");
        System.out.println(id);
        Map<String,String> body = new HashMap<>();
        String url = "http://192.168.1.14:8000/api/"+"genre/title/";
        url += "?genreID=" +  String.valueOf(id);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Book book = new Book(jsonObject.getString("id"),
                                jsonObject.getString("genreId"),
                                jsonObject.getString("name"),
                                jsonObject.getString("author"),
                                jsonObject.getString("description")

                        );
                        list.add(book);

                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
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

            @Override
            protected Map<String,String> getParams(){
                return body;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ListBookActivity.this);
        requestQueue.add(stringRequest);

        Context context = recyclerView.getContext();
        LinearLayoutManager manager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(ListBookActivity.this, BookActivity.class);
        startActivity(intent);
    }
}
package com.example.truyenhay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.adapter.RecycleViewAdapterChapList;
import com.example.truyenhay.model.Book;
import com.example.truyenhay.model.Chap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ChapList extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Button bt;
    RecycleViewAdapterChapList adapter;
    Book title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap_list);
        recyclerView=findViewById(R.id.recycleChap);

        adapter=new RecycleViewAdapterChapList();
        Intent intent =getIntent();
        title=(Book) intent.getSerializableExtra("title");


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url =  "http://192.168.1.14:8000/api"+ "/chapterList";

        url += "?titleID=" + String.valueOf(title.getId());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Chap> list = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Chap chapter = new Chap(jsonObject.getString("id")
                                        ,jsonObject.getString("name"),
                                        jsonObject.getString("number"));
                                System.out.println(chapter.getName());
                                list.add(chapter);
                            }
                            adapter.setList(list);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException ex) {
                            throw new RuntimeException(ex);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data, "UTF-8"));
                            System.out.println(jsonObject.getString("error"));
                            Toast.makeText(ChapList.this,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e){
                            System.out.println("Server is offline....");
                            Toast.makeText(ChapList.this,"Server is offline....",Toast.LENGTH_SHORT);
                        }
                    }

                });
        requestQueue.add(stringRequest);



        Context context = recyclerView.getContext();
        LinearLayoutManager manager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        bt=findViewById(R.id.idbt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChapList.this, AddChap.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }



}
package com.example.truyenhay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
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
import com.example.truyenhay.adapter.RecycleViewAdapter;
import com.example.truyenhay.adapter.RecycleViewAdapterChapList;
import com.example.truyenhay.model.Book;
import com.example.truyenhay.model.Chap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookActivity extends AppCompatActivity   implements RecycleViewAdapterChapList.ItemListener,RecycleViewAdapter.ItemListener{
    private RecyclerView recyclerView;
    private TextView txtName, loai, tg, mota;
    RecycleViewAdapterChapList adapter;
    String idgenre;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        txtName =findViewById(R.id.txtbook);
        loai =findViewById(R.id.loai);
        mota =findViewById(R.id.mota);
        tg =findViewById(R.id.tg);
        recyclerView=findViewById(R.id.recycleChap);

        Intent intent = getIntent();
        book=(Book)intent.getSerializableExtra("book");

        adapter=new RecycleViewAdapterChapList();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url =  "http://192.168.1.14:8000/api"+ "/chapterList";

        url += "?titleID=" + String.valueOf(book.getId());

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
                            Toast.makeText(BookActivity.this,jsonObject.getString("error"),Toast.LENGTH_SHORT);
                        } catch (UnsupportedEncodingException | JSONException e) {
                            throw new RuntimeException(e);
                        } catch (NullPointerException e){
                            System.out.println("Server is offline....");
                            Toast.makeText(BookActivity.this,"Server is offline....",Toast.LENGTH_SHORT);
                        }
                    }

                });
        requestQueue.add(stringRequest);

        Context context = recyclerView.getContext();
        LinearLayoutManager manager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);


        RequestQueue queue = Volley.newRequestQueue(BookActivity.this);

        url =  "http://192.168.1.14:8000/api"+"/title/";

        Map<String,String> body = new HashMap<>();


        url += "?titleid="+String.valueOf(book.getId()) ;

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String title=jsonObject.getString("name");
                    String author=jsonObject.getString("author");
                    String description=jsonObject.getString("description");
                    String genre=jsonObject.getString("genre");
                    System.out.println(idgenre);
                    txtName.setText(title);
                    tg.setText(author);
                    mota.setText(description);
                    loai.setText(genre);

                } catch (JSONException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());

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


    }

    @Override

    public void onItemClick(View view, int position) {
        Chap chap=adapter.getItem(position);
        Intent intent2=new Intent(BookActivity.this, ChapActivity.class);
        intent2.putExtra("chap", chap);
        startActivity(intent2);

    }

}
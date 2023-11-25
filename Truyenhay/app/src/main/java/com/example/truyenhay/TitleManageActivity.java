package com.example.truyenhay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.adapter.RecycleViewAdapter;
import com.example.truyenhay.model.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitleManageActivity extends AppCompatActivity implements RecycleViewAdapter.ItemListener, SearchView.OnQueryTextListener {

    private RecyclerView recycleView;
    RecycleViewAdapter adapter;
    private SearchView searchView;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_manage);
        recycleView =findViewById(R.id.recycleView);
        searchView=findViewById(R.id.searchView);
        fab=findViewById(R.id.addtitle);
        adapter = new RecycleViewAdapter();
        List<Book> list = new ArrayList<>();
        Book b = new Book();
        list.add(b);
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
        adapter.setItemListener(this);
        searchView.setOnQueryTextListener(this);
        searchView.setQuery("",true);
        adapter.notifyDataSetChanged();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), AddTitleByGenre.class);

                startActivity(intent);
            }
        });
    }

    private void intiview() {

    }


    @Override
    public void onItemClick(View view, int position) {
        Book title=adapter.getItem(position);
        Intent intent=new Intent(getApplicationContext(), TitleUpdateActivity.class);
        intent.putExtra("item", title);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.setList(new ArrayList<>());

        String url =  "http://192.168.1.14:8000/api/search_title_by_name/";
        System.out.println(url);
        String search = String.valueOf(searchView.getQuery());
        Map<String, String> body = new HashMap<>();
        url +="?name=" + search + "&author=" + search;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Book> list = new ArrayList<>();
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

                                adapter.setList(list);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            System.err.println(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());

                    }

                    protected Map<String, String> getParams() {
                        return body;
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        adapter.notifyDataSetChanged();
        return false;
    }
}
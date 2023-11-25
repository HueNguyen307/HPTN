package com.example.truyenhay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.truyenhay.BookActivity;
import com.example.truyenhay.R;
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

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener, SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    private SearchView searchView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        searchView=view.findViewById(R.id.searchView);
        adapter = new RecycleViewAdapter();
        List<Book> list = new ArrayList<>();
        Book b = new Book();
        list.add(b);
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        searchView.setOnQueryTextListener(this);
        searchView.setQuery("",true);
    }
    public void updateList(){


    }
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
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

                    }

                    protected Map<String, String> getParams() {
                        return body;
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        adapter.notifyDataSetChanged();
        return false;
    }

    public void onItemClick(View view, int position) {
        Book book=adapter.getItem(position);
        System.out.println(book.getId());
        Intent intent=new Intent(getActivity(), BookActivity.class);
        intent.putExtra("book",book);
        startActivity(intent);
    }
}

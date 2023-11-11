package com.example.truyenhay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyenhay.adapter.GenreRecycleViewAdapter;
import com.example.truyenhay.model.Genre;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GenreManageActivity extends AppCompatActivity implements GenreRecycleViewAdapter.ItemListener{
    RecyclerView recycleView;
    GenreRecycleViewAdapter adapter;
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

        adapter=new GenreRecycleViewAdapter();
        List<Genre> list=new ArrayList<>();
        //lay ds tu api

        adapter.setItemListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Genre genre=adapter.getGenre(position);
        Intent intent=new Intent(getApplicationContext(), GenreUpdateActivity.class);
        intent.putExtra("item", genre);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Genre> list=new ArrayList<>();
        //lay ds tu api

        adapter.setList(list);
    }
}
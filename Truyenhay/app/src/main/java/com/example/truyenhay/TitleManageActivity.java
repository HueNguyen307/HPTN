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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truyenhay.adapter.TitleRecycleViewAdapter;
import com.example.truyenhay.model.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TitleManageActivity extends AppCompatActivity implements TitleRecycleViewAdapter.ItemListener{

    RecyclerView recycleView;
    TitleRecycleViewAdapter adapter;
    TextView tt;
    FloatingActionButton fab;
    Button btnSearch;
    Spinner spinner;
    String id;
    boolean exist;
    String genreselected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_manage);
        intiview();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TitleManageActivity.this, "Add title thanh cong", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), TitleAddActivity.class);
//                intent.putExtra("genreid", id);
                startActivity(intent);
            }
        });
        List<Book
                > mList=new ArrayList<>();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    private void intiview() {
        recycleView=findViewById(R.id.title_recycleView);
        fab=findViewById(R.id.addtitle);
        spinner=findViewById(R.id.spinner);
        btnSearch=findViewById(R.id.btnSearchtitleByGenre);
        tt=findViewById(R.id.txtTonTai);
        adapter=new TitleRecycleViewAdapter();
        exist=false;

        // danh sach the loai
        String[] listSchool = new String[0];
        ArrayAdapter<String> adapterSchool=new ArrayAdapter<>(this, R.layout.item_spinner,listSchool);
        spinner.setAdapter(adapterSchool);

        List<Book> k=new ArrayList<>();
        adapter.setList(k);
        adapter.setItemListener(this);
        LinearLayoutManager manager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Book title=adapter.getTitle(position);
        Intent intent=new Intent(getApplicationContext(), TitleUpdateActivity.class);
        intent.putExtra("item", title);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
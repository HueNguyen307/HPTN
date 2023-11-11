package com.example.truyenhay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GenreAddActivity extends AppCompatActivity {
    EditText name, des;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_add);
        name=findViewById(R.id.add_genre_name);
        des=findViewById(R.id.add_genre_des);
        btnAdd=findViewById(R.id.btnAddGenre);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
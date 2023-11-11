package com.example.truyenhay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truyenhay.model.Genre;



public class GenreUpdateActivity extends AppCompatActivity {
    EditText userid, genreid, name, description;
    Button btnupdate, btndelete;
    Genre genre;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_update);
//        userid=findViewById(R.id.edtGenreIDUser);
//        genreid=findViewById(R.id.edt_genre_id);
        name=findViewById(R.id.edtGenreName);
        description=findViewById(R.id.edtGenreDes);
        btndelete=findViewById(R.id.GenreDelete);
        btnupdate=findViewById(R.id.GenreUpdate);
        intent=getIntent();
        genre = (Genre) intent.getSerializableExtra("item");
//        userid.setText(genre.);
        genreid.setText("id");
        name.setText(genre.getName());
        description.setText("mo ta");
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initview() {

    }
}
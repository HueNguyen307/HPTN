package com.example.truyenhay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truyenhay.model.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TitleUpdateActivity extends AppCompatActivity {

    EditText userid;
    EditText titleid;
    EditText name;
    EditText description;
    EditText author;
    CheckBox ck;
    Button btnupdate, btndelete, btnaddchap;

    private Book title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_update);
//        userid=findViewById(R.id.edtTitleIDUser);
        titleid=findViewById(R.id.edt_title_id);
        name=findViewById(R.id.edtTitleName);
        author=findViewById(R.id.edtTitleAuthor);
        description=findViewById(R.id.edtTitleDes);
//        ck=findViewById(R.id.updateTitleCK);
        btndelete=findViewById(R.id.TitleDelete);
        btnupdate=findViewById(R.id.TitleUpdate);
        btnaddchap=findViewById(R.id.addChap);
        Intent intent=getIntent();
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
        btnaddchap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TitleUpdateActivity.this, ChapList.class);
                startActivity(intent);
            }
        });
    }
}
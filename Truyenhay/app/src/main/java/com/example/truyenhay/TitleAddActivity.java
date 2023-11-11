package com.example.truyenhay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TitleAddActivity extends AppCompatActivity {
    EditText genreid;
    EditText userid, name, des, author;
    CheckBox cbFree;
    Button btnAddTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_add);
//        genreid=findViewById(R.id.add_title_genreid);
        name=findViewById(R.id.add_title_name);
        des=findViewById(R.id.add_title_des);
        author=findViewById(R.id.add_title_author);
//        cbFree=findViewById(R.id.ckFeeAddTitle);
        btnAddTitle=findViewById(R.id.btnAddTitle);
//        Intent intent=getIntent();
//        genreid.setText(intent.getSerializableExtra("id").toString());
        btnAddTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //end
            }
        });

    }
}